package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinerBlockT2 extends MinerBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MinerEntityT2(blockPos, blockState);
    }
}
