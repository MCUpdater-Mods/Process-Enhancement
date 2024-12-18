package com.mcupdater.procenhance.blocks.pump;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PumpBlockT4 extends PumpBlock {
    public static final MapCodec<PumpBlockT4> CODEC = simpleCodec(PumpBlockT4::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public PumpBlockT4(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PumpEntityT4(blockPos, blockState);
    }
}
