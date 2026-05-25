package com.mcupdater.procenhance.items.tools.chisel;

import com.mcupdater.procenhance.recipe.ChiselRecipe;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ChiselItem extends Item {
	public ChiselItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState target = level.getBlockState(blockPos);
		RecipeHolder<ChiselRecipe> recipe = level.getRecipeManager().getRecipeFor(Registration.CHISEL_RECIPE.get(), new SingleRecipeInput(target.getBlock().asItem().getDefaultInstance()), level).orElse(null);
		if (recipe != null) {
			level.playSound(context.getPlayer(), blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4F + 0.8F);
			level.setBlock(blockPos, recipe.value().getResultBlockState(level.registryAccess()), 11);
			level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
			if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
				context.getItemInHand().hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(context.getHand()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.FAIL;
	}
}
