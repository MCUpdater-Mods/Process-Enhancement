package com.mcupdater.procenhance.grid;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Grid {
	private	final UUID gridId;
	private boolean valid = true;
	private Grid replacedBy = null;
	private final Set<Endpoint> aggregateEndpoints = new HashSet<>();

	public Grid() {
		this(UUID.randomUUID());
	}

	public Grid(UUID gridId) {
		this.gridId = gridId;
	}

	public UUID getGridId() {
		return this.gridId;
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
		GridManager.getInstance().getNodeSet(this).forEach(node -> aggregateEndpoints.addAll(node.getLocalEndpoints()));
		GridManager.getInstance().setDirty();
	}

	public Set<Endpoint> getEndpoints() {
		return this.aggregateEndpoints;
	}

	public static Grid load(CompoundTag compound, HolderLookup.Provider provider) {
		Grid toLoad = new Grid(compound.getUUID("id"));
		toLoad.valid = compound.getBoolean("valid");
		if (compound.contains("replacedBy")) {
			GridManager.getInstance().enqueueReplacementMap(toLoad, compound.getUUID("replacedBy"));
		}
		return toLoad;
	}

	public CompoundTag saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		compound.putUUID("id",this.gridId);
		compound.putBoolean("valid",this.valid);
		if (replacedBy != null) {
			compound.putUUID("replacedBy", replacedBy.getGridId());
		}
		return compound;
	}

	public void setReplacedBy(Grid grid) {
		this.replacedBy = grid;
	}
}
