package com.mcupdater.procenhance.blocks.hydrator;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HydratorBlock extends AbstractMachineBlock {
	public static final MapCodec<HydratorBlock> CODEC = simpleCodec(HydratorBlock::new);

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public HydratorBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new HydratorEntity(pPos, pState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof HydratorEntity hydrator) {
				hydrator.tick(lvl, pos, state);
			}
		};
	}
}
