package com.mcupdater.procenhance.blocks.solar_generator;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public abstract class SolarBlock extends AbstractMachineBlock {
	public static Properties defaultProperties() {
		return Properties.of()
				.mapColor(MapColor.STONE)
				.sound(SoundType.STONE)
				.strength(5.0f)
				.requiresCorrectToolForDrops();
	}

	public SolarBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(ACTIVE)) {
			double x = (double) pos.getX() + 0.5D;
			double y = (double) pos.getY();
			double z = (double) pos.getZ() + 0.5D;

			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			double horizontalRand = random.nextDouble() * 0.6D - 0.3D;
			double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : horizontalRand;
			double yOffset = random.nextDouble() * 6.0D / 16.0D;
			double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : horizontalRand;
			level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), y + random.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + (axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), y + random.nextDouble(), z + (axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : (random.nextDouble() * 0.6D - 0.3D)), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof SolarEntity solar) {
				solar.tick(lvl, pos, state);
			}
		};
	}
}
