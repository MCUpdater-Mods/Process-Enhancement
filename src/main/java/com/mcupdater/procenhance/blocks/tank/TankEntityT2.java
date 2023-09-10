package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.TANKT2_ENTITY;

public class TankEntityT2 extends TankEntity {

    public TankEntityT2(BlockPos blockPos, BlockState blockState) {
        super(TANKT2_ENTITY.get(), blockPos, blockState, 10);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.intermediate_tank");
    }
}
