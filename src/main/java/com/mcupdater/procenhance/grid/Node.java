package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Node {
		private final UUID nodeId;
		private String levelName;
		private BlockPos pos;
		private Grid grid;
		private final Set<UUID> neighborNodes = new HashSet<>();
		private final Set<Endpoint> localEndpoints = new HashSet<>();

	public Node(BlockPos blockPos, Level level, Set<Node> neighborNodes, Set<Endpoint> endpoints) {
		this.nodeId = UUID.randomUUID();
		this.levelName = level.dimension().location().toString();
		this.pos = blockPos;
		this.neighborNodes.clear();
		this.neighborNodes.addAll(neighborNodes.stream().map(Node::getNodeId).collect(Collectors.toSet()));
		this.localEndpoints.clear();
		this.localEndpoints.addAll(endpoints);
		this.setGrid(calculateGrid());
		this.neighborNodes.forEach(node -> {
			if (node != null) GridManager.getInstance().getNodeById(node).addNeighbor(this);
		});
		//ProcessEnhancement.LOGGER.debug("Grid: {} - Node: {}",this.grid.getGridId(),this.nodeId);
		GridManager.getInstance().addNode(this, false);
	}

	// This constructor should only be used while loading
	private Node(UUID nodeId) {
		this.nodeId = nodeId;
	}

	public void addNeighbor(Node newNeighbor) {
		this.neighborNodes.add(newNeighbor.getNodeId());
		this.validate();
	}

	private void removeNeighbor(Node neighbor) {
		this.neighborNodes.remove(neighbor);
		this.validate();
	}

	public Grid getGrid() {
		if (this.grid == null) {
			ProcessEnhancement.LOGGER.error("Null Grid! Node {}",this.nodeId.toString());
			return null;
		} else {
			Grid found = (this.grid.isValid() ? this.grid : this.grid.getReplacedBy());
			if (found == null) {
				GridManager.getInstance().reassignOrphaned(this.grid.getGridId());
			}
			return found;
		}
	}

	public void setGrid(Grid newGrid) {
		if (this.grid != null) {
			GridManager.getInstance().changeGrid(this.getNodeId(), this.grid.getGridId(), newGrid.getGridId());
			this.grid = newGrid;
		} else {
			this.grid = newGrid;
			GridManager.getInstance().addNodeToMap(this.grid.getGridId(), this.nodeId);
		}
	}

	public void invalidate() {
		if (this.neighborNodes.size() > 1) {
			this.grid.invalidate();
		}
		this.neighborNodes.forEach(nodeId -> {
			Node node = GridManager.getInstance().getNodeById(nodeId);
			if (node != null) {
				node.removeNeighbor(this);
				// node.validate();
			}
		});
		GridManager.getInstance().removeNode(this);
	}

	private Grid calculateGrid() {
		Set<UUID> invalidNeighbors = new HashSet<>();
		Set<Grid> grids = this.neighborNodes.isEmpty() ? new HashSet<>() : this.neighborNodes.stream().filter(nodeId -> {
			Node node = GridManager.getInstance().getNodeById(nodeId);
			if (node == null) {
				invalidNeighbors.add(nodeId);
			}
			return node != null && node.getGrid() != null;
		}).map(nodeId -> GridManager.getInstance().getNodeById(nodeId).getGrid()).collect(Collectors.toSet());
		Grid finalGrid;
		if (grids.isEmpty()) {
			finalGrid = GridManager.getInstance().createGrid();
		} else {
			finalGrid = grids.stream().max(new GridSizeComparator()).get();
			grids.forEach(foundGrid -> { if (!foundGrid.equals(finalGrid)) foundGrid.mergeInto(finalGrid); });
		}
		this.neighborNodes.removeAll(invalidNeighbors);
		return finalGrid;
	}

	public void validate() {
		if (!this.grid.isValid()) {
			this.setGrid(this.grid.getReplacedBy() != null ? this.grid.getReplacedBy() : calculateGrid());
		}
		long gridSize = this.grid.getSize();
		if (gridSize > 1 && this.neighborNodes.isEmpty()) { // Disconnected nodes should not be possible
			try {
				Level level = ProcessEnhancement.serverInstance.getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(this.levelName)));
				for (Direction side : Direction.values()) {
					if (level.getBlockEntity(this.pos.relative(side)) instanceof INodeHolder holder) {
						this.neighborNodes.add(holder.getNode().getNodeId());
					}
				}
			} catch (Exception e) {
				ProcessEnhancement.LOGGER.error("Lookup error", e);
			}
		}
		if (gridSize < this.neighborNodes.size() || (gridSize > 1 && this.neighborNodes.isEmpty()) || !GridManager.getInstance().getNodeSet(this.grid).stream().map(Node::getNodeId).collect(Collectors.toSet()).containsAll(this.neighborNodes)) {
			this.setGrid(calculateGrid());
		}
	}

	public void addEndpoint(Endpoint newEndpoint) {
		this.localEndpoints.add(newEndpoint);
	}

	public void removeEndpoint(Endpoint oldEndpoint) {
		this.localEndpoints.remove(oldEndpoint);
	}

	public Set<Endpoint> getLocalEndpoints() {
		return this.localEndpoints;
	}

	public static Node load(CompoundTag compound, HolderLookup.Provider registries) {
		Node loadedNode = new Node(compound.getUUID("id"));
		loadedNode.levelName = compound.getString("levelName");
		loadedNode.pos = NbtUtils.readBlockPos(compound, "pos").orElse(null);
		UUID gridId = NbtUtils.loadUUID(compound.get("grid"));
		loadedNode.grid = GridManager.getInstance().getGridById(gridId);
		if (compound.contains("endpoints")) {
			ListTag listEndpoints = compound.getList("endpoints", Tag.TAG_COMPOUND);
			for (int i = 0; i < listEndpoints.size(); i++) {
				loadedNode.localEndpoints.add(Endpoint.load(listEndpoints.getCompound(i), registries));
			}
		}
		if (compound.contains("neighbors")) {
			ListTag listNeighbors = compound.getList("neighbors", Tag.TAG_INT_ARRAY);
			for (Tag neighborTag : listNeighbors) {
				UUID neighborId = NbtUtils.loadUUID(neighborTag);
				Node neighbor = GridManager.getInstance().getNodeById(neighborId);
				if (neighbor != null) {
					loadedNode.neighborNodes.add(neighbor.getNodeId());
					neighbor.addNeighbor(loadedNode);
				}
			}
		}
		return loadedNode;
	}

	public CompoundTag saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		compound.putUUID("id", this.nodeId);
		compound.putString("levelName", this.levelName);
		compound.put("pos", NbtUtils.writeBlockPos(this.pos));
		compound.put("grid", NbtUtils.createUUID(grid.getGridId()));
		if (!this.localEndpoints.isEmpty()) {
			ListTag endpoints = new ListTag();
			this.localEndpoints.forEach(endpoint -> endpoints.add(endpoint.save(new CompoundTag(), registries)));
			compound.put("endpoints", endpoints);
		}
		if (!this.neighborNodes.isEmpty()) {
			ListTag neighbors = new ListTag();
			this.neighborNodes.stream().filter(Objects::nonNull).forEach(node -> neighbors.add(NbtUtils.createUUID(node)));
			compound.put("neighbors", neighbors);
		}
		return compound;
	}

	public UUID getNodeId() {
		return this.nodeId;
	}

	public Grid getRawGrid() {
		return this.grid;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public Set<UUID> getNeighbors() {
		return this.neighborNodes;
	}

	public void clearEndpoints() {
		this.localEndpoints.clear();
	}

	public void updateGridEndpoints() {
		this.getGrid().rebuildEndpoints();
	}
}

