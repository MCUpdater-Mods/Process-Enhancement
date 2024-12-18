package com.mcupdater.procenhance.blocks.stonecutter;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.setup.Registration;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ElectricStonecutterBlock extends AbstractMachineBlock {
    public static final MapCodec<ElectricStonecutterBlock> CODEC = simpleCodec(ElectricStonecutterBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public ElectricStonecutterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ElectricStonecutterEntity(pPos, pState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(ACTIVE)) {
            double x = (double) pPos.getX() + 0.5D;
            double y = (double) pPos.getY();
            double z = (double) pPos.getZ() + 0.5D;
            pLevel.playLocalSound(x, y, z, Registration.MACHINE_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(x, y, z, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double horizontalRand = pRandom.nextDouble() * 0.6D - 0.3D;
            double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : horizontalRand;
            double yOffset = pRandom.nextDouble() * 6.0D / 16.0D;
            double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : horizontalRand;
            pLevel.addParticle(ParticleTypes.ASH, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), y + pRandom.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), y + pRandom.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (pRandom.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void dropInventory(Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof ElectricStonecutterEntity entity) {
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), entity.getInventory().getItem(0));
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), entity.getInventory().getItem(1));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof ElectricStonecutterEntity stonecutter) {
                stonecutter.tick(lvl, pos, state);
            }
        };
    }
}
