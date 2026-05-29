package com.mcupdater.procenhance.grid;

import net.minecraft.core.BlockPos;

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
		this.neighborNodes.stream().forEach(node -> node.addNeighbor(this));
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

	public void invalidate() {
		GridManager.getInstance().removeNode(this);
		if (this.neighborNodes.size() > 1) {
			this.grid.invalidate();
			this.neighborNodes.stream().forEach(node -> node.removeNeighbor(this));
		}
	}

	private Grid calculateGrid() {
		Set<Grid> grids = this.neighborNodes.stream().map(Node::getGrid).filter(Objects::nonNull).collect(Collectors.toSet());
		Grid finalGrid;
		if (grids.isEmpty()) {
			finalGrid = GridManager.getInstance().createGrid();
		} else {
			finalGrid = grids.stream().sorted(new GridSizeComparator().reversed()).findFirst().get();
			grids.stream().forEach(foundGrid -> { if (!foundGrid.equals(finalGrid)) foundGrid.mergeInto(finalGrid); });
		}
		return finalGrid;
	}

	private void validate() {
		if (!this.grid.isValid()) {
			this.grid = this.grid.getReplacedBy() != null ? this.grid.getReplacedBy() : calculateGrid();
			this.neighborNodes.stream().forEach(Node::validate);
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
}

