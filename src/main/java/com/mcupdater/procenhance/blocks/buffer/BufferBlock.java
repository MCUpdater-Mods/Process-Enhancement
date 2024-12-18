package com.mcupdater.procenhance.blocks.buffer;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BufferBlock extends AbstractMachineBlock {
    public static final MapCodec<BufferBlock> CODEC = simpleCodec(BufferBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public BufferBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BufferEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof BufferEntity bufferEntity) {
                Containers.dropContents(level, blockPos, bufferEntity.getItemHandler());
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof BufferEntity bufferEntity) {
                bufferEntity.tick(lvl, pos, state);
            }
        };
    }
}
