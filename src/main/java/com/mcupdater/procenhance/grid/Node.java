package com.mcupdater.procenhance.grid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Node {
		private final UUID nodeId;
		private BlockPos pos;
		private Grid grid;
		private final Set<Node> neighborNodes;
		private final Set<Endpoint> localEndpoints;

	public Node(BlockPos blockPos, Set<Node> neighborNodes, Set<Endpoint> endpoints) {
		this.nodeId = UUID.randomUUID();
		this.neighborNodes = neighborNodes;
		this.localEndpoints = endpoints;
		this.grid = calculateGrid();
		this.neighborNodes.forEach(node -> node.addNeighbor(this));
	}

	public Node(UUID nodeId) {
		this.nodeId = nodeId;
		this.neighborNodes = new HashSet<>();
		this.localEndpoints = new HashSet<>();
	}

	private void addNeighbor(Node newNeighbor) {
		this.neighborNodes.add(newNeighbor);
		this.validate();
	}

	private void removeNeighbor(Node neighbor) {
		this.neighborNodes.remove(neighbor);
		this.validate();
	}

	public Grid getGrid() {
		return (this.grid.isValid() ? this.grid : null);
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void invalidate() {
		GridManager.getInstance().removeNode(this);
		if (this.neighborNodes.size() > 1) {
			this.grid.invalidate();
			this.neighborNodes.forEach(node -> node.removeNeighbor(this));
		}
	}

	private Grid calculateGrid() {
		Set<Grid> grids = this.neighborNodes.stream().map(Node::getGrid).filter(Objects::nonNull).collect(Collectors.toSet());
		Grid finalGrid;
		if (grids.isEmpty()) {
			finalGrid = GridManager.getInstance().createGrid();
		} else {
			finalGrid = grids.stream().max(new GridSizeComparator()).get();
			grids.forEach(foundGrid -> { if (!foundGrid.equals(finalGrid)) foundGrid.mergeInto(finalGrid); });
		}
		return finalGrid;
	}

	private void validate() {
		if (!this.grid.isValid()) {
			this.grid = this.grid.getReplacedBy() != null ? this.grid.getReplacedBy() : calculateGrid();
			this.neighborNodes.forEach(Node::validate);
		}
	}

	public void updateGrid(Grid newGrid) {
		this.grid = newGrid;
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
		loadedNode.pos = NbtUtils.readBlockPos(compound, "pos").orElse(null);
		loadedNode.grid = GridManager.getInstance().getGridById(NbtUtils.loadUUID(Objects.requireNonNull(compound.get("grid"))));
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
				Node neighbor = GridManager.getInstance().getNodebyId(neighborId);
				if (neighbor != null) {
					loadedNode.neighborNodes.add(neighbor);
					neighbor.addNeighbor(loadedNode);
				}
			}
		}
		return loadedNode;
	}

	public CompoundTag saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		compound.putUUID("id", this.nodeId);
		compound.put("pos", NbtUtils.writeBlockPos(this.pos));
		compound.put("grid", NbtUtils.createUUID(grid.getGridId()));
		if (!this.localEndpoints.isEmpty()) {
			ListTag endpoints = new ListTag();
			this.localEndpoints.forEach(endpoint -> endpoints.add(endpoint.save(new CompoundTag(), registries)));
			compound.put("endpoints", endpoints);
		}
		if (!this.neighborNodes.isEmpty()) {
			ListTag neighbors = new ListTag();
			this.neighborNodes.forEach(node -> neighbors.add(NbtUtils.createUUID(node.getNodeId())));
			compound.put("neighbors", neighbors);
		}
		return compound;
	}

	public UUID getNodeId() {
		return this.nodeId;
	}
}

