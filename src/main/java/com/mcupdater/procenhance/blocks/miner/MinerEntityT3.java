package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.MINERT3_ENTITY;

public class MinerEntityT3 extends MinerEntity {

    public MinerEntityT3(BlockPos blockPos, BlockState blockState) {
        super(MINERT3_ENTITY.get(), blockPos, blockState, 4, 5);
    }
    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.advanced_miner");
    }
}
