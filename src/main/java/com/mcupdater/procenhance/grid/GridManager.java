package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class GridManager extends SavedData {

	public GridManager() {
		ProcessEnhancement.LOGGER.info("GridManager init");
		setDirty();
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		ProcessEnhancement.LOGGER.info("GridManager save");
		return new CompoundTag();
	}

	public static GridManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
		return new GridManager();
	}
}
