package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.MINERT4_ENTITY;

public class MinerEntityT4 extends MinerEntity {

    public MinerEntityT4(BlockPos blockPos, BlockState blockState) {
        super(MINERT4_ENTITY.get(), blockPos, blockState, 8, 15);
    }
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.industrial_miner");
    }
}
