package com.mcupdater.procenhance.blocks.battery;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockT2 extends BatteryBlock {
    public static final MapCodec<BatteryBlockT2> CODEC = simpleCodec(BatteryBlockT2::new);

    public BatteryBlockT2(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BatteryEntityT2(pPos, pState);
    }
}
