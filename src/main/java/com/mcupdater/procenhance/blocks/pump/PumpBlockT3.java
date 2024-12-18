package com.mcupdater.procenhance.blocks.pump;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PumpBlockT3 extends PumpBlock {
    public static final MapCodec<PumpBlockT3> CODEC = simpleCodec(PumpBlockT3::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public PumpBlockT3(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PumpEntityT3(blockPos, blockState);
    }
}
