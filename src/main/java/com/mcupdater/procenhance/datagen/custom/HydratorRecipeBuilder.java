package com.mcupdater.procenhance.datagen.custom;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.HydratorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
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
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HydratorRecipeBuilder implements RecipeBuilder {

	private final Ingredient inputItem;
	private final FluidStack inputFluid;
	private final Item output;
	private final int count;
	private final int processTime;
	protected final List<ICondition> conditions = new ArrayList<>();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final String recipeName;
	private String additonalHierarchy = "";

	public HydratorRecipeBuilder(Ingredient inputItem, FluidStack inputFluid, ItemLike output, int count, int processTime, String recipeName) {
		this.inputItem = inputItem;
		this.inputFluid = inputFluid;
		this.output = output.asItem();
		this.count = count;
		this.processTime = processTime;
		this.recipeName = recipeName != null ? recipeName : ResourceLocation.parse(this.output.toString()).getPath();
	}

	public HydratorRecipeBuilder(String modId, Ingredient inputItem, FluidStack inputFluid, ItemLike output, int count, int processTime, String recipeName) {
		this(inputItem, inputFluid, output, count, processTime, recipeName);
		this.additonalHierarchy = "compat/" + modId + "/";
	}

	@Override
	public HydratorRecipeBuilder unlockedBy(String pCriterionName, Criterion<?> criterion) {
		this.advancement.addCriterion(pCriterionName, criterion);
		return this;
	}

	@Override
	public HydratorRecipeBuilder group(@Nullable String pGroupName) {
		return this;
	}

	public HydratorRecipeBuilder addCondition(@Nullable ICondition condition) {
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
		this.advancement
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
				.rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(AdvancementRequirements.Strategy.OR);

		conditionalRecipeOutput.accept(pRecipeId, new HydratorRecipe(new ItemStack(this.output, this.count), this.processTime, NonNullList.of(this.inputItem,this.inputItem), this.inputFluid), this.advancement.build(pRecipeId.withPrefix("recipes/")));
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "hydrator/" + this.additonalHierarchy + this.recipeName));
	}
}
