package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockT4 extends BatteryBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BatteryEntityT4(pPos, pState);
    }
}
