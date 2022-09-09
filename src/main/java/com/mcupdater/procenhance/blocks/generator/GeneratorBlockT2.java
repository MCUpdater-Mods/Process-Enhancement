package com.mcupdater.procenhance.blocks.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockT2 extends GeneratorBlock {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GeneratorEntityT2(blockPos, blockState);
    }
}
