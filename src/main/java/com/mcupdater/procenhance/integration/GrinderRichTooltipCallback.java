package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GrinderRichTooltipCallback implements IRecipeSlotRichTooltipCallback {
    private final GrinderRecipe recipe;
    private final RecipeResult result;
    private final Integer weight;

    public GrinderRichTooltipCallback(GrinderRecipe recipe, Tuple<RecipeResult, Integer> prize) {
        this.recipe = recipe;
        this.result = prize.getA();
        this.weight = prize.getB();
    }

    @Override
    public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
        int totalPool = 0;
        for (Tuple<RecipeResult, Integer> tuple : recipe.getOutputs()) {
            totalPool += tuple.getB();
        }
        double chance = ((double) this.weight / (double) totalPool) * 100;
        tooltip.add(Component.literal(String.format("Chance: %.2f%%", chance)));
    }
}
