package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.PUMPT1_ENTITY;

public class PumpEntityT1 extends PumpEntity {

    public PumpEntityT1(BlockPos blockPos, BlockState blockState) {
        super(PUMPT1_ENTITY.get(), blockPos, blockState, 1, 1);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_pump");
    }
}
