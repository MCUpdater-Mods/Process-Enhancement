package com.mcupdater.procenhance.grid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public record Endpoint(String levelId, BlockPos blockPos, Direction side, Endpoint.Type type) {

	public static Endpoint load(CompoundTag compound, HolderLookup.Provider provider) {
		return new Endpoint(compound.getString("levelId"),NbtUtils.readBlockPos(compound,"blockPos").orElse(null), Direction.byName(compound.getString("side")), Endpoint.Type.values()[compound.getByte("type")]);
	}

	public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
		compound.putString("levelId", this.levelId);
		compound.put("blockPos", NbtUtils.writeBlockPos(this.blockPos));
		compound.putString("side", this.side.getName());
		compound.putByte("type", (byte) type.ordinal());
		return compound;
	}

	public enum Type {SOURCE, SINK, STORAGE}
}
