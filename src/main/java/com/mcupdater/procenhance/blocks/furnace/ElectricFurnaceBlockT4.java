package com.mcupdater.procenhance.blocks.furnace;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElectricFurnaceBlockT4 extends ElectricFurnaceBlock {
    public static final MapCodec<ElectricFurnaceBlockT4> CODEC = simpleCodec(ElectricFurnaceBlockT4::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public ElectricFurnaceBlockT4(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ElectricFurnaceEntityT4(blockPos, blockState);
    }
}
