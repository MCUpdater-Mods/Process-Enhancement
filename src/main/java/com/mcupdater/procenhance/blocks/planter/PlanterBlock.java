package com.mcupdater.procenhance.blocks.planter;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlanterBlock extends AbstractMachineBlock {
	public static final MapCodec<PlanterBlock> CODEC = simpleCodec(PlanterBlock::new);

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public PlanterBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PlanterEntity(blockPos, blockState);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof PlanterEntity planter) {
				planter.tick(lvl, pos, state);
			}
		};
	}

	@Override
	protected void dropInventory(Level pLevel, BlockPos pPos) {
		if (pLevel.getBlockEntity(pPos) instanceof PlanterEntity entity) {
			for (int i = 0; i < 9; i++) {
				Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), entity.getInventory().getItem(i));
			}
		}
	}
}
