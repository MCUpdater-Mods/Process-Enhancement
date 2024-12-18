package com.mcupdater.procenhance.blocks.biogenerator;

import com.mcupdater.procenhance.blocks.generator.GeneratorBlockT1;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BiogeneratorBlockT1 extends BiogeneratorBlock {
    public static final MapCodec<BiogeneratorBlockT1> CODEC = simpleCodec(BiogeneratorBlockT1::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public BiogeneratorBlockT1(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BiogeneratorEntityT1(blockPos, blockState);
    }
}
