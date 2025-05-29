package com.mcupdater.procenhance.blocks.solar_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_SOLAR_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.SOLARGENERATORT4_ENTITY;

public class SolarEntityT4 extends SolarEntity {

	public SolarEntityT4(BlockPos blockPos, BlockState blockState) {
		super(SOLARGENERATORT4_ENTITY.get(), blockPos, blockState);
		setup(BASIC_SOLAR_GENERATOR_PER_TICK.get() * 8);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.industrial_solar_generator");
	}
}
