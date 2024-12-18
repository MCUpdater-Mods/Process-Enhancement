package com.mcupdater.procenhance.blocks.grinder;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GrinderBlockT4 extends GrinderBlock {
    public static final MapCodec<GrinderBlockT4> CODEC = simpleCodec(GrinderBlockT4::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public GrinderBlockT4(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GrinderEntityT4(blockPos, blockState);
    }
}
