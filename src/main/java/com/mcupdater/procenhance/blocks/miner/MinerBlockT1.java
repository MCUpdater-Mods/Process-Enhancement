package com.mcupdater.procenhance.blocks.miner;

import com.mcupdater.procenhance.blocks.tank.TankBlockT1;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinerBlockT1 extends MinerBlock {
    public static final MapCodec<MinerBlockT1> CODEC = simpleCodec(MinerBlockT1::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public MinerBlockT1(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MinerEntityT1(blockPos, blockState);
    }
}
