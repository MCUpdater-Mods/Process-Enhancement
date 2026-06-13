package com.mcupdater.procenhance.integration.jei;

import com.mcupdater.procenhance.recipe.BatteryUpgradeRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class BatteryUpgradeCraftingExtension implements ICraftingCategoryExtension<BatteryUpgradeRecipe> {

	@Override
	public void setRecipe(RecipeHolder<BatteryUpgradeRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
		BatteryUpgradeRecipe recipe = recipeHolder.value();
		ItemStack resultItem = JEIProcEnhancePlugin.lookupOutput(recipe);

		int width = getWidth(recipeHolder);
		int height = getHeight(recipeHolder);
		craftingGridHelper.createAndSetOutputs(builder, List.of(resultItem));
		craftingGridHelper.createAndSetIngredients(builder, recipe.getIngredients(), width, height);
	}

	@Override
	public int getWidth(RecipeHolder<BatteryUpgradeRecipe> recipeHolder) {
		return 3;
	}

	@Override
	public int getHeight(RecipeHolder<BatteryUpgradeRecipe> recipeHolder) {
		return 3;
	}
}
