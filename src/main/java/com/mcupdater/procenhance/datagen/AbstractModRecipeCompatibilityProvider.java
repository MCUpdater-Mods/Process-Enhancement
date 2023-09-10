package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.datagen.custom.CompatSawmillRecipeBuilder;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

public abstract class AbstractModRecipeCompatibilityProvider extends ModRecipeProvider {

    public AbstractModRecipeCompatibilityProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static void generateSawmillRecipes(Consumer<FinishedRecipe> consumer, String modId, ItemLike log, ItemLike planks, ItemLike stairs, ItemLike slab, ItemLike pressurePlate, ItemLike sign, ItemLike door, ItemLike trapDoor, ItemLike strippedLog, ItemLike strippedWood, ItemLike fence, ItemLike fenceGate, ItemLike boat, ItemLike button, ItemLike ladder) {
        ICondition condition = new ModLoadedCondition(modId);
        sawmill(consumer, Ingredient.of(log),planks,6,32,0.05f, condition, modId);
        if (stairs != null) sawmill(consumer, Ingredient.of(log),stairs,6,32,0.05f, condition, modId);
        if (slab != null) sawmill(consumer, Ingredient.of(log),slab,12,32,0.05f, condition, modId);
        if (pressurePlate != null) sawmill(consumer, Ingredient.of(log),pressurePlate,3,32,0.05f, condition, modId);
        if (sign != null) sawmill(consumer, Ingredient.of(log),sign,3,32,0.05f, condition, modId);
        if (door != null) sawmill(consumer, Ingredient.of(log),door,3,32,0.05f, condition, modId);
        if (trapDoor != null) sawmill(consumer, Ingredient.of(log),trapDoor,2,32,0.05f, condition, modId);
        if (strippedLog != null) sawmill(consumer, Ingredient.of(log),strippedLog,1,16,0.01f, condition, modId);
        if (strippedWood != null) sawmill(consumer, Ingredient.of(log),strippedWood,1,16,0.01f, condition, modId);
        if (fence != null) sawmill(consumer, Ingredient.of(log),fence,4,32,0.05f, condition, modId);
        if (fenceGate != null) sawmill(consumer, Ingredient.of(log),fenceGate,2,32,0.05f, condition, modId);
        if (boat != null) sawmill(consumer, Ingredient.of(log),boat,1,32,0.05f, condition, modId);
        if (ladder != null) sawmill(consumer, Ingredient.of(log),ladder, 24, 32, 0.05f, condition, modId);
        if (stairs != null) sawmill(consumer, Ingredient.of(planks),stairs,1,16,0.01f, condition, modId);
        if (slab != null) sawmill(consumer, Ingredient.of(planks),slab,2,16,0.01f, condition, modId);
        if (button != null) sawmill(consumer, Ingredient.of(planks),button,4,16,0.05f, condition, modId);
        if (ladder != null) sawmill(consumer, Ingredient.of(planks),ladder, 4, 16, 0.05f, condition, modId);
        if (boat != null) sawmill(consumer, Ingredient.of(boat),planks, 5, 32, 0f, condition, modId);
    }

    public static void sawmill(Consumer<FinishedRecipe> consumer, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition, String modId) {
        new CompatSawmillRecipeBuilder(modId,input,output,count,processTime,experience).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).addCondition(condition).save(consumer);
    }
}
