package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SawmillRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final RecipeResult output;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final String recipeName;
    private String additionalHierarchy = "";

    public SawmillRecipeBuilder(Ingredient input, RecipeResult output, int processTime, float experience, String recipeName) {
        this.input = input;
        this.output = output;
        this.processTime = processTime;
        this.experience = experience;
        this.recipeName = recipeName != null ? recipeName : ResourceLocation.parse(this.output.getItemStack().getItem().toString()).getPath();
    }

    public SawmillRecipeBuilder(String modId, Ingredient input, RecipeResult output, int processTime, float experience, String recipeName) {
        this(input, output, processTime, experience, recipeName);
        this.additionalHierarchy = "compat/" + modId + "/";
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
        return this.output.getItemStack().getItem();
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
        this.criteria.entrySet().forEach(entry -> advancementBuilder.addCriterion(entry.getKey(), entry.getValue()));
        conditionalRecipeOutput.accept(recipeId, new SawmillRecipe(this.output, this.processTime, this.experience, NonNullList.of(this.input,this.input)), advancementBuilder.build(recipeId.withPrefix("recipes/")));
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "sawmill/" + this.additionalHierarchy + this.recipeName));
    }
}
