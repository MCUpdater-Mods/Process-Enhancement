package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PumpBlockT4 extends PumpBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PumpEntityT4(blockPos, blockState);
    }
}
