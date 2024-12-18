package com.mcupdater.procenhance.blocks.lava_generator;

import com.mcupdater.procenhance.blocks.generator.GeneratorBlockT1;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LavaGeneratorBlockT1 extends LavaGeneratorBlock {
    public static final MapCodec<LavaGeneratorBlockT1> CODEC = simpleCodec(LavaGeneratorBlockT1::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public LavaGeneratorBlockT1(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LavaGeneratorEntityT1(blockPos, blockState);
    }
}
