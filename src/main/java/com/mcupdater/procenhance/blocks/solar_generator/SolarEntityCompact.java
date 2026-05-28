package com.mcupdater.procenhance.blocks.solar_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.COMPACTSOLARGENERATOR_ENTITY;

public class SolarEntityCompact extends SolarEntity {

	public SolarEntityCompact(BlockPos blockPos, BlockState blockState) {
		super(COMPACTSOLARGENERATOR_ENTITY.get(), blockPos, blockState);
		setup(1);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.compact_solar_generator");
	}
}
