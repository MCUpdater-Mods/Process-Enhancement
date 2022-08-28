package com.mcupdater.procenhance.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeCompatibilityProvider extends ModRecipeProvider {
    public ModRecipeCompatibilityProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer) {

    }

    public static void generateSawmillRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike log, ItemLike planks, ItemLike stairs, ItemLike slab, ItemLike pressurePlate, ItemLike sign, ItemLike door, ItemLike trapDoor, ItemLike strippedLog, ItemLike strippedWood, ItemLike fence, ItemLike fenceGate, ItemLike boat, ItemLike button, ItemLike ladder) {
        sawmill(consumer, Ingredient.of(log),planks,6,32,0.05f, condition);
        if (stairs != null) sawmill(consumer, Ingredient.of(log),stairs,6,32,0.05f, condition);
        if (slab != null) sawmill(consumer, Ingredient.of(log),slab,12,32,0.05f, condition);
        if (pressurePlate != null) sawmill(consumer, Ingredient.of(log),pressurePlate,3,32,0.05f, condition);
        if (sign != null) sawmill(consumer, Ingredient.of(log),sign,3,32,0.05f, condition);
        if (door != null) sawmill(consumer, Ingredient.of(log),door,3,32,0.05f, condition);
        if (trapDoor != null) sawmill(consumer, Ingredient.of(log),trapDoor,2,32,0.05f, condition);
        if (strippedLog != null) sawmill(consumer, Ingredient.of(log),strippedLog,1,16,0.01f, condition);
        if (strippedWood != null) sawmill(consumer, Ingredient.of(log),strippedWood,1,16,0.01f, condition);
        if (fence != null) sawmill(consumer, Ingredient.of(log),fence,4,32,0.05f, condition);
        if (fenceGate != null) sawmill(consumer, Ingredient.of(log),fenceGate,2,32,0.05f, condition);
        if (boat != null) sawmill(consumer, Ingredient.of(log),boat,1,32,0.05f, condition);
        if (ladder != null) sawmill(consumer, Ingredient.of(log),ladder, 24, 32, 0.05f, condition);
        if (stairs != null) sawmill(consumer, Ingredient.of(planks),stairs,1,16,0.01f, condition);
        if (slab != null) sawmill(consumer, Ingredient.of(planks),slab,2,16,0.01f, condition);
        if (button != null) sawmill(consumer, Ingredient.of(planks),button,4,16,0.05f, condition);
        if (ladder != null) sawmill(consumer, Ingredient.of(planks),ladder, 4, 16, 0.05f, condition);
        if (boat != null) sawmill(consumer, Ingredient.of(boat),planks, 5, 32, 0f, condition);
    }

}
