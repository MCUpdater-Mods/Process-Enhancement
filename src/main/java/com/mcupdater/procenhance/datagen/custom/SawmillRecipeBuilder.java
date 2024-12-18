package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SawmillRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final Item output;
    private final int count;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final String recipeName;

    public SawmillRecipeBuilder(Ingredient input, ItemLike output, int count, int processTime, float experience, String recipeName) {
        this.input = input;
        this.output = output.asItem();
        this.count = count;
        this.processTime = processTime;
        this.experience = experience;
        this.recipeName = recipeName != null ? recipeName : ResourceLocation.parse(this.output.toString()).getPath();
    }

    @Override
    public SawmillRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);
        return this;
    }

    @Override
    public SawmillRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public SawmillRecipeBuilder addCondition(@Nullable ICondition condition) {
        if (condition != null) {
            this.conditions.add(condition);
        }
        return this;
    }

    @Override
    public Item getResult() {
        return this.output;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        ICondition[] finalConditions = new ICondition[this.conditions.size()];
        finalConditions = this.conditions.toArray(finalConditions);
        RecipeOutput conditionalRecipeOutput = !this.conditions.isEmpty() ? recipeOutput.withConditions(finalConditions) : recipeOutput;
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        conditionalRecipeOutput.accept(recipeId, new SawmillRecipe(new ItemStack(this.output, this.count), this.processTime, this.experience, NonNullList.of(this.input,this.input)), advancementBuilder.build(recipeId.withPrefix("recipes/sawmill/")));
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "sawmill/" + recipeName));
    }
}
