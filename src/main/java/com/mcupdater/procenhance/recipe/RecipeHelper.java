package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class RecipeHelper {
	public static RecipeHolder<GrinderRecipe> getGrinderRecipe(Level level, SingleRecipeInput recipeInput) {
		return level.getRecipeManager().getRecipeFor(Registration.GRINDER_RECIPE.get(), recipeInput, level).orElse(null);
	}
}
