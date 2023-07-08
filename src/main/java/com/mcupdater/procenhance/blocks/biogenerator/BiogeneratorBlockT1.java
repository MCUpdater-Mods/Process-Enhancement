package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BiogeneratorBlockT1 extends BiogeneratorBlock {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BiogeneratorEntityT1(blockPos, blockState);
    }
}
