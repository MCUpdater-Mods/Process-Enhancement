package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.MINERT1_ENTITY;

public class MinerEntityT1 extends MinerEntity {

    public MinerEntityT1(BlockPos blockPos, BlockState blockState) {
        super(MINERT1_ENTITY.get(), blockPos, blockState, 1, 1);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_miner");
    }
}
