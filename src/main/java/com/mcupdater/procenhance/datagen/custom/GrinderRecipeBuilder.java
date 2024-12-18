package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.crafting.ConditionalRecipeOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GrinderRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final NonNullList<Tuple<ItemStack,Integer>> outputs;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final String recipeName;

    public GrinderRecipeBuilder(Ingredient input, int processTime, float experience, @NotNull String recipeName) {
        this.input = input;
        this.outputs = NonNullList.create();
        this.processTime = processTime;
        this.experience = experience;
        this.recipeName = recipeName;
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
        this.outputs.add(this.outputs.size(), new Tuple<>(itemStack, weight));
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grinder/" + this.recipeName));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        ICondition[] finalConditions = new ICondition[this.conditions.size()];
        finalConditions = this.conditions.toArray(finalConditions);
        RecipeOutput conditionalRecipeOutput = !this.conditions.isEmpty() ? recipeOutput.withConditions(finalConditions) : recipeOutput;
        this.advancement.parent(ResourceLocation.withDefaultNamespace("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        conditionalRecipeOutput.accept(recipeId, new GrinderRecipe(this.outputs, this.processTime, this.experience, NonNullList.of(this.input,this.input)), this.advancement.build(ResourceLocation.fromNamespaceAndPath(recipeId.getNamespace(),"recipes/grinder/" + recipeId.getPath())));
    }
}
