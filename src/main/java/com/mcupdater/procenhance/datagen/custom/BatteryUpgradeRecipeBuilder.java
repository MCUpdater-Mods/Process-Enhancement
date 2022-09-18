package com.mcupdater.procenhance.datagen.custom;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.utils.ObservedMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.recipe.BatteryUpgradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BatteryUpgradeRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final RecipeSerializer<?> serializer;
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final List<String> rows = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;

    public BatteryUpgradeRecipeBuilder(ItemLike result, RecipeSerializer<?> serializer) {
        this.result = result.asItem();
        this.serializer = serializer;
    }

    public static BatteryUpgradeRecipeBuilder shaped(ItemLike result, RecipeSerializer<?> serializer) {
        return new BatteryUpgradeRecipeBuilder(result, serializer);
    }

    public BatteryUpgradeRecipeBuilder define(Character pSymbol, Ingredient pIngredient) {
        if (this.key.containsKey(pSymbol)) {
            throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
        } else if (pSymbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(pSymbol, pIngredient);
            return this;
        }
    }

    public BatteryUpgradeRecipeBuilder pattern(String pPattern) {
        if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pPattern);
            return this;
        }
    }

    public BatteryUpgradeRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new BatteryUpgradeRecipeBuilder.Result(pRecipeId, this.result, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + pRecipeId.getPath())));
    }

    private void ensureValid(ResourceLocation pRecipeId) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + pRecipeId + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String row : this.rows) {
                for(int colIndex = 0; colIndex < row.length(); ++colIndex) {
                    char col = row.charAt(colIndex);
                    if (!this.key.containsKey(col) && col != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + pRecipeId + " uses undefined symbol '" + col + "'");
                    }

                    set.remove(col);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + pRecipeId);
            } else if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pRecipeId);
            }
        }
    }

    private class Result implements FinishedRecipe {
        private final ResourceLocation recipeId;
        private final Item result;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pRecipeId, Item pResult, String pGroup, List<String> pPattern, Map<Character, Ingredient> pKey, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.recipeId = pRecipeId;
            this.result = pResult;
            this.group = pGroup;
            this.pattern = pPattern;
            this.key = pKey;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray patternArray = new JsonArray();
            for(String row : this.pattern) {
                patternArray.add(row);
            }
            pJson.add("pattern", patternArray);

            JsonObject ingredientMap = new JsonObject();
            for(Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                ingredientMap.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            pJson.add("key", ingredientMap);

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", this.result.getRegistryName().toString());
            pJson.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId() {
            return this.recipeId;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BatteryUpgradeRecipe.SERIALIZER;
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