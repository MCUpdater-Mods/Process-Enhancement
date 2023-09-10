package com.mcupdater.procenhance.items.autopackager;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PatternHollowItem extends AbstractPatternItem {
    private static final Map<Ingredient, CraftingRecipe> RECIPES = new HashMap<>();
    public PatternHollowItem(Properties pProperties) {
        super(pProperties);
        this.ingredientCount = 9;
    }

    @Override
    public CraftingRecipe matchingRecipe(ItemStack stack, Level level) {
        if (stack.getCount() >= ingredientCount) {
            Ingredient ingredient = Ingredient.of(stack.getItem());
            if (!RECIPES.containsKey(ingredient)) {
                CraftingContainer testContainer = new CraftingContainer(new AbstractContainerMenu(MenuType.CRAFTING, -1){

                    @Override
                    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
                        return ItemStack.EMPTY;
                    }

                    @Override
                    public boolean stillValid(Player pPlayer) {
                        return false;
                    }
                },3,3);
                ItemStack testStack = stack.copy();
                testStack.setCount(1);
                for (int slot = 0; slot < 9; slot++) {
                    testContainer.setItem(slot, (slot == 4 ? ItemStack.EMPTY : testStack));
                }
                Optional<CraftingRecipe> recipe = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, testContainer, level);
                if (recipe.isPresent()) {
                    RECIPES.put(ingredient, recipe.get());
                } else {
                    RECIPES.put(ingredient, null);
                }
            }
            return RECIPES.get(ingredient);
        }
        return null;
    }

    @Override
    public void clearCache() {
        RECIPES.clear();
    }

    @Override
    public ItemStack cycleItem() {
        return new ItemStack(Registration.SLATE_SLAB.get(),1);
    }
}
