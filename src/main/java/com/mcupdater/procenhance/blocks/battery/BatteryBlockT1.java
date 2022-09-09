package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockT1 extends BatteryBlock {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BatteryEntityT1(pPos, pState);
    }
}
