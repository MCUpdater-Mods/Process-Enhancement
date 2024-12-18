package com.mcupdater.procenhance.datagen.custom;

import com.google.common.collect.Maps;
import com.mcupdater.procenhance.recipe.TankUpgradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
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

public class TankUpgradeRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final RecipeSerializer<?> serializer;
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final List<String> rows = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;

    public TankUpgradeRecipeBuilder(ItemLike result, RecipeSerializer<?> serializer) {
        this.result = result.asItem();
        this.serializer = serializer;
    }

    public static TankUpgradeRecipeBuilder shaped(ItemLike result, RecipeSerializer<?> serializer) {
        return new TankUpgradeRecipeBuilder(result, serializer);
    }

    public TankUpgradeRecipeBuilder define(Character pSymbol, Ingredient pIngredient) {
        if (this.key.containsKey(pSymbol)) {
            throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
        } else if (pSymbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(pSymbol, pIngredient);
            return this;
        }
    }

    public TankUpgradeRecipeBuilder pattern(String pPattern) {
        if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pPattern);
            return this;
        }
    }

    public TankUpgradeRecipeBuilder unlockedBy(String pCriterionName, Criterion<?> criterion) {
        this.advancement.addCriterion(pCriterionName, criterion);
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
    public void save(RecipeOutput recipeOutput, ResourceLocation pRecipeId) {
        this.advancement.parent(ResourceLocation.withDefaultNamespace("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(AdvancementRequirements.Strategy.OR);
        recipeOutput.accept(pRecipeId, new TankUpgradeRecipe(this.group == null ? "" : this.group, CraftingBookCategory.MISC, getPattern(), new ItemStack(this.result), true), this.advancement.build(ResourceLocation.fromNamespaceAndPath(pRecipeId.getNamespace(), "recipes/" + pRecipeId.getPath())));
    }
}