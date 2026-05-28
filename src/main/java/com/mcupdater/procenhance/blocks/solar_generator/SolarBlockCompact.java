package com.mcupdater.procenhance.blocks.solar_generator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarBlockCompact extends SolarBlock {
	public static final MapCodec<SolarBlockCompact> CODEC = simpleCodec(SolarBlockCompact::new);

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public SolarBlockCompact(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SolarEntityCompact(pos, state);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(ACTIVE)) {
			double x = (double) pos.getX() + 0.5D;
			double y = (double) pos.getY();
			double z = (double) pos.getZ() + 0.5D;

			Direction direction = Direction.UP;
			Direction.Axis axis = direction.getAxis();
			double horizontalRand = random.nextDouble() * 0.3D - 0.1D;
			double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : horizontalRand;
			double yOffset = random.nextDouble() * 1.2D / 16.0D;
			double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : horizontalRand;
			level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), y + random.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), y + random.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public @NotNull RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(5,0,5,11,2,11);
	}
}
