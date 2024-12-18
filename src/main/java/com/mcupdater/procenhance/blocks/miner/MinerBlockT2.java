package com.mcupdater.procenhance.blocks.miner;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinerBlockT2 extends MinerBlock {
    public static final MapCodec<MinerBlockT2> CODEC = simpleCodec(MinerBlockT2::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public MinerBlockT2(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MinerEntityT2(blockPos, blockState);
    }
}
