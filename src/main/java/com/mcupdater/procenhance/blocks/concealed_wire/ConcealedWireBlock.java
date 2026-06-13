package com.mcupdater.procenhance.blocks.concealed_wire;

import com.mcupdater.procenhance.grid.INodeBlock;
import com.mcupdater.procenhance.grid.INodeHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class ConcealedWireBlock extends Block implements EntityBlock, INodeBlock {

	public ConcealedWireBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ConcealedWireEntity(pos, state);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && stack.is(Tags.Items.TOOLS_WRENCH)) {
			ConcealedWireEntity entity = (ConcealedWireEntity) level.getBlockEntity(pos);
			if (entity != null) {
				Item target = player.getOffhandItem().getItem();
				if (target.equals(Items.AIR)) {
					entity.next = level.getBlockState(pos);
					this.setMimic(level, pos);
					return ItemInteractionResult.SUCCESS;
				}
				if (target instanceof BlockItem blockTarget) {
					if (!blockTarget.getBlock().defaultBlockState().getShape(level, pos).equals(Block.box(0, 0, 0, 16, 16, 16))) {
						return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
					}
					if (!(blockTarget.getBlock() instanceof EntityBlock)) {
						if (entity.mimic.is(blockTarget.getBlock())) {
							return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
						}
						entity.next = blockTarget.getBlock().getStateForPlacement(new BlockPlaceContext(level, player, hand, stack, hitResult));
						this.setMimic(level, pos);
						return ItemInteractionResult.SUCCESS;
					}
				}
			}
		}
		if (hand == InteractionHand.OFF_HAND && player.getMainHandItem().is(Tags.Items.TOOLS_WRENCH) ){
			return ItemInteractionResult.CONSUME;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	private void setMimic(Level level, BlockPos pos) {
		ConcealedWireEntity entity = (ConcealedWireEntity) level.getBlockEntity(pos);
		if (entity != null && entity.mimic != null && entity.next != null && !entity.next.equals(entity.mimic)) {
			BlockState previous = entity.mimic;
			entity.mimic = entity.next;
			level.setBlockAndUpdate(pos, entity.getBlockState());
			entity.updateBlock();
		}
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof ConcealedWireEntity wire) {
				wire.tick(lvl, pos);
			}
		};
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (!state.getBlock().equals(newState.getBlock()) && level.getBlockEntity(pos) instanceof INodeHolder entity) {
			entity.onRemove();
			level.invalidateCapabilities(pos);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}
}
