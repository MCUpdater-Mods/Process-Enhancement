package com.mcupdater.procenhance.blocks.pump;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PumpBlockT3 extends PumpBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PumpEntityT3(blockPos, blockState);
    }
}
