package com.mcupdater.procenhance.blocks.grinder;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public abstract class GrinderBlock extends AbstractMachineBlock {

    public GrinderBlock() {
        super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(10.0f));
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(ACTIVE)) {
            double x = (double) pPos.getX() + 0.5D;
            double y = (double) pPos.getY();
            double z = (double) pPos.getZ() + 0.5D;
            pLevel.playLocalSound(x, y, z, Registration.MACHINE_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);

            Direction direction = pState.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double horizontalRand = pRandom.nextDouble() * 0.6D - 0.3D;
            double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : horizontalRand;
            double yOffset = pRandom.nextDouble() * 6.0D / 16.0D;
            double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : horizontalRand;
            pLevel.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.ASH, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), y + pRandom.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), y + pRandom.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean flag) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof GrinderEntity grinderEntity) {
                Containers.dropContents(level, blockPos, grinderEntity.getInventory());
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, flag);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof GrinderEntity grinder) {
                grinder.tick(lvl, pos, state);
            }
        };
    }
}