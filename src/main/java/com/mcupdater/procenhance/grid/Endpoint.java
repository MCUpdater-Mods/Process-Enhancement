package com.mcupdater.procenhance.grid;

import net.minecraft.core.BlockPos;

public record Endpoint(BlockPos blockPos, Endpoint.Type type) {

	public enum Type {SOURCE, SINK, STORAGE}
}
