package com.mcupdater.procenhance.items.tools.stairmaker;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.StairmakerRecipe;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.gameevent.GameEvent;

import static net.minecraft.world.level.block.StairBlock.FACING;
import static net.minecraft.world.level.block.StairBlock.HALF;

public class StairmakerItem extends Item {
	public StairmakerItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState target = level.getBlockState(blockPos);
		Direction direction = context.getClickedFace();
		if (context.getPlayer().isDiscrete() && target.getBlock() instanceof StairBlock) {
			BlockState newState;
			Direction currentFacing = target.getValue(FACING);
			Half currentHalf = target.getValue(HALF);
			if (currentFacing != context.getHorizontalDirection()) {
				newState = target.setValue(FACING, context.getHorizontalDirection());
			} else {
				newState = target.setValue(HALF, currentHalf == Half.TOP ? Half.BOTTOM : Half.TOP);
			}
			level.setBlock(blockPos, newState, 11);
			level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		RecipeHolder<StairmakerRecipe> recipe = level.getRecipeManager().getRecipeFor(Registration.STAIRMAKER_RECIPE.get(), new SingleRecipeInput(target.getBlock().asItem().getDefaultInstance()), level).orElse(null);
		if (recipe != null) {
			level.playSound(context.getPlayer(), blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4F + 0.8F);
			Half half;
			if (direction == Direction.UP || (context.getClickLocation().y - (double) blockPos.getY() > 0.5)) {
				half = Half.BOTTOM;
			} else {
				half = Half.TOP;
			}
			BlockState newState = recipe.value().getResultBlockState(level.registryAccess())
					.setValue(FACING, context.getHorizontalDirection())
					.setValue(HALF, half);
			level.setBlock(blockPos, newState, 11);
			level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
			if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
				context.getItemInHand().hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(context.getHand()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.FAIL;
	}
}
