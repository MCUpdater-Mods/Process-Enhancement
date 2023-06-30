package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.PUMPT2_ENTITY;

public class PumpEntityT2 extends PumpEntity {

    public PumpEntityT2(BlockPos blockPos, BlockState blockState) {
        super(PUMPT2_ENTITY.get(), blockPos, blockState, 2, 3);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.intermediate_pump");
    }
}
