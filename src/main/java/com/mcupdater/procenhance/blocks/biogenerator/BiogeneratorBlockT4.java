package com.mcupdater.procenhance.blocks.biogenerator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BiogeneratorBlockT4 extends BiogeneratorBlock {
    public static final MapCodec<BiogeneratorBlockT4> CODEC = simpleCodec(BiogeneratorBlockT4::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public BiogeneratorBlockT4(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BiogeneratorEntityT4(blockPos, blockState);
    }
}
