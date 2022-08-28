package com.mcupdater.procenhance.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
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
import org.apache.logging.log4j.core.jackson.JsonConstants;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SawmillRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final Item output;
    private final int count;
    private final int processTime;
    private final float experience;
    protected final List<ICondition> conditions = new ArrayList<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public SawmillRecipeBuilder(Ingredient input, ItemLike output, int count, int processTime, float experience) {
        this.input = input;
        this.output = output.asItem();
        this.count = count;
        this.processTime = processTime;
        this.experience = experience;
    }

    @Override
    public SawmillRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(criterionName, criterionTriggerInstance);
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
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);

        finishedRecipeConsumer.accept(new SawmillRecipeBuilder.Result(recipeId, this.output, this.count, this.input, this.processTime, this.experience, this.advancement, new ResourceLocation(recipeId.getNamespace(),"recipes/sawmill/" + this.output.getItemCategory().getRecipeFolderName() + "/" + recipeId.getPath())));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation recipeId;
        private final Item output;
        private final Ingredient input;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final int processTime;
        private final float experience;

        public Result(ResourceLocation recipeId, Item output, int count, Ingredient input, int processTime, float experience, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.recipeId = recipeId;
            this.output = output;
            this.count = count;
            this.input = input;
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
            jsonObject.add("ingredients", ingredientsArray);
            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", this.output.getRegistryName().toString());
            if (this.count > 1) {
                resultObject.addProperty("count", this.count);
            }
            jsonObject.add("result",resultObject);
            jsonObject.addProperty("processTime",this.processTime);
            jsonObject.addProperty("experience",this.experience);
        }

        @Override
        public ResourceLocation getId() {
            String source = this.input.getItems()[0].getItem().getRegistryName().getPath();
            if (source.equals("barrier")) {
                ItemStack firstIngredient = Arrays.stream(this.input.getItems()).findFirst().get();
                source = "tag-" + firstIngredient.getTag().getCompound("display").getString("Name").split(":")[3].replace("\"}","");
            }
            return new ResourceLocation(ProcessEnhancement.MODID, "sawmill/" + this.output.getRegistryName().getPath() + "_from_" + source);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return SawmillRecipe.Serializer.INSTANCE;
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
