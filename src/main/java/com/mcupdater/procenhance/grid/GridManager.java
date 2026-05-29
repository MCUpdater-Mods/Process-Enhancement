package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GridManager extends SavedData {
	private static GridManager INSTANCE;
	private final Map<Grid, Set<Node>> nodeMap = new HashMap<>();
	private final Set<Node> allNodes = new HashSet<>();
	private final Queue<Tuple<Grid, UUID>> replacementQueue = new LinkedList<>();

	public GridManager() {
		ProcessEnhancement.LOGGER.info("GridManager init");
		setDirty();
	}

	public Grid createGrid() {
		Grid newGrid = new Grid();
		this.nodeMap.put(newGrid, new HashSet<>());
		return newGrid;
	}

	public static void setInstance(GridManager instance) {
		INSTANCE = instance;
	}

	public static GridManager getInstance() {
		return INSTANCE;
	}

	@Override
	public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		ProcessEnhancement.LOGGER.info("GridManager save");
		cleanupGrids();
		ListTag grids = new ListTag();
		nodeMap.forEach((grid, nodeSet) -> {
			CompoundTag entry = new CompoundTag();
			entry.put("grid", grid.saveAdditional(new CompoundTag(), registries));
			ListTag nodes = new ListTag();
			nodeSet.forEach(node -> nodes.add(node.saveAdditional(new CompoundTag(), registries)));
			entry.put("nodes", nodes);
			grids.add(entry);
		});
		tag.put("nodeMap", grids);
		return tag;
	}

	private void cleanupGrids() {
		nodeMap.forEach((grid, nodes) -> {
			if (nodes.isEmpty()) nodeMap.remove(grid);
		});
	}

	public static GridManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
		GridManager loading = new GridManager();
		if (compoundTag.contains("nodeMap")) {
			ListTag grids = compoundTag.getList("nodeMap", Tag.TAG_COMPOUND);
			for (int i = 0; i < grids.size(); i++) {
				CompoundTag entry = grids.getCompound(i);
				Grid loadedGrid = Grid.load(entry.getCompound("grid"), provider);
				Set<Node> nodeSet = new HashSet<>();
				if (entry.contains("nodes")) {
					ListTag nodes = entry.getList("nodes", Tag.TAG_COMPOUND);
					for (int j = 0; j < nodes.size(); j++) {
						nodeSet.add(Node.load(nodes.getCompound(j), provider));
					}
				}
				loading.nodeMap.put(loadedGrid, nodeSet);
			}
		}
		if (!loading.replacementQueue.isEmpty()) {
			Set<Grid> grids = loading.nodeMap.keySet();
			loading.replacementQueue.forEach(entry -> entry.getA().setReplacedBy(grids.stream().filter(grid -> entry.getB().equals(grid.getGridId())).findFirst().orElse(null)));
			loading.replacementQueue.clear();
		}
		return loading;
	}

	public Set<Node> getNodeSet(Grid grid) {
		return this.nodeMap.get(grid);
	}

	public void removeNode(Node node) {
		this.nodeMap.get(node.getGrid()).remove(node);
		this.allNodes.remove(node);
		this.setDirty();
	}

	public void addNode(Node node) {
		this.nodeMap.get(node.getGrid()).add(node);
		this.allNodes.add(node);
		this.setDirty();
	}

	public void migrateGrid(Grid oldGrid, Grid newGrid) {
		Set<Node> nodeSet = this.nodeMap.get(oldGrid);
		this.nodeMap.get(newGrid).addAll(nodeSet);
		nodeSet.forEach(node -> node.updateGrid(newGrid));
		this.nodeMap.remove(oldGrid);
		this.setDirty();
	}

	public void enqueueReplacementMap(Grid grid, UUID replacedBy) {
		this.replacementQueue.add(new Tuple<>(grid, replacedBy));
	}

	public Node getNodebyId(UUID neighborId) {
		return this.allNodes.stream().filter(node -> node.getNodeId().equals(neighborId)).findFirst().orElse(null);
	}

	public Grid getGridById(UUID gridId) {
		return this.nodeMap.keySet().stream().filter(grid -> gridId.equals(grid.getGridId())).findFirst().orElse(null);
	}
}
