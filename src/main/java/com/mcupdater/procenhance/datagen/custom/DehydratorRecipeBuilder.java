package com.mcupdater.procenhance.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.DehydratorRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DehydratorRecipeBuilder implements RecipeBuilder {

	private final Ingredient inputItem;
	private final FluidStack outputFluid;
	private final Item output;
	private final int count;
	private final int processTime;
	protected final List<ICondition> conditions = new ArrayList<>();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final String recipeName;

	public DehydratorRecipeBuilder(Ingredient inputItem, FluidStack outputFluid, ItemLike output, int count, int processTime, String recipeName) {
		this.inputItem = inputItem;
		this.outputFluid = outputFluid;
		this.output = output.asItem();
		this.count = count;
		this.processTime = processTime;
		this.recipeName = recipeName != null ? recipeName : ResourceLocation.parse(this.output.toString()).getPath();
	}

	@Override
	public DehydratorRecipeBuilder unlockedBy(String pCriterionName, Criterion<?> criterion) {
		this.advancement.addCriterion(pCriterionName, criterion);
		return this;
	}

	@Override
	public DehydratorRecipeBuilder group(@Nullable String pGroupName) {
		return this;
	}

	public DehydratorRecipeBuilder addCondition(@Nullable ICondition condition) {
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
	public void save(RecipeOutput recipeOutput, ResourceLocation pRecipeId) {
		ICondition[] finalConditions = new ICondition[this.conditions.size()];
		finalConditions = this.conditions.toArray(finalConditions);
		RecipeOutput conditionalRecipeOutput = !this.conditions.isEmpty() ? recipeOutput.withConditions(finalConditions) : recipeOutput;
		this.advancement.parent(ResourceLocation.withDefaultNamespace("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
				.rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(AdvancementRequirements.Strategy.OR);

		conditionalRecipeOutput.accept(pRecipeId, new DehydratorRecipe(new ItemStack(this.output, this.count), this.processTime, NonNullList.of(this.inputItem,this.inputItem), this.outputFluid), this.advancement.build(ResourceLocation.fromNamespaceAndPath(pRecipeId.getNamespace(), "recipes/dehydrator/" + pRecipeId.getPath())));
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "dehydrator/" + this.recipeName));
	}
}
