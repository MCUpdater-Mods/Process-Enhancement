package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.recipe.GrinderRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GrinderTooltipCallback implements IRecipeSlotTooltipCallback {
    private final GrinderRecipe recipe;
    private final ItemStack result;
    private final Integer weight;

    public GrinderTooltipCallback(GrinderRecipe recipe, Tuple<ItemStack, Integer> prize) {
        this.recipe = recipe;
        this.result = prize.getA();
        this.weight = prize.getB();
    }

    @Override
    public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
        int totalPool = 0;
        for (Tuple<ItemStack, Integer> tuple : recipe.getOutputs()) {
            totalPool += tuple.getB();
        }
        double chance = ((double) this.weight / (double) totalPool) * 100;
        tooltip.add(Component.literal(String.format("Chance: %.2f%%", chance)));
    }
}
