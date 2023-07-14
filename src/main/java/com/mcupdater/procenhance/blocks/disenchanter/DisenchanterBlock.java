package com.mcupdater.procenhance.blocks.disenchanter;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.procenhance.blocks.grinder.GrinderEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class DisenchanterBlock extends AbstractMachineBlock {
    public DisenchanterBlock() {
        super(Properties.of(Material.STONE).sound(SoundType.STONE).strength(15.0f).lightLevel((blockState) -> 7));
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof DisenchanterEntity disenchanterEntity) {
                Containers.dropContents(level, blockPos, disenchanterEntity.getInventory());
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
