package com.mcupdater.procenhance.blocks.grinder;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GrinderBlockT2 extends GrinderBlock {
    public static final MapCodec<GrinderBlockT2> CODEC = simpleCodec(GrinderBlockT2::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public GrinderBlockT2(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GrinderEntityT2(blockPos, blockState);
    }
}
