package com.mcupdater.procenhance.blocks.lava_generator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LavaGeneratorBlockT3 extends LavaGeneratorBlock {
    public static final MapCodec<LavaGeneratorBlockT3> CODEC = simpleCodec(LavaGeneratorBlockT3::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public LavaGeneratorBlockT3(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LavaGeneratorEntityT3(blockPos, blockState);
    }
}
