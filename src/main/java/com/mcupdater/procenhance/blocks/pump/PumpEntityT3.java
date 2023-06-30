package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.PUMPT3_ENTITY;

public class PumpEntityT3 extends PumpEntity {

    public PumpEntityT3(BlockPos blockPos, BlockState blockState) {
        super(PUMPT3_ENTITY.get(), blockPos, blockState, 4, 5);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.advanced_pump");
    }
}
