package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.MINERT2_ENTITY;

public class MinerEntityT2 extends MinerEntity {

    public MinerEntityT2(BlockPos blockPos, BlockState blockState) {
        super(MINERT2_ENTITY.get(), blockPos, blockState, 2, 3);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.intermediate_miner");
    }
}
