package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.PUMPT3_ENTITY;

public class PumpEntityT3 extends PumpEntity {

    public PumpEntityT3(BlockPos blockPos, BlockState blockState) {
        super(PUMPT3_ENTITY.get(), blockPos, blockState, 4, 9);
    }
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_pump");
    }
}
