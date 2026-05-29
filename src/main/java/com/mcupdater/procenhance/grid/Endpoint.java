package com.mcupdater.procenhance.grid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public record Endpoint(BlockPos blockPos, Endpoint.Type type) {

	public static Endpoint load(CompoundTag compound, HolderLookup.Provider provider) {
		return new Endpoint(NbtUtils.readBlockPos(compound,"blockPos").orElse(null), Endpoint.Type.values()[compound.getByte("type")]);
	}

	public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
		compound.put("blockPos", NbtUtils.writeBlockPos(this.blockPos));
		compound.putByte("type", (byte) type.ordinal());
		return compound;
	}

	public enum Type {SOURCE, SINK, STORAGE}
}
