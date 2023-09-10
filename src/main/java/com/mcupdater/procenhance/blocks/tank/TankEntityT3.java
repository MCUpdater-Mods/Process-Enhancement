package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.TANKT3_ENTITY;

public class TankEntityT3 extends TankEntity {

    public TankEntityT3(BlockPos blockPos, BlockState blockState) {
        super(TANKT3_ENTITY.get(), blockPos, blockState, 100);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_tank");
    }
}
