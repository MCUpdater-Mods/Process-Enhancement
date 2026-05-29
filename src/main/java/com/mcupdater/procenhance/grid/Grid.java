package com.mcupdater.procenhance.grid;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Grid {
	private	UUID gridId;
	private boolean valid = true;
	private Grid replacedBy = null;
	private Set<Endpoint> aggregateEndpoints = new HashSet<>();

	public Grid() {
		this.gridId = UUID.randomUUID();
	}

	public boolean isValid() {
		return valid;
	}

	public void invalidate() {
		valid = false;
	}

	public void mergeInto(Grid grid) {
		this.replacedBy = grid;
		this.invalidate();
	}

	public Grid getReplacedBy() {
		return replacedBy;
	}

	public long getSize() {
		return GridManager.getInstance().getNodeSet(this).size();
	}

	public void rebuildEndpoints() {
		aggregateEndpoints.clear();
		GridManager.getInstance().getNodeSet(this).stream().forEach(node -> aggregateEndpoints.addAll(node.getLocalEndpoints()));
	}
}
