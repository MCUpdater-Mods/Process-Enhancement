package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.Random;

public class ElectricFurnaceBlock extends AbstractMachineBlock {
    public ElectricFurnaceBlock() {
        super(Properties.of(Material.STONE).sound(SoundType.STONE).strength(5.0f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ElectricFurnaceEntity(blockPos, blockState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(ACTIVE)) {
            double x = (double) pPos.getX() + 0.5D;
            double y = (double) pPos.getY();
            double z = (double) pPos.getZ() + 0.5D;
            pLevel.playLocalSound(x, y, z, Registration.MACHINE_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double horizontalRand = pRandom.nextDouble() * 0.6D - 0.3D;
            double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : horizontalRand;
            double yOffset = pRandom.nextDouble() * 6.0D / 16.0D;
            double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : horizontalRand;
            pLevel.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean flag) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof ElectricFurnaceEntity electricFurnaceEntity) {
                Containers.dropContents(level, blockPos, electricFurnaceEntity.getInventory());
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, flag);
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof ElectricFurnaceEntity furnace) {
                furnace.tick(lvl, pos, state);
            }
        };
    }
}
