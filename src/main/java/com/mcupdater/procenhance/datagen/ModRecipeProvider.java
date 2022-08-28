package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.datagen.custom.GrinderRecipeBuilder;
import com.mcupdater.procenhance.datagen.custom.SawmillRecipeBuilder;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

import static com.mcupdater.procenhance.setup.Registration.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        crudeMachineRecipe(finishedRecipeConsumer, CRUDEGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.COBBLESTONE), Ingredient.of(Items.STICK));
        basicMachineRecipe(finishedRecipeConsumer, BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.BRICK), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(finishedRecipeConsumer, BASICBATTERY_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(CAPACITOR.get()));
        crudeMachineRecipe(finishedRecipeConsumer, FURNACE_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(finishedRecipeConsumer, SAWMILL_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.IRON_AXE));
        basicMachineRecipe(finishedRecipeConsumer, GRINDER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));
        basicMachineRecipe(finishedRecipeConsumer, STONECUTTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE_BRICKS), Ingredient.of(Blocks.STONECUTTER));

        // Sawmill recipes
        //sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DIRT), Items.DIAMOND, 1, 32, 0.05f, new ModLoadedCondition("testmod"));
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.PLANKS), Items.STICK, 3, 16, 0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.LOGS),Items.LADDER, 24, 32, 0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.PLANKS), Items.LADDER, 4, 16, 0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Items.ACACIA_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.STRIPPED_ACACIA_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.STRIPPED_ACACIA_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.ACACIA_LOGS),Items.ACACIA_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Items.BIRCH_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.STRIPPED_BIRCH_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.STRIPPED_BIRCH_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.BIRCH_LOGS),Items.BIRCH_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Items.DARK_OAK_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.STRIPPED_DARK_OAK_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.STRIPPED_DARK_OAK_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.DARK_OAK_LOGS),Items.DARK_OAK_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Items.JUNGLE_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.STRIPPED_JUNGLE_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.STRIPPED_JUNGLE_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.JUNGLE_LOGS),Items.JUNGLE_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Items.OAK_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.STRIPPED_OAK_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.STRIPPED_OAK_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.OAK_LOGS),Items.OAK_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Items.SPRUCE_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.STRIPPED_SPRUCE_LOG,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.STRIPPED_SPRUCE_WOOD,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.SPRUCE_LOGS),Items.SPRUCE_BOAT,1,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Items.CRIMSON_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.STRIPPED_CRIMSON_STEM,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.STRIPPED_CRIMSON_HYPHAE,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_PLANKS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_STAIRS,6,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_SLAB,12,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Items.WARPED_SIGN,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_DOOR,3,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_TRAPDOOR,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.STRIPPED_WARPED_STEM,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.STRIPPED_WARPED_HYPHAE,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_FENCE,4,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_FENCE_GATE,2,32,0.05f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_STAIRS,1,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_SLAB,2,16,0.01f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_BUTTON,4,16,0.01f, null);

        sawmill(finishedRecipeConsumer, Ingredient.of(Items.ACACIA_BOAT),Blocks.ACACIA_PLANKS,5,32,0f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.BIRCH_BOAT),Blocks.BIRCH_PLANKS,5,32,0f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.DARK_OAK_BOAT),Blocks.DARK_OAK_PLANKS,5,32,0f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.JUNGLE_BOAT),Blocks.JUNGLE_PLANKS,5,32,0f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.OAK_BOAT),Blocks.OAK_PLANKS,5,32,0f, null);
        sawmill(finishedRecipeConsumer, Ingredient.of(Items.SPRUCE_BOAT),Blocks.SPRUCE_PLANKS,5,32,0f, null);

        // Grinder recipes
        grinder("cobblestone",Ingredient.of(Blocks.COBBLESTONE),200,0.01f).addOutput(new ItemStack(Blocks.GRAVEL,1), 1).save(finishedRecipeConsumer);
        grinder("gravel",Ingredient.of(Blocks.GRAVEL), 200, 0.01f)
                .addOutput(new ItemStack(Blocks.DIRT,1), 40)
                .addOutput(new ItemStack(Blocks.SAND,1), 40)
                .addOutput(new ItemStack(Items.FLINT, 1), 20)
                .addOutput(new ItemStack(Items.COAL, 1), 10)
                .addOutput(new ItemStack(IRON_DUST.get(), 1), 10)
                .addOutput(new ItemStack(COPPER_DUST.get(),1),10)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 8)
                .addOutput(new ItemStack(Items.REDSTONE, 1), 8)
                .addOutput(new ItemStack(Items.LAPIS_LAZULI, 1), 8)
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
        grinder_oreblock("iron_ore", Ingredient.of(ItemTags.IRON_ORES),1, 200,0.05f, IRON_DUST.get(), finishedRecipeConsumer);
        grinder_oreblock("gold_ore", Ingredient.of(ItemTags.GOLD_ORES),1, 200,0.05f, GOLD_DUST.get(), finishedRecipeConsumer);
        grinder_oreblock("copper_ore", Ingredient.of(ItemTags.COPPER_ORES),2, 200,0.05f, COPPER_DUST.get(), finishedRecipeConsumer);
        grinder_oreblock("coal_ore", Ingredient.of(ItemTags.COAL_ORES), 1, 200, 0.05f, Items.COAL, finishedRecipeConsumer);
        grinder_oreblock("redstone_ore", Ingredient.of(ItemTags.REDSTONE_ORES), 3, 200, 0.05f, Items.REDSTONE, finishedRecipeConsumer);
        grinder_oreblock("lapis_ore", Ingredient.of(ItemTags.LAPIS_ORES), 4, 200, 0.05f, Items.LAPIS_LAZULI, finishedRecipeConsumer);
        grinder_oreblock("diamond_ore", Ingredient.of(ItemTags.DIAMOND_ORES), 1, 200, 0.05f, Items.DIAMOND, finishedRecipeConsumer);
        grinder_oreblock("emerald_ore", Ingredient.of(ItemTags.EMERALD_ORES), 1, 200, 0.05f, Items.EMERALD, finishedRecipeConsumer);
        grinder_oreblock("nether_quartz_ore", Ingredient.of(Items.NETHER_QUARTZ_ORE), 2, 200, 0.05f, Items.QUARTZ, finishedRecipeConsumer);
        grinder_oreblock("ancient_debris", Ingredient.of(Items.ANCIENT_DEBRIS), 1, 200, 0.05f, Items.NETHERITE_SCRAP, finishedRecipeConsumer);
        grinder_single("dandelion", Ingredient.of(Items.DANDELION), new ItemStack(Items.YELLOW_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("poppy", Ingredient.of(Items.POPPY), new ItemStack(Items.RED_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("blue_orchid", Ingredient.of(Items.BLUE_ORCHID), new ItemStack(Items.LIGHT_BLUE_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("allium", Ingredient.of(Items.ALLIUM), new ItemStack(Items.MAGENTA_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("azure_bluet", Ingredient.of(Items.AZURE_BLUET), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("red_tulip", Ingredient.of(Items.RED_TULIP), new ItemStack(Items.RED_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("orange_tulip", Ingredient.of(Items.ORANGE_TULIP), new ItemStack(Items.ORANGE_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("white_tulip", Ingredient.of(Items.WHITE_TULIP), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("pink_tulip", Ingredient.of(Items.PINK_TULIP), new ItemStack(Items.PINK_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("oxeye_daisy", Ingredient.of(Items.OXEYE_DAISY), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("cornflower", Ingredient.of(Items.CORNFLOWER), new ItemStack(Items.BLUE_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("lily_of_the_valley", Ingredient.of(Items.LILY_OF_THE_VALLEY), new ItemStack(Items.WHITE_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("wither_rose", Ingredient.of(Items.WITHER_ROSE), new ItemStack(Items.BLACK_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("sunflower", Ingredient.of(Items.SUNFLOWER), new ItemStack(Items.YELLOW_DYE,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("lilac", Ingredient.of(Items.LILAC), new ItemStack(Items.MAGENTA_DYE,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("rose_bush", Ingredient.of(Items.ROSE_BUSH), new ItemStack(Items.RED_DYE,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("peony", Ingredient.of(Items.PEONY), new ItemStack(Items.PINK_DYE,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("cocoa_beans", Ingredient.of(Items.COCOA_BEANS), new ItemStack(Items.BROWN_DYE,3), 50,0.0f, finishedRecipeConsumer);
        grinder_single("ink_sac", Ingredient.of(Items.INK_SAC), new ItemStack(Items.BLACK_DYE, 3), 50, 0.0f, finishedRecipeConsumer);
        grinder_single("lapis_lazuli", Ingredient.of(Items.LAPIS_LAZULI), new ItemStack(Items.BLUE_DYE, 3), 50, 0.0f, finishedRecipeConsumer);
        grinder_single("bone", Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("blaze_rod", Ingredient.of(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER,4), 50,0.0f, finishedRecipeConsumer);
        grinder_single("flint", Ingredient.of(Items.FLINT), new ItemStack(Items.GUNPOWDER,1), 50,0.0f, finishedRecipeConsumer);
        grinder_single("wool", Ingredient.of(ItemTags.WOOL), new ItemStack(Items.STRING,4), 100, 0.01f, finishedRecipeConsumer);

        cookOre(finishedRecipeConsumer, IRON_DUST.get(), Items.IRON_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, COPPER_DUST.get(), Items.COPPER_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, GOLD_DUST.get(), Items.GOLD_INGOT, 1.0f);

        ShapedRecipeBuilder.shaped(CAPACITOR.get()).define('B',Ingredient.of(Items.BLUE_DYE)).define('C',Ingredient.of(Items.COPPER_INGOT)).define('P',Ingredient.of(Items.PAPER)).define('I',Ingredient.of(Items.IRON_NUGGET)).pattern("BBB").pattern("CPC").pattern("I I").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(COPPERWIRE_BLOCKITEM.get(),16).define('C', Ingredient.of(Items.COPPER_INGOT)).pattern("CCC").unlockedBy("automatic",has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
    }

    private void grinder_oreblock(String recipeName, Ingredient input, int multiplier, int processTime, float experience, Item output, Consumer<FinishedRecipe> finishedRecipeConsumer) {
        grinder(recipeName, input, processTime, experience)
                .addOutput(new ItemStack(output, 3 * multiplier), 55)
                .addOutput(new ItemStack(output, 4 * multiplier), 25)
                .addOutput(new ItemStack(output, 5 * multiplier), 18)
                .addOutput(new ItemStack(output, 6 * multiplier), 2)
                .save(finishedRecipeConsumer);
    }

    private void grinder_single(String recipeName, Ingredient input, ItemStack output, int processTime, float experience, Consumer<FinishedRecipe> finishedRecipeConsumer) {
        grinder(recipeName,input,processTime,experience)
                .addOutput(output,1)
                .save(finishedRecipeConsumer);
    }

    private void cookOre(Consumer<FinishedRecipe> finishedRecipeConsumer, Item input, Item output, float experience) {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input),output,experience,200,RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_" + Objects.requireNonNull(input.getRegistryName()).getPath(), has(input)).save(finishedRecipeConsumer, Objects.requireNonNull(output.getRegistryName()).getPath() + "_from_smelting_" + input.getRegistryName().getPath());
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input),output,experience,100,RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_" + input.getRegistryName().getPath(), has(input)).save(finishedRecipeConsumer, output.getRegistryName().getPath() + "_from_blasting_" + input.getRegistryName().getPath());
    }

    protected static void crudeMachineRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        ShapedRecipeBuilder.shaped(result).define('C', corner).define('F',face).define('#',core).pattern("CFC").pattern("F#F").pattern("CFC").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
    }

    protected static void basicMachineRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        ShapedRecipeBuilder.shaped(result).define('C', corner).define('F',face).define('#',core).define('R',Ingredient.of(Items.REDSTONE)).pattern("CFC").pattern("F#F").pattern("CRC").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
    }

    public static void sawmill(Consumer<FinishedRecipe> consumer, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition) {
        new SawmillRecipeBuilder(input,output,count,processTime,experience).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).addCondition(condition).save(consumer);
    }

    public static GrinderRecipeBuilder grinder(String recipeName, Ingredient input, int processTime, float experience) {
        return new GrinderRecipeBuilder(recipeName, input, processTime, experience).unlockedBy("has_grinder", has(Registration.GRINDER_BLOCK.get()));
    }
}
