package com.mcupdater.procenhance.datagen.custom;

import com.google.common.collect.Maps;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.BatteryUpgradeRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

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

    public BatteryUpgradeRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.advancement.addCriterion(criterionName, criterion);
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

    private ShapedRecipePattern getPattern() {
        return ShapedRecipePattern.of(this.key, this.rows);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        this.advancement.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        recipeOutput.accept(recipeId, new BatteryUpgradeRecipe(this.group == null ? "" : this.group, CraftingBookCategory.MISC, this.getPattern(), new ItemStack(this.result), true), this.advancement.build(recipeId.withPrefix("recipes/")));
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "battery_upgrade/" + ResourceLocation.parse(this.result.toString()).getPath()));
    }
}