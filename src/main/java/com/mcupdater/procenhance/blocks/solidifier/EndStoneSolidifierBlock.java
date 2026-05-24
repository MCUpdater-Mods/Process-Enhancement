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

import javax.annotation.Nullable;

public class EndStoneSolidifierBlock extends AbstractMachineBlock {
	public static final MapCodec<EndStoneSolidifierBlock> CODEC = simpleCodec(EndStoneSolidifierBlock::new);

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public EndStoneSolidifierBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new EndStoneSolidifierEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof EndStoneSolidifierEntity endstoneSolidifier) {
				endstoneSolidifier.tick(lvl, pos, state);
			}
		};
	}
}
