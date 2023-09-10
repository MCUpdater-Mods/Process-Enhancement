package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.PUMPT4_ENTITY;

public class PumpEntityT4 extends PumpEntity {

    public PumpEntityT4(BlockPos blockPos, BlockState blockState) {
        super(PUMPT4_ENTITY.get(), blockPos, blockState, 8, 15);
    }
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.industrial_pump");
    }
}
