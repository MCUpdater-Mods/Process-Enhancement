package com.mcupdater.procenhance.blocks.solar_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_SOLAR_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.SOLARGENERATORT1_ENTITY;

public class SolarEntityT1 extends SolarEntity {

	public SolarEntityT1(BlockPos blockPos, BlockState blockState) {
		super(SOLARGENERATORT1_ENTITY.get(), blockPos, blockState);
		setup(BASIC_SOLAR_GENERATOR_PER_TICK.get());
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.basic_solar_generator");
	}
}
