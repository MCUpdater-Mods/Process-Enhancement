package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.result.ItemRecipeResult;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import com.mcupdater.procenhance.recipe.result.TagRecipeResult;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GrinderRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final NonNullList<Tuple<RecipeResult,Integer>> outputs;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final String recipeName;
    private String additionalHierarchy = "";

    public GrinderRecipeBuilder(Ingredient input, int processTime, float experience, @NotNull String recipeName) {
        this.input = input;
        this.outputs = NonNullList.create();
        this.processTime = processTime;
        this.experience = experience;
        this.recipeName = recipeName;
    }

    public GrinderRecipeBuilder(String modId, Ingredient input, int processTime, float experience, @NotNull String recipeName) {
        this(input, processTime, experience, recipeName);
        this.additionalHierarchy = "compat/" + modId + "/";
    }

    @Override
    public GrinderRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.advancement.addCriterion(criterionName, criterion);
        return this;
    }

    @Override
    public GrinderRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.BARRIER;
    }

    public GrinderRecipeBuilder addCondition(@Nullable ICondition condition) {
        if (condition != null) {
            this.conditions.add(condition);
        }
        return this;
    }

    public GrinderRecipeBuilder addOutput(ItemStack itemStack, Integer weight) {
        this.outputs.add(this.outputs.size(), new Tuple<>(new ItemRecipeResult(itemStack), weight));
        return this;
    }

    public GrinderRecipeBuilder addOutput(TagKey<Item> tagKey, int count, Integer weight) {
        this.outputs.add(this.outputs.size(), new Tuple<>(new TagRecipeResult(tagKey, count), weight));
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grinder/" + this.additionalHierarchy + this.recipeName));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        ICondition[] finalConditions = new ICondition[this.conditions.size()];
        finalConditions = this.conditions.toArray(finalConditions);
        RecipeOutput conditionalRecipeOutput = !this.conditions.isEmpty() ? recipeOutput.withConditions(finalConditions) : recipeOutput;
        this.advancement
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        conditionalRecipeOutput.accept(recipeId, new GrinderRecipe(this.outputs, this.processTime, this.experience, NonNullList.of(this.input,this.input)), this.advancement.build(recipeId.withPrefix("recipes/")));
    }
}
