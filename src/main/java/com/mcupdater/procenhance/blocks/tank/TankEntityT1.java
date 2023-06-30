package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.TANKT1_ENTITY;

public class TankEntityT1 extends TankEntity {

    public TankEntityT1(BlockPos blockPos, BlockState blockState) {
        super(TANKT1_ENTITY.get(), blockPos, blockState, 1);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_tank");
    }
}
