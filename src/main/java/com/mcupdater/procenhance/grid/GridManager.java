package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class GridManager extends SavedData {
	private static GridManager INSTANCE = new GridManager();
	private Map<Grid, Set<Node>> nodeMap = new HashMap<>();

	public GridManager() {
		ProcessEnhancement.LOGGER.info("GridManager init");
		setDirty();
	}

	public Grid createGrid() {
		Grid newGrid = new Grid();
		this.nodeMap.put(newGrid, new HashSet<>());
		return newGrid;
	}

	public static GridManager getInstance() {
		return INSTANCE;
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		ProcessEnhancement.LOGGER.info("GridManager save");
		return new CompoundTag();
	}

	public static GridManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
		return new GridManager();
	}

	public Set<Node> getNodeSet(Grid grid) {
		return this.nodeMap.get(grid);
	}

	public void removeNode(Node node) {
		this.nodeMap.get(node.getGrid()).remove(node);
	}

	public void addNode(Node node) {
		this.nodeMap.get(node.getGrid()).add(node);
	}

	public void migrateGrid(Grid oldGrid, Grid newGrid) {
		Set<Node> nodeSet = this.nodeMap.get(oldGrid);
		this.nodeMap.get(newGrid).addAll(nodeSet);
		nodeSet.stream().forEach(node -> node.updateGrid(newGrid));
		this.nodeMap.remove(oldGrid);
	}
}
