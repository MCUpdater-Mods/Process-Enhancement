package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinerBlockT1 extends MinerBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MinerEntityT1(blockPos, blockState);
    }
}
