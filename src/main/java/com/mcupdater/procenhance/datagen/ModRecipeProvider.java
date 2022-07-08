package com.mcupdater.procenhance.datagen;

import com.google.common.collect.ImmutableList;
import com.mcupdater.procenhance.datagen.custom.GrinderRecipeBuilder;
import com.mcupdater.procenhance.datagen.custom.SawmillRecipeBuilder;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.mcupdater.procenhance.setup.Registration.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer) {
        machineRecipe(finishedRecipeConsumer,Registration.BASICGENERATOR_BLOCK.get(), Ingredient.of(Blocks.COBBLESTONE), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.FURNACE));
        machineRecipe(finishedRecipeConsumer,Registration.FURNACE_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        machineRecipe(finishedRecipeConsumer,Registration.SAWMILL_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.IRON_AXE));
        machineRecipe(finishedRecipeConsumer,Registration.GRINDER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));

        // Sawmill recipes
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.PLANKS), Items.STICK, 3, 100, 0.01f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_PLANKS,6,200,0.05f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.ACACIA_BOAT),Blocks.ACACIA_PLANKS,5,200,0f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.BIRCH_BOAT),Blocks.BIRCH_PLANKS,5,200,0f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.DARK_OAK_BOAT),Blocks.DARK_OAK_PLANKS,5,200,0f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.JUNGLE_BOAT),Blocks.JUNGLE_PLANKS,5,200,0f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.OAK_BOAT),Blocks.OAK_PLANKS,5,200,0f);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.SPRUCE_BOAT),Blocks.SPRUCE_PLANKS,5,200,0f);

        // Grinder recipes
        grinder("cobblestone",Ingredient.of(Blocks.COBBLESTONE),200,0.01f).addOutput(new ItemStack(Blocks.GRAVEL,1), 1).save(finishedRecipeConsumer);
        grinder("gravel",Ingredient.of(Blocks.GRAVEL), 200, 0.01f)
                .addOutput(new ItemStack(Blocks.DIRT,1), 40)
                .addOutput(new ItemStack(Blocks.SAND,1), 40)
                .addOutput(new ItemStack(Items.COAL, 1), 10)
                .addOutput(new ItemStack(IRON_DUST.get(), 1), 10)
                .addOutput(new ItemStack(COPPER_DUST.get(),1),10)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 8)
                .addOutput(new ItemStack(Items.REDSTONE, 1), 8)
                .addOutput(new ItemStack(Items.GLOWSTONE_DUST, 1), 6)
                .addOutput(new ItemStack(Items.DIAMOND, 1), 2)
                .save(finishedRecipeConsumer);
        grinder("raw_iron", Ingredient.of(Items.RAW_IRON),200,0.05f)
                .addOutput(new ItemStack(IRON_DUST.get(), 1), 2)
                .addOutput(new ItemStack(IRON_DUST.get(), 2), 1)
                .save(finishedRecipeConsumer);
        grinder("raw_gold", Ingredient.of(Items.RAW_GOLD),200,0.05f)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 2)
                .addOutput(new ItemStack(GOLD_DUST.get(), 2), 1)
                .save(finishedRecipeConsumer);
        grinder("raw_copper", Ingredient.of(Items.RAW_COPPER),200,0.05f)
                .addOutput(new ItemStack(COPPER_DUST.get(), 1), 2)
                .addOutput(new ItemStack(COPPER_DUST.get(), 2), 1)
                .save(finishedRecipeConsumer);
        grinder("iron_ore", Ingredient.of(ItemTags.IRON_ORES),200,0.05f)
                .addOutput(new ItemStack(IRON_DUST.get(), 3), 55)
                .addOutput(new ItemStack(IRON_DUST.get(), 4), 25)
                .addOutput(new ItemStack(IRON_DUST.get(), 5), 18)
                .addOutput(new ItemStack(IRON_DUST.get(), 6), 2)
                .save(finishedRecipeConsumer);
        grinder("gold_ore", Ingredient.of(ItemTags.GOLD_ORES),200,0.05f)
                .addOutput(new ItemStack(GOLD_DUST.get(), 3), 55)
                .addOutput(new ItemStack(GOLD_DUST.get(), 4), 25)
                .addOutput(new ItemStack(GOLD_DUST.get(), 5), 18)
                .addOutput(new ItemStack(GOLD_DUST.get(), 6), 2)
                .save(finishedRecipeConsumer);
        grinder("copper_ore", Ingredient.of(ItemTags.COPPER_ORES),200,0.05f)
                .addOutput(new ItemStack(COPPER_DUST.get(), 3), 55)
                .addOutput(new ItemStack(COPPER_DUST.get(), 4), 25)
                .addOutput(new ItemStack(COPPER_DUST.get(), 5), 18)
                .addOutput(new ItemStack(COPPER_DUST.get(), 6), 2)
                .save(finishedRecipeConsumer);

        cookOre(finishedRecipeConsumer, IRON_DUST.get(), Items.IRON_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, COPPER_DUST.get(), Items.COPPER_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, GOLD_DUST.get(), Items.GOLD_INGOT, 1.0f);
    }

    private void cookOre(Consumer<FinishedRecipe> finishedRecipeConsumer, Item input, Item output, float experience) {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input),output,experience,200,RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_" + input.getRegistryName().getPath(), has(input)).save(finishedRecipeConsumer, output.getRegistryName().getPath() + "_from_smelting_" + input.getRegistryName().getPath());
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input),output,experience,100,RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_" + input.getRegistryName().getPath(), has(input)).save(finishedRecipeConsumer, output.getRegistryName().getPath() + "_from_blasting_" + input.getRegistryName().getPath());
    }

    protected static void machineRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        ShapedRecipeBuilder.shaped(result).define('C', corner).define('F',face).define('#',core).pattern("CFC").pattern("F#F").pattern("CFC").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
    }

    protected static void sawmill(Consumer<FinishedRecipe> consumer, Ingredient input, ItemLike output, int count, int processTime, float experience) {
        new SawmillRecipeBuilder(input,output,count,processTime,experience).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).save(consumer);
    }

    protected static GrinderRecipeBuilder grinder(String recipeName, Ingredient input, int processTime, float experience) {
        return new GrinderRecipeBuilder(recipeName, input, processTime, experience).unlockedBy("has_grinder", has(Registration.GRINDER_BLOCK.get()));
    }
}
