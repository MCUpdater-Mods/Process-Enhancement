package com.mcupdater.procenhance.items.autopackager;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;

public abstract class AbstractPatternItem extends Item {
    protected int ingredientCount;

    public AbstractPatternItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract CraftingRecipe matchingRecipe(ItemStack stack, Level level);

    public ItemStack doCraft(ItemStack stack, Level level) {
        ItemStack result;
        CraftingRecipe recipe = matchingRecipe(stack, level);
        if (recipe != null) {
            result = recipe.getResultItem().copy();
            stack.split(ingredientCount);
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public abstract void clearCache();

    public abstract ItemStack cycleItem();

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.setItemInHand(pUsedHand, cycleItem());
        return InteractionResultHolder.success(itemStack);
    }
}
