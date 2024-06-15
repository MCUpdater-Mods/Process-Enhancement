package com.mcupdater.procenhance.blocks.hydrator;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HydratorBlock extends AbstractMachineBlock {
	public HydratorBlock() {
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(10.0f));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new HydratorEntity(pPos, pState);
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult trace) {
		if (!pLevel.isClientSide) {
			BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
			if (blockEntity instanceof HydratorEntity) {
				Map<Direction, Component> adjacentNames = DataHelper.getAdjacentNames(pLevel, pPos);
				NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider) blockEntity, buf -> {
					buf.writeBlockPos(pPos);
					DataHelper.writeDirectionMap(buf, adjacentNames);
				});
			} else {
				return InteractionResult.FAIL;
			}
		}
		return InteractionResult.SUCCESS;
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
