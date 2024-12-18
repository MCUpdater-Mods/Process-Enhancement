package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CobblestoneSolidifierBlock extends AbstractMachineBlock {
    public static final MapCodec<CobblestoneSolidifierBlock> CODEC = simpleCodec(CobblestoneSolidifierBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public CobblestoneSolidifierBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CobblestoneSolidifierEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof CobblestoneSolidifierEntity cobblestoneSolidifier) {
                cobblestoneSolidifier.tick(lvl, pos, state);
            }
        };
    }


}
