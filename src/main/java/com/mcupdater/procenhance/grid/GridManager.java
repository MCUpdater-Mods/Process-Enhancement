package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class GridManager extends SavedData {
	private static GridManager INSTANCE;
	private static boolean loaded = false;
	private final Set<Grid> grids = new HashSet<>();
	private final Map<UUID, Set<UUID>> nodeMap = new HashMap<>();
	private final Set<Node> nodes = new HashSet<>();
	private final Queue<Tuple<Grid, UUID>> replacementQueue = new LinkedList<>();

	public GridManager() {
		ProcessEnhancement.LOGGER.info("GridManager init");
		setInstance(this);
	}

	public GridManager(Set<Grid> loadedGrids, Set<Node> loadedNodes) {
		setInstance(this);
	}

	public Grid createGrid() {
		Grid newGrid = new Grid(UUID.randomUUID());
		grids.add(newGrid);
		return newGrid;
	}

	public static void setInstance(GridManager instance) {
		INSTANCE = instance;
	}

	public static GridManager getInstance(ServerLevel level) {
		if (INSTANCE == null) {
			INSTANCE = level.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(GridManager::new, GridManager::load),"pe_grid");
		}
		return INSTANCE;
	}

	public static GridManager getInstance() {
		return INSTANCE;
	}

	@Override
	public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		ProcessEnhancement.LOGGER.info("GridManager save");
		cleanupGrids();
		ListTag gridsTag = new ListTag();
		this.grids.forEach(grid -> gridsTag.add(grid.saveAdditional(new CompoundTag(), registries)));
		//ProcessEnhancement.LOGGER.debug("Grids: {}",grids.size());
		//grids.forEach(grid -> ProcessEnhancement.LOGGER.debug(" - ID: {}; Valid: {}; ReplacedBy: {}",grid.getGridId(),grid.isValid(),(grid.getReplacedBy() != null) ? grid.getReplacedBy().toString() : "null"));
		tag.put("grids", gridsTag);
		ListTag nodesTag = new ListTag();
		this.nodes.forEach(node -> nodesTag.add(node.saveAdditional(new CompoundTag(), registries)));
		//ProcessEnhancement.LOGGER.debug("Nodes: {}",nodes.size());
		//nodes.forEach(node -> ProcessEnhancement.LOGGER.debug(" - ID: {}; Level: {}, Pos: {}; Grid: {}; Neighbors: {}; Endpoints: {}",node.getNodeId().toString(),node.getLevelName(),node.getPos(),node.getRawGrid().getGridId().toString(),node.getNeighbors().size(),node.getLocalEndpoints().size()));
		tag.put("nodes", nodesTag);
		ProcessEnhancement.LOGGER.info(tag.toString());
		return tag;
	}

	private void cleanupGrids() {
		this.nodeMap.forEach((grid, nodes) -> {
			if (nodes.isEmpty()) grids.remove(grid);
		});
	}

	public static GridManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
		ProcessEnhancement.LOGGER.info("GridManager load");
		new GridManager();
		ProcessEnhancement.LOGGER.debug("Tag data: {}",compoundTag.toString());
		Set<Grid> loadedGrids = new HashSet<>();
		if (compoundTag.contains("grids")) {
			ListTag gridsTag = compoundTag.getList("grids", Tag.TAG_COMPOUND);
			for (int i = 0; i < gridsTag.size(); i++) {
				Grid newGrid = Grid.load(gridsTag.getCompound(i), provider);
				loadedGrids.add(newGrid);
			}
		}
		INSTANCE.grids.addAll(loadedGrids);
		Set<Node> loadedNodes = new HashSet<>();
		if (compoundTag.contains("nodes")) {
			ListTag nodesTag = compoundTag.getList("nodes", Tag.TAG_COMPOUND);
			for (int i = 0; i < nodesTag.size(); i++) {
				loadedNodes.add(Node.load(nodesTag.getCompound(i), provider));
			}
		}
		loadedNodes.stream().forEach(node -> INSTANCE.addNode(node, true));
		//ProcessEnhancement.LOGGER.debug("Grids: {}",INSTANCE.grids.size());
		//INSTANCE.grids.forEach(grid -> ProcessEnhancement.LOGGER.debug(" - ID: {}; Valid: {}; ReplacedBy: {}",grid.getGridId(),grid.isValid(),(grid.getReplacedBy() != null) ? grid.getReplacedBy().toString() : "null"));
		//ProcessEnhancement.LOGGER.debug("Nodes: {}",INSTANCE.nodes.size());
		//INSTANCE.nodes.forEach(node -> ProcessEnhancement.LOGGER.debug(" - ID: {}; Level: {}, Pos: {}; Grid: {}; Neighbors: {}; Endpoints: {}",node.getNodeId().toString(),node.getLevelName(),node.getPos(),node.getRawGrid().getGridId().toString(),node.getNeighbors().size(),node.getLocalEndpoints().size()));
		return INSTANCE;
	}

	public Set<Node> getNodeSet(Grid grid) {
		Set<Node> nodeSet = new HashSet<>();
		Set<UUID> nodeIdSet = this.nodeMap.get(grid.getGridId());
		if (nodeIdSet != null) {
			nodeIdSet.stream().forEach(nodeId -> {
				Node node = this.getNodeById(nodeId);
				if (node != null) {
					nodeSet.add(node);
				} else {
					ProcessEnhancement.LOGGER.warn("Node {} not found!",nodeId);
				}
			});
		} else {
			ProcessEnhancement.LOGGER.debug("Grid {} not found in map",grid.getGridId());
		}
		return nodeSet;
	}

	public void removeNode(Node node) {
		ProcessEnhancement.LOGGER.info("Removing node: {}",node.getNodeId());
		Grid grid = node.getRawGrid();
		if (grid != null) {
			this.nodeMap.get(grid.getGridId()).remove(node.getNodeId());
		} else {
			ProcessEnhancement.LOGGER.debug("Null grid!");
		}
		this.nodes.remove(node);
		this.setDirty();
	}

	public void addNode(Node node, Boolean loading) {
		ProcessEnhancement.LOGGER.debug("Before: Nodes size: {}", this.nodes.size());
		this.nodes.forEach(temp -> ProcessEnhancement.LOGGER.debug(" - id: {}; grid: {}",temp.getNodeId(),temp.getRawGrid().getGridId()));
		if (node.getGrid() != null) {
			this.grids.add(node.getGrid());
		} else {
			ProcessEnhancement.LOGGER.warn("Load failure! Node {}", node.getNodeId());
		}
		this.nodes.add(node);
		ProcessEnhancement.LOGGER.debug("After: Nodes size: {}", this.nodes.size());
		this.nodes.forEach(temp -> ProcessEnhancement.LOGGER.debug(" - id: {}; grid: {}",temp.getNodeId(),temp.getRawGrid().getGridId()));
		this.nodeMap.computeIfAbsent(node.getGrid().getGridId(), k -> new HashSet<>()).add(node.getNodeId());
		ProcessEnhancement.LOGGER.debug("Map size: {}", node.getGrid(), this.nodeMap.size());
		this.nodeMap.forEach((key, value) -> ProcessEnhancement.LOGGER.debug(" - Grid: {}; Nodes: {}",key,value.size()));
		if (!loading) this.setDirty();
	}

	public void migrateGrid(Grid oldGrid, Grid newGrid) {
		Set<UUID> nodeSet = this.nodeMap.get(oldGrid.getGridId());
		this.nodeMap.get(newGrid.getGridId()).addAll(nodeSet);
		nodeSet.forEach(nodeId -> getNodeById(nodeId).setGrid(newGrid));
		this.nodeMap.remove(oldGrid.getGridId());
		this.setDirty();
	}

	public void enqueueReplacementMap(Grid grid, UUID replacedBy) {
		this.replacementQueue.add(new Tuple<>(grid, replacedBy));
	}

	public Node getNodeById(UUID nodeId) {
		Node result = this.nodes.stream().filter(node -> node.getNodeId().equals(nodeId)).findFirst().orElse(null);
		if (result == null) {
			ProcessEnhancement.LOGGER.warn("Node {} not found",nodeId);
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();

			for (int i = 1; i < Math.min(11, stack.length); i++) {
				ProcessEnhancement.LOGGER.debug(stack[i].toString());
			}
		}
		return result;
	}

	public Grid getGridById(UUID gridId) {
		Grid result = this.grids.stream().filter(grid -> grid.getGridId().equals(gridId)).findFirst().orElse(null);
		if (result == null) {
			ProcessEnhancement.LOGGER.warn("Grid {} not found",gridId);
		}
		return result;
	}

	public void reassignOrphaned(UUID gridId) {
		ProcessEnhancement.LOGGER.debug("Grid {} invalid.  Reassigning orphans", gridId);
		Set<UUID> orphanedNodes = this.nodes.stream().filter(node -> node.getRawGrid().equals(gridId)).map(node -> node.getNodeId()).collect(Collectors.toSet());
		ProcessEnhancement.LOGGER.debug(" - Orphan count: {}",orphanedNodes.size());
		orphanedNodes.stream().forEach(nodeId -> {
			this.getNodeById(nodeId).validate();
			ProcessEnhancement.LOGGER.debug(" - Node: {} has new Grid: {}",nodeId, this.getNodeById(nodeId).getGrid());
		});
	}

	public static boolean isLoaded() {
		return loaded;
	}

	public static void setLoaded() {
		loaded = true;
	}

	public void removeGridFromMap(UUID gridId) {
		this.nodeMap.remove(gridId);
	}

	public void addNodeToMap(UUID gridId, UUID nodeId) {
		this.nodeMap.computeIfAbsent(gridId,k-> new HashSet<>()).add(nodeId);
	}

	public void changeGrid(@NotNull UUID nodeId, @NotNull UUID oldGrid, @NotNull UUID newGrid) {
		this.nodeMap.computeIfAbsent(oldGrid, k -> new HashSet<>()).remove(nodeId);
		this.nodeMap.computeIfAbsent(newGrid, k -> new HashSet<>()).add(nodeId);
	}
}
