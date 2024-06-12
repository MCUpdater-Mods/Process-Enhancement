package com.mcupdater.procenhance.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.HydratorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HydratorRecipeBuilder implements RecipeBuilder {

	private final Ingredient inputItem;
	private final FluidStack inputFluid;
	private final Item output;
	private final int count;
	private final int processTime;
	protected final List<ICondition> conditions = new ArrayList<>();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();

	public HydratorRecipeBuilder(Ingredient inputItem, FluidStack inputFluid, ItemLike output, int count, int processTime) {
		this.inputItem = inputItem;
		this.inputFluid = inputFluid;
		this.output = output.asItem();
		this.count = count;
		this.processTime = processTime;
	}

	@Override
	public HydratorRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
		this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
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
	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
		this.advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
				.rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

		pFinishedRecipeConsumer.accept(new HydratorRecipeBuilder.Result(pRecipeId, this.output, this.count, this.inputItem, this.inputFluid, this.processTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/hydrator/" + this.output.getItemCategory().getRecipeFolderName() + "/" + pRecipeId.getPath())));
	}

	public class Result implements FinishedRecipe {
		private final ResourceLocation recipeId;
		private final Item output;
		private final Ingredient inputItem;
		private final FluidStack inputFluid;
		private final int count;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final int processTime;

		public Result(ResourceLocation recipeid, Item output, int count, Ingredient inputItem, FluidStack inputFluid, int processTime, Advancement.Builder advancement, ResourceLocation advancementId) {
			this.recipeId = recipeid;
			this.output = output;
			this.count = count;
			this.inputItem = inputItem;
			this.inputFluid = inputFluid;
			this.processTime = processTime;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public JsonObject serializeRecipe() {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("type", Registry.RECIPE_SERIALIZER.getKey(this.getType()).toString());
			if (!conditions.isEmpty()) {
				JsonArray conditionsArray = new JsonArray();
				for (ICondition condition : conditions) {
					conditionsArray.add(CraftingHelper.serialize(condition));
				}
				jsonobject.add("conditions", conditionsArray);
			}
			this.serializeRecipeData(jsonobject);
			return jsonobject;
		}

		@Override
		public void serializeRecipeData(JsonObject jsonObject) {
			JsonArray ingredientsArray = new JsonArray();
			ingredientsArray.add(inputItem.toJson());
			jsonObject.add("itemIngredients", ingredientsArray);
			jsonObject.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(inputFluid.getFluid()).toString());
			jsonObject.addProperty("fluidAmount", inputFluid.getAmount());
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
			if (this.count > 1) {
				resultObject.addProperty("count", this.count);
			}
			jsonObject.add("result",resultObject);
			jsonObject.addProperty("processTime",this.processTime);
		}

		@Override
		public ResourceLocation getId() {
			String sourceItem = ForgeRegistries.ITEMS.getKey(this.inputItem.getItems()[0].getItem()).getPath();
			String sourceFluid = ForgeRegistries.FLUIDS.getKey(this.inputFluid.getFluid()).getPath();
			if (sourceItem.equals("barrier")) {
				ItemStack firstIngredient = Arrays.stream(this.inputItem.getItems()).findFirst().get();
				sourceItem = "tag-" + firstIngredient.getTag().getCompound("display").getString("Name").split(":")[3].replace("\"}", "");
			}
			return new ResourceLocation(ProcessEnhancement.MODID, "hydrator/" + ForgeRegistries.ITEMS.getKey(this.output).getPath() + "_from_" + sourceItem + "_and_" + sourceFluid);
		}

		@Override
		public RecipeSerializer<?> getType() {
			return HydratorRecipe.Serializer.INSTANCE;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}

	}
}
