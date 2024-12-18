package com.mcupdater.procenhance.blocks.disenchanter;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisenchanterBlock extends AbstractMachineBlock {
    public static final MapCodec<DisenchanterBlock> CODEC = simpleCodec(DisenchanterBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public DisenchanterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof DisenchanterEntity disenchanterEntity) {
                Containers.dropContents(level, blockPos, disenchanterEntity.getItemHandler());
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DisenchanterEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof DisenchanterEntity disenchanter) {
                disenchanter.tick(lvl, pos, state);
            }
        };
    }
}
