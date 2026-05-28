package com.mcupdater.procenhance.blocks.lantern;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.TERRACOTTA_LANTERN_ENTITY;

public class TerracottaLanternEntity extends LanternEntity {

	public TerracottaLanternEntity(BlockPos blockPos, BlockState blockState) {
		super(TERRACOTTA_LANTERN_ENTITY.get(), blockPos, blockState);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.electric_lantern");
	}
}
