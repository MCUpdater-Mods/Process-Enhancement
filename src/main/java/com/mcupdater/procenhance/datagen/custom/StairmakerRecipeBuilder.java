package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.StairmakerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StairmakerRecipeBuilder implements RecipeBuilder {
	private final Ingredient input;
	private final BlockItem result;
	protected final List<ICondition> conditions = new ArrayList<>();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final String recipeName;
	private String additionalHierarchy = "";

	public StairmakerRecipeBuilder(Ingredient input, BlockItem result, String recipeName) {
		this.input = input;
		this.result = result;
		this.recipeName = recipeName != null ? recipeName : ResourceLocation.parse(this.result.toString()).getPath();
	}

	public StairmakerRecipeBuilder(String modId, Ingredient input, BlockItem result, String recipeName) {
		this(input, result, recipeName);
		this.additionalHierarchy = "compat/" + modId + "/";
	}

	@Override
	public StairmakerRecipeBuilder unlockedBy(String pCriterionName, Criterion<?> criterion) {
		this.advancement.addCriterion(pCriterionName, criterion);
		return this;
	}

	@Override
	public StairmakerRecipeBuilder group(@Nullable String pGroupName) {
		return this;
	}

	public StairmakerRecipeBuilder addCondition(@Nullable ICondition condition) {
		if (condition != null) {
			this.conditions.add(condition);
		}
		return this;
	}

	@Override
	public BlockItem getResult() {
		return this.result;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation pRecipeId) {
		ICondition[] finalConditions = new ICondition[this.conditions.size()];
		finalConditions = this.conditions.toArray(finalConditions);
		RecipeOutput conditionalRecipeOutput = !this.conditions.isEmpty() ? recipeOutput.withConditions(finalConditions) : recipeOutput;
		this.advancement
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
				.rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(AdvancementRequirements.Strategy.OR);

		conditionalRecipeOutput.accept(pRecipeId, new StairmakerRecipe(this.input, this.result.getDefaultInstance()), this.advancement.build(pRecipeId.withPrefix("recipes/")));
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "stairmaker/" + this.additionalHierarchy + this.recipeName));
	}
}
