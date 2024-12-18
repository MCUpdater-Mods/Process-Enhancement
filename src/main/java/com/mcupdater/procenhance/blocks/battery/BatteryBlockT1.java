package com.mcupdater.procenhance.blocks.battery;

import com.mcupdater.procenhance.blocks.autopackager.PackagerBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockT1 extends BatteryBlock {
    public static final MapCodec<BatteryBlockT1> CODEC = simpleCodec(BatteryBlockT1::new);

    public BatteryBlockT1(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BatteryEntityT1(pPos, pState);
    }

}
