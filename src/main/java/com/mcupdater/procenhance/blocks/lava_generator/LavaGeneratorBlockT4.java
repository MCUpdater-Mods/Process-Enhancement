package com.mcupdater.procenhance.blocks.lava_generator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LavaGeneratorBlockT4 extends LavaGeneratorBlock {
    public static final MapCodec<LavaGeneratorBlockT4> CODEC = simpleCodec(LavaGeneratorBlockT4::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public LavaGeneratorBlockT4(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LavaGeneratorEntityT4(blockPos, blockState);
    }
}
