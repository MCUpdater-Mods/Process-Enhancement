package com.mcupdater.procenhance.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GrinderRecipeBuilder implements RecipeBuilder {
    private final String recipeName;
    private final Ingredient input;
    private final List<Tuple<ItemStack,Integer>> outputs;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public GrinderRecipeBuilder(String recipeName, Ingredient input, int processTime, float experience) {
        this.recipeName = recipeName;
        this.input = input;
        this.outputs = new ArrayList<>();
        this.processTime = processTime;
        this.experience = experience;
    }

    @Override
    public GrinderRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(criterionName, criterionTriggerInstance);
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
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new GrinderRecipeBuilder.Result(recipeId, recipeName, this.input, this.outputs, this.processTime, this.experience, this.advancement, new ResourceLocation(recipeId.getNamespace(),"recipes/grinder/" + this.recipeName)));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation recipeId;
        private final String recipeName;
        private final Ingredient input;
        private final List<Tuple<ItemStack,Integer>> outputs;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final int processTime;
        private final float experience;

        public Result(ResourceLocation recipeId, String recipeName, Ingredient input, List<Tuple<ItemStack,Integer>> outputs, int processTime, float experience, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.recipeId = recipeId;
            this.recipeName = recipeName;
            this.input = input;
            this.outputs = outputs;
            this.processTime = processTime;
            this.experience = experience;
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
            ingredientsArray.add(input.toJson());
            jsonObject.add("ingredients",ingredientsArray);
            JsonArray outputsArray = new JsonArray();
            for (Tuple<ItemStack,Integer> tuple : this.outputs) {
                JsonObject entry = new JsonObject();
                JsonObject itemStackJson = new JsonObject();
                itemStackJson.addProperty("item", ForgeRegistries.ITEMS.getKey(tuple.getA().getItem()).toString());
                if (tuple.getA().getCount() > 1) {
                    itemStackJson.addProperty("count", tuple.getA().getCount());
                }
                entry.add("stack",itemStackJson);
                entry.addProperty("weight", tuple.getB());
                outputsArray.add(entry);
            }
            jsonObject.add("outputs", outputsArray);
            jsonObject.addProperty("processTime", this.processTime);
            jsonObject.addProperty("experience", this.experience);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(ProcessEnhancement.MODID, "grinder/" + this.recipeName);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return GrinderRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
