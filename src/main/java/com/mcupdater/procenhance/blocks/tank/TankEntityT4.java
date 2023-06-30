package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.TANKT4_ENTITY;

public class TankEntityT4 extends TankEntity {

    public TankEntityT4(BlockPos blockPos, BlockState blockState) {
        super(TANKT4_ENTITY.get(), blockPos, blockState, 1000);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.industrial_tank");
    }
}
