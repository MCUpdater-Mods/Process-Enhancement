package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.datagen.custom.*;
import com.mcupdater.procenhance.recipe.ConfigCondition;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.*;
import net.neoforged.neoforge.fluids.FluidStack;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.mcupdater.procenhance.setup.Registration.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        crudeMachineRecipe(recipeOutput, CRUDEGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.SMOOTH_STONE), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(recipeOutput, SAWMILL_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.IRON_AXE));
        basicMachineRecipe(recipeOutput, STONECUTTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE_BRICKS), Ingredient.of(Blocks.STONECUTTER));
        basicMachineRecipe(recipeOutput, BUFFER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Tags.Items.CHESTS), Ingredient.of(Items.GLASS_BOTTLE));
        basicMachineRecipe(recipeOutput, AUTOPACKAGER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.PISTON), Ingredient.of(Blocks.CRAFTING_TABLE));

        basicMachineRecipe(recipeOutput, BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.BRICKS), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(recipeOutput, INTERGENERATOR_BLOCK.get(), BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, ADVGENERATOR_BLOCK.get(), INTERGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, INDGENERATOR_BLOCK.get(), ADVGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, BASICLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.NETHER_BRICKS), Ingredient.of(Blocks.BLAST_FURNACE));
        upgradeMachineRecipe(recipeOutput, INTERLAVAGENERATOR_BLOCK.get(), BASICLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, ADVLAVAGENERATOR_BLOCK.get(), INTERLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, INDLAVAGENERATOR_BLOCK.get(), ADVLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, BASICBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.DEEPSLATE_BRICKS), Ingredient.of(PLANT_DUST.get()));
        upgradeMachineRecipe(recipeOutput, INTERBIOGENERATOR_BLOCK.get(), BASICBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, ADVBIOGENERATOR_BLOCK.get(), INTERBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, INDBIOGENERATOR_BLOCK.get(), ADVBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, BASICBATTERY_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(CAPACITOR.get()));
        upgradeBatteryRecipe(recipeOutput, INTBATTERY_BLOCK.get(), BASICBATTERY_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeBatteryRecipe(recipeOutput, ADVBATTERY_BLOCK.get(), INTBATTERY_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeBatteryRecipe(recipeOutput, INDBATTERY_BLOCK.get(), ADVBATTERY_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        crudeMachineRecipe(recipeOutput, FURNACET1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(recipeOutput, FURNACET2_BLOCK.get(), FURNACET1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, FURNACET3_BLOCK.get(), FURNACET2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, FURNACET4_BLOCK.get(), FURNACET3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, GRINDERT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));
        upgradeMachineRecipe(recipeOutput, GRINDERT2_BLOCK.get(), GRINDERT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, GRINDERT3_BLOCK.get(), GRINDERT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, GRINDERT4_BLOCK.get(), GRINDERT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, TANKT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.GLASS));
        upgradeTankRecipe(recipeOutput, TANKT2_BLOCK.get(), TANKT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeTankRecipe(recipeOutput, TANKT3_BLOCK.get(), TANKT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeTankRecipe(recipeOutput, TANKT4_BLOCK.get(), TANKT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, PUMPT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Items.BUCKET));
        upgradeMachineRecipe(recipeOutput, PUMPT2_BLOCK.get(), PUMPT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, PUMPT3_BLOCK.get(), PUMPT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, PUMPT4_BLOCK.get(), PUMPT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        minerRecipe(recipeOutput, MINERT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Items.DIAMOND_PICKAXE));
        upgradeMinerRecipe(recipeOutput, MINERT2_BLOCK.get(), MINERT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMinerRecipe(recipeOutput, MINERT3_BLOCK.get(), MINERT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMinerRecipe(recipeOutput, MINERT4_BLOCK.get(), MINERT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, HARVESTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.STONE_HOE));
        basicMachineRecipe(recipeOutput, PLANTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS));
        // Sawmill recipes
        //sawmill(recipeOutput, Ingredient.of(ItemTags.DIRT), Items.DIAMOND, 1, 32, 0.05f, new ModLoadedCondition("testmod"));
        sawmill(recipeOutput, Ingredient.of(ItemTags.PLANKS), Items.STICK, 3, 16, 0.01f, null,"stick_from_planks");
        sawmill(recipeOutput, Ingredient.of(ItemTags.LOGS),Items.LADDER, 24, 32, 0.05f, null,"ladder_from_logs");
        sawmill(recipeOutput, Ingredient.of(ItemTags.PLANKS), Items.LADDER, 4, 16, 0.01f, null,"ladder_from_planks");

        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Items.ACACIA_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.STRIPPED_ACACIA_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.STRIPPED_ACACIA_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Blocks.ACACIA_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.ACACIA_LOGS),Items.ACACIA_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_STAIRS,1,16,0.01f, null,"acacia_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_SLAB,2,16,0.01f, null,"acacia_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.ACACIA_PLANKS),Blocks.ACACIA_BUTTON,4,16,0.01f, null,"acacia_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Items.BIRCH_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.STRIPPED_BIRCH_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.STRIPPED_BIRCH_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Blocks.BIRCH_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.BIRCH_LOGS),Items.BIRCH_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_STAIRS,1,16,0.01f, null,"birch_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_SLAB,2,16,0.01f, null, "birch_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.BIRCH_PLANKS),Blocks.BIRCH_BUTTON,4,16,0.01f, null,"birch_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Items.DARK_OAK_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.STRIPPED_DARK_OAK_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.STRIPPED_DARK_OAK_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Blocks.DARK_OAK_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.DARK_OAK_LOGS),Items.DARK_OAK_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_STAIRS,1,16,0.01f, null,"dark_oak_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_SLAB,2,16,0.01f, null,"dark_oak_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.DARK_OAK_PLANKS),Blocks.DARK_OAK_BUTTON,4,16,0.01f, null,"dark_oak_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Items.JUNGLE_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.STRIPPED_JUNGLE_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.STRIPPED_JUNGLE_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Blocks.JUNGLE_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.JUNGLE_LOGS),Items.JUNGLE_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_STAIRS,1,16,0.01f, null,"jungle_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_SLAB,2,16,0.01f, null,"jungle_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.JUNGLE_PLANKS),Blocks.JUNGLE_BUTTON,4,16,0.01f, null,"jungle_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Items.OAK_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.STRIPPED_OAK_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.STRIPPED_OAK_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Blocks.OAK_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS),Items.OAK_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_STAIRS,1,16,0.01f, null,"oak_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_SLAB,2,16,0.01f, null,"oak_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.OAK_PLANKS),Blocks.OAK_BUTTON,4,16,0.01f, null,"oak_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Items.SPRUCE_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.STRIPPED_SPRUCE_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.STRIPPED_SPRUCE_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Blocks.SPRUCE_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.SPRUCE_LOGS),Items.SPRUCE_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_STAIRS,1,16,0.01f, null,"spruce_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_SLAB,2,16,0.01f, null,"spruce_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.SPRUCE_PLANKS),Blocks.SPRUCE_BUTTON,4,16,0.01f, null,"spruce_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Items.MANGROVE_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.STRIPPED_MANGROVE_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.STRIPPED_MANGROVE_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Blocks.MANGROVE_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.MANGROVE_LOGS),Items.MANGROVE_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.MANGROVE_PLANKS),Blocks.MANGROVE_STAIRS,1,16,0.01f, null,"mangrove_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.MANGROVE_PLANKS),Blocks.MANGROVE_SLAB,2,16,0.01f, null,"mangrove_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.MANGROVE_PLANKS),Blocks.MANGROVE_BUTTON,4,16,0.01f, null,"mangrove_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Items.CRIMSON_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.STRIPPED_CRIMSON_STEM,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.STRIPPED_CRIMSON_HYPHAE,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CRIMSON_STEMS),Blocks.CRIMSON_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_STAIRS,1,16,0.01f, null,"crimson_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_SLAB,2,16,0.01f, null,"crimson_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.CRIMSON_PLANKS),Blocks.CRIMSON_BUTTON,4,16,0.01f, null,"crimson_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Items.WARPED_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.STRIPPED_WARPED_STEM,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.STRIPPED_WARPED_HYPHAE,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.WARPED_STEMS),Blocks.WARPED_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_STAIRS,1,16,0.01f, null,"warped_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_SLAB,2,16,0.01f, null,"warped_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.WARPED_PLANKS),Blocks.WARPED_BUTTON,4,16,0.01f, null,"warped_planks_to_button");

        sawmill(recipeOutput, Ingredient.of(Items.ACACIA_BOAT),Blocks.ACACIA_PLANKS,5,32,0f, null,"acacia_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.BIRCH_BOAT),Blocks.BIRCH_PLANKS,5,32,0f, null,"birch_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.DARK_OAK_BOAT),Blocks.DARK_OAK_PLANKS,5,32,0f, null,"dark_oak_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.JUNGLE_BOAT),Blocks.JUNGLE_PLANKS,5,32,0f, null,"jungle_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.OAK_BOAT),Blocks.OAK_PLANKS,5,32,0f, null,"oak_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.SPRUCE_BOAT),Blocks.SPRUCE_PLANKS,5,32,0f, null,"spruce_boat_planks");
        sawmill(recipeOutput, Ingredient.of(Items.MANGROVE_BOAT),Blocks.MANGROVE_PLANKS,5,32,0f, null,"mangrove_boat_planks");

        // Grinder recipes
        grinder(Ingredient.of(Blocks.COBBLESTONE),200,0.01f,"cobblestone").addOutput(new ItemStack(Blocks.GRAVEL,1), 1).save(recipeOutput);
        // Disabled recipe generation.  Generated recipes moved to resources
        grinder(Ingredient.of(Blocks.GRAVEL), 200, 0.01f,"gravel_noresources")
                .addCondition(new NotCondition(new ConfigCondition()))
                .addOutput(new ItemStack(Items.FLINT, 1), 1)
                .save(recipeOutput);
        grinder(Ingredient.of(Blocks.GRAVEL), 200, 0.01f,"gravel_resources")
                .addCondition(new ConfigCondition())
                .addOutput(new ItemStack(Blocks.DIRT,1), 40)
                .addOutput(new ItemStack(Blocks.SAND,1), 40)
                .addOutput(new ItemStack(Items.FLINT, 1), 20)
                .addOutput(new ItemStack(Items.COAL, 1), 10)
                .addOutput(new ItemStack(IRON_DUST.get(), 1), 10)
                .addOutput(new ItemStack(COPPER_DUST.get(),1),10)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 8)
                .addOutput(new ItemStack(Items.REDSTONE, 1), 8)
                .addOutput(new ItemStack(Items.LAPIS_LAZULI, 1), 8)
                .addOutput(new ItemStack(Items.DIAMOND, 1), 2)
                .save(recipeOutput);
        grinder(Ingredient.of(Blocks.BASALT), 200, 0.1f,"basalt")
                .addCondition(new ConfigCondition())
                .addOutput(new ItemStack(Blocks.NETHERRACK, 1), 40)
                .addOutput(new ItemStack(Blocks.BLACKSTONE, 1), 40)
                .addOutput(new ItemStack(Blocks.SOUL_SAND, 1), 30)
                .addOutput(new ItemStack(Blocks.SOUL_SOIL, 1), 30)
                .addOutput(new ItemStack(Items.QUARTZ, 1), 20)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 10)
                .addOutput(new ItemStack(Items.GLOWSTONE_DUST, 1), 10)
                .addOutput(new ItemStack(Blocks.GILDED_BLACKSTONE, 1), 5)
                .addOutput(new ItemStack(Items.NETHERITE_SCRAP, 1), 1)
                .save(recipeOutput);
        grinder(Ingredient.of(Blocks.SOUL_SOIL), 200, 0.1f,"soul_soil")
                .addOutput(new ItemStack(Blocks.WARPED_ROOTS, 1), 30)
                .addOutput(new ItemStack(Blocks.CRIMSON_ROOTS, 1), 30)
                .addOutput(new ItemStack(Items.NETHER_WART, 1), 1)
                .save(recipeOutput);
        grinder(Ingredient.of(Items.RAW_IRON),200,0.05f,"raw_iron")
                .addOutput(new ItemStack(IRON_DUST.get(), 1), 2)
                .addOutput(new ItemStack(IRON_DUST.get(), 2), 1)
                .save(recipeOutput);
        grinder(Ingredient.of(Items.RAW_GOLD),200,0.05f,"raw_gold")
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 2)
                .addOutput(new ItemStack(GOLD_DUST.get(), 2), 1)
                .save(recipeOutput);
        grinder(Ingredient.of(Items.RAW_COPPER),200,0.05f,"raw_copper")
                .addOutput(new ItemStack(COPPER_DUST.get(), 1), 2)
                .addOutput(new ItemStack(COPPER_DUST.get(), 2), 1)
                .save(recipeOutput);
        grinder_oreblock(Ingredient.of(ItemTags.IRON_ORES),1, 200,0.05f, IRON_DUST.get(), recipeOutput,"iron");
        grinder_oreblock(Ingredient.of(ItemTags.GOLD_ORES),1, 200,0.05f, GOLD_DUST.get(), recipeOutput,"gold");
        grinder_oreblock(Ingredient.of(ItemTags.COPPER_ORES),2, 200,0.05f, COPPER_DUST.get(), recipeOutput,"copper");
        grinder_oreblock(Ingredient.of(ItemTags.COAL_ORES), 1, 200, 0.05f, Items.COAL, recipeOutput,"coal");
        grinder_oreblock(Ingredient.of(ItemTags.REDSTONE_ORES), 3, 200, 0.05f, Items.REDSTONE, recipeOutput,"redstone");
        grinder_oreblock(Ingredient.of(ItemTags.LAPIS_ORES), 4, 200, 0.05f, Items.LAPIS_LAZULI, recipeOutput,"lapis");
        grinder_oreblock(Ingredient.of(ItemTags.DIAMOND_ORES), 1, 200, 0.05f, Items.DIAMOND, recipeOutput,"diamond");
        grinder_oreblock(Ingredient.of(ItemTags.EMERALD_ORES), 1, 200, 0.05f, Items.EMERALD, recipeOutput,"emerald");
        grinder_oreblock(Ingredient.of(Items.NETHER_QUARTZ_ORE), 2, 200, 0.05f, Items.QUARTZ, recipeOutput,"nether_quartz");
        grinder_oreblock(Ingredient.of(Items.ANCIENT_DEBRIS), 1, 200, 0.05f, Items.NETHERITE_SCRAP, recipeOutput,"ancient_debris");
        grinder_single(Ingredient.of(Items.DANDELION), new ItemStack(Items.YELLOW_DYE,3), 50,0.0f, recipeOutput,"dandelion");
        grinder_single(Ingredient.of(Items.POPPY), new ItemStack(Items.RED_DYE,3), 50,0.0f, recipeOutput,"poppy");
        grinder_single(Ingredient.of(Items.BLUE_ORCHID), new ItemStack(Items.LIGHT_BLUE_DYE,3), 50,0.0f, recipeOutput,"blue_orchid");
        grinder_single(Ingredient.of(Items.ALLIUM), new ItemStack(Items.MAGENTA_DYE,3), 50,0.0f, recipeOutput,"allium");
        grinder_single(Ingredient.of(Items.AZURE_BLUET), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, recipeOutput,"azure_bluet");
        grinder_single(Ingredient.of(Items.RED_TULIP), new ItemStack(Items.RED_DYE,3), 50,0.0f, recipeOutput,"red_tulip");
        grinder_single(Ingredient.of(Items.ORANGE_TULIP), new ItemStack(Items.ORANGE_DYE,3), 50,0.0f, recipeOutput,"orange_tulip");
        grinder_single(Ingredient.of(Items.WHITE_TULIP), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, recipeOutput,"white_tulip");
        grinder_single(Ingredient.of(Items.PINK_TULIP), new ItemStack(Items.PINK_DYE,3), 50,0.0f, recipeOutput,"pink_tulip");
        grinder_single(Ingredient.of(Items.OXEYE_DAISY), new ItemStack(Items.LIGHT_GRAY_DYE,3), 50,0.0f, recipeOutput,"oxeye_daisy");
        grinder_single(Ingredient.of(Items.CORNFLOWER), new ItemStack(Items.BLUE_DYE,3), 50,0.0f, recipeOutput,"cornflower");
        grinder_single(Ingredient.of(Items.LILY_OF_THE_VALLEY), new ItemStack(Items.WHITE_DYE,3), 50,0.0f, recipeOutput,"lily_of_the_valley");
        grinder_single(Ingredient.of(Items.WITHER_ROSE), new ItemStack(Items.BLACK_DYE,3), 50,0.0f, recipeOutput,"wither_rose");
        grinder_single(Ingredient.of(Items.SUNFLOWER), new ItemStack(Items.YELLOW_DYE,6), 50,0.0f, recipeOutput,"sunflower");
        grinder_single(Ingredient.of(Items.LILAC), new ItemStack(Items.MAGENTA_DYE,6), 50,0.0f, recipeOutput,"lilac");
        grinder_single(Ingredient.of(Items.ROSE_BUSH), new ItemStack(Items.RED_DYE,6), 50,0.0f, recipeOutput,"rose_bush");
        grinder_single(Ingredient.of(Items.PEONY), new ItemStack(Items.PINK_DYE,6), 50,0.0f, recipeOutput,"peony");
        grinder_single(Ingredient.of(Items.COCOA_BEANS), new ItemStack(Items.BROWN_DYE,3), 50,0.0f, recipeOutput,"cocoa_beans");
        grinder_single(Ingredient.of(Items.INK_SAC), new ItemStack(Items.BLACK_DYE, 3), 50, 0.0f, recipeOutput,"ink_sac");
        grinder_single(Ingredient.of(Items.LAPIS_LAZULI), new ItemStack(Items.BLUE_DYE, 3), 50, 0.0f, recipeOutput,"lapis_lazuli");
        grinder_single(Ingredient.of(Blocks.WARPED_ROOTS), new ItemStack(Items.CYAN_DYE,2), 50, 0.1f, recipeOutput,"warped_roots");
        grinder_single(Ingredient.of(Blocks.CRIMSON_ROOTS), new ItemStack(Items.RED_DYE,2), 50, 0.1f, recipeOutput,"crimson_roots");
        grinder_single(Ingredient.of(Blocks.LILY_PAD), new ItemStack(Items.GREEN_DYE,4), 50, 0.1f, recipeOutput,"lily_pad");
        grinder_single(Ingredient.of(Blocks.VINE), new ItemStack(Items.GREEN_DYE,2), 50, 0.1f, recipeOutput,"vine");
        grinder_single(Ingredient.of(Items.GUNPOWDER), new ItemStack(Items.GRAY_DYE,2), 50, 0.1f, recipeOutput,"gunpowder");
        grinder_single(Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL,6), 50,0.0f, recipeOutput,"bone");
        grinder_single(Ingredient.of(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER,4), 50,0.0f, recipeOutput,"blaze_rod");
        grinder_single(Ingredient.of(Items.FLINT), new ItemStack(Items.GUNPOWDER,1), 50,0.0f, recipeOutput,"flint");
        grinder_single(Ingredient.of(ItemTags.WOOL), new ItemStack(Items.STRING,4), 100, 0.01f, recipeOutput,"wool");
        grinder_single(Ingredient.of(Items.SUGAR_CANE), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, recipeOutput,"sugar_cane");
        grinder_single(Ingredient.of(Items.BAMBOO), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, recipeOutput,"bamboo");
        grinder_single(Ingredient.of(Items.KELP), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, recipeOutput,"kelp");
        grinder_single(Ingredient.of(Items.COPPER_INGOT), new ItemStack(COPPER_DUST.get(), 1), 50, 0f, recipeOutput,"copper_ingot");
        grinder_single(Ingredient.of(Blocks.SMOOTH_BASALT), new ItemStack(Blocks.BASALT, 1), 50, 0.1f, recipeOutput,"smooth_basalt");

        hydrator(recipeOutput, Ingredient.of(ItemTags.DIRT), new FluidStack(Fluids.WATER,100), Blocks.MUD, 1, 32, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.COBBLESTONE), new FluidStack(Fluids.WATER,100), Blocks.MOSS_BLOCK, 1, 32, null);

        dehydrator(recipeOutput, Ingredient.of(Blocks.MUD), new FluidStack(Fluids.WATER, 50), Blocks.CLAY, 1, 32, null,"clay");
        dehydrator(recipeOutput, Ingredient.of(Blocks.CLAY), new FluidStack(Fluids.WATER, 50), Blocks.TERRACOTTA, 1, 32, null,"terracotta");
        dehydrator(recipeOutput, Ingredient.of(Blocks.MAGMA_BLOCK), new FluidStack(Fluids.LAVA, 250), Blocks.BLACKSTONE, 1, 64, null,"lava");

        cookOre(recipeOutput, IRON_DUST.get(), Items.IRON_INGOT, 0.7f);
        cookOre(recipeOutput, COPPER_DUST.get(), Items.COPPER_INGOT, 0.7f);
        cookOre(recipeOutput, GOLD_DUST.get(), Items.GOLD_INGOT, 1.0f);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,CAPACITOR.get()).define('B',Ingredient.of(Items.BLUE_DYE)).define('C',Ingredient.of(Items.COPPER_INGOT)).define('P',Ingredient.of(Items.PAPER)).define('I',Ingredient.of(Items.IRON_NUGGET)).pattern("BBB").pattern("CPC").pattern("I I").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DISENCHANTER_BLOCK.get()).define('L',Ingredient.of(Items.LAPIS_LAZULI)).define('A',Ingredient.of(Items.AMETHYST_SHARD)).define('B',Ingredient.of(Blocks.POLISHED_BLACKSTONE)).define('E',Ingredient.of(Blocks.ENCHANTING_TABLE)).pattern("LBA").pattern("BEB").pattern("ABL").unlockedBy("automatic", has(Blocks.ENCHANTING_TABLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DECONSTRUCTOR_BLOCK.get()).define('A', Ingredient.of(Items.AMETHYST_BLOCK)).define('E', Ingredient.of(Items.EMERALD)).define('G', Ingredient.of(GRINDERT3_BLOCK.get())).pattern("AEA").pattern("EGE").pattern("AEA").unlockedBy("automatic", has(GRINDERT3_BLOCK.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,COBBLESTONESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.COBBLESTONE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.COBBLESTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,BASALTSOLIDIFIER_BLOCK.get()).define('B',Ingredient.of(Blocks.BASALT)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('I', Ingredient.of(Blocks.BLUE_ICE)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).define('S', Ingredient.of(Blocks.SOUL_SOIL)).pattern("CBC").pattern("IPL").pattern("CSC").unlockedBy("automatic", has(Blocks.BLUE_ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,COPPERWIRE_BLOCKITEM.get(),16).define('C', Ingredient.of(Items.COPPER_INGOT)).pattern("CCC").unlockedBy("automatic",has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SLIME_BALL).requires(Items.MILK_BUCKET,1).requires(PLANT_DUST.get(),1).unlockedBy("automatic",has(PLANT_DUST.get())).save(recipeOutput);
        basicMachineRecipe(recipeOutput,HYDRATOR_BLOCK.get(),Ingredient.of(Items.COPPER_INGOT),Ingredient.of(Items.IRON_INGOT),Ingredient.of(Items.GLASS_BOTTLE));
        basicMachineRecipe(recipeOutput,DEHYDRATOR_BLOCK.get(),Ingredient.of(Items.COPPER_INGOT),Ingredient.of(Items.IRON_INGOT),Ingredient.of(Items.POINTED_DRIPSTONE));

        // Add mod compatibility recipes
        for (BWGWoodSet woodSet : BWGWoodSet.woodsets()) {
            generateConditionalSawmillRecipes(recipeOutput, "biomeswevegone", woodSet.name(), woodSet.logstem(), woodSet.planks(), woodSet.stairs(), woodSet.slab(), woodSet.pressurePlate(), woodSet.sign(), woodSet.door(), woodSet.trapdoor(), woodSet.strippedLogStem(), woodSet.strippedWood(), woodSet.fence(), woodSet.fenceGate(), woodSet.boatItem() != null ? woodSet.boatItem().get() : null, woodSet.button(), null);
        }
    }

    private void hydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidInput, ItemLike output, int count, int processTime, ICondition condition) {
        hydrator(recipeOutput, itemInput, fluidInput, output, count, processTime, condition, null);
    }

    private void hydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidInput, ItemLike output, int count, int processTime, ICondition condition, String recipeName) {
        new HydratorRecipeBuilder(itemInput,fluidInput,output,count,processTime,recipeName).unlockedBy("has_hydrator", has(Registration.HYDRATOR_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

    private void dehydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidOutput, ItemLike output, int count, int processTime, ICondition condition) {
        dehydrator(recipeOutput,itemInput,fluidOutput,output,count,processTime,condition,"");
    }

    private void dehydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidOutput, ItemLike output, int count, int processTime, ICondition condition, String recipeName) {
        new DehydratorRecipeBuilder(itemInput,fluidOutput,output,count,processTime,recipeName).unlockedBy("has_dehydrator", has(Registration.DEHYDRATOR_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

    private void grinder_oreblock(Ingredient input, int multiplier, int processTime, float experience, Item output, RecipeOutput recipeOutput, String recipeName) {
        grinder(input, processTime, experience, recipeName)
                .addOutput(new ItemStack(output, 3 * multiplier), 55)
                .addOutput(new ItemStack(output, 4 * multiplier), 25)
                .addOutput(new ItemStack(output, 5 * multiplier), 18)
                .addOutput(new ItemStack(output, 6 * multiplier), 2)
                .save(recipeOutput);
    }

    private void grinder_single(Ingredient input, ItemStack output, int processTime, float experience, RecipeOutput recipeOutput, String recipeName) {
        grinder(input,processTime,experience, recipeName)
                .addOutput(output,1)
                .save(recipeOutput);
    }

    private void cookOre(RecipeOutput recipeOutput, Item input, Item output, float experience) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input),RecipeCategory.MISC,output,experience, 200).unlockedBy("has_" + Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(input)).getPath(), has(input)).save(recipeOutput, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_smelting_" + BuiltInRegistries.ITEM.getKey(input).getPath()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input),RecipeCategory.MISC,output,experience,100).unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(input).getPath(), has(input)).save(recipeOutput, BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_blasting_" + BuiltInRegistries.ITEM.getKey(input).getPath());
    }

    protected static void crudeMachineRecipe(RecipeOutput recipeOutput, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('C', corner)
                .define('F', face)
                .define('#', core)
                .pattern("CFC")
                .pattern("F#F")
                .pattern("CFC")
                .unlockedBy("automatic", has(Items.COPPER_INGOT))
                .save(recipeOutput);
    }

    protected static void basicMachineRecipe(RecipeOutput recipeOutput, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('C', corner)
                .define('F', face)
                .define('#', core)
                .define('R', Ingredient.of(Items.REDSTONE))
                .pattern("CFC")
                .pattern("F#F")
                .pattern("CRC")
                .unlockedBy("automatic", has(Items.COPPER_INGOT))
                .save(recipeOutput);
    }

    private void upgradeMachineRecipe(RecipeOutput recipeOutput, ItemLike result, Block baseMachine, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('M', Ingredient.of(baseMachine))
                .define('U', upgradeItem1)
                .define('u', upgradeItem2)
                .pattern("UuU")
                .pattern("uMu")
                .pattern("UuU")
                .unlockedBy("automatic", has(baseMachine))
                .save(recipeOutput);
    }

    private void upgradeBatteryRecipe(RecipeOutput recipeOutput, ItemLike result, Block baseBattery, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        BatteryUpgradeRecipeBuilder.shaped(result, BATTERYUPGRADE_SERIALIZER.get())
                .define('B', Ingredient.of(baseBattery))
                .define('U', upgradeItem1)
                .define('u', upgradeItem2)
                .pattern("UuU")
                .pattern("uBu")
                .pattern("UuU")
                .unlockedBy("has_battery", has(baseBattery))
                .save(recipeOutput);
    }

    private void upgradeTankRecipe(RecipeOutput recipeOutput, ItemLike result, Block baseTank, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        TankUpgradeRecipeBuilder.shaped(result, TANK_UPGRADE_SERIALIZER.get())
                .define('T', Ingredient.of(baseTank))
                .define('U', upgradeItem1)
                .define('u', upgradeItem2)
                .pattern("UuU")
                .pattern("uTu")
                .pattern("UuU")
                .unlockedBy("has_tank", has(baseTank))
                .save(recipeOutput);
    }

    protected static void minerRecipe(RecipeOutput recipeOutput, ItemLike result, Ingredient corner, Ingredient face, Ingredient core) {
        MinerRecipeBuilder.shaped(result, MINER_RECIPE_SERIALIZER.get())
                .define('C', corner)
                .define('F',face)
                .define('#',core)
                .define('R',Ingredient.of(Items.REDSTONE))
                .pattern("CFC")
                .pattern("F#F")
                .pattern("CRC")
                .unlockedBy("automatic", has(Items.COPPER_INGOT))
                .save(recipeOutput);
    }

    private void upgradeMinerRecipe(RecipeOutput recipeOutput, ItemLike result, Block baseMiner, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        MinerRecipeBuilder.shaped(result, MINER_RECIPE_SERIALIZER.get())
                .define('B', Ingredient.of(baseMiner))
                .define('U', upgradeItem1)
                .define('u', upgradeItem2)
                .pattern("UuU")
                .pattern("uBu")
                .pattern("UuU")
                .unlockedBy("has_miner", has(baseMiner))
                .save(recipeOutput);
    }

    public static void sawmill(RecipeOutput recipeOutput, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition) {
        sawmill(recipeOutput,input,output,count,processTime,experience,condition,null);
    }

    public static void sawmill(RecipeOutput recipeOutput, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition, String recipeName) {
        new SawmillRecipeBuilder(input,output,count,processTime,experience,recipeName)
                .unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get()))
                .addCondition(condition)
                .save(recipeOutput);
    }

    public static GrinderRecipeBuilder grinder(Ingredient input, int processTime, float experience, String recipeName) {
        return new GrinderRecipeBuilder(input, processTime, experience, recipeName).unlockedBy("has_grinder", has(Registration.GRINDERT1_BLOCK.get()));
    }

    // Conditional loading methods
    public static void generateConditionalSawmillRecipes(RecipeOutput consumer, String modId, String woodName, ItemLike log, ItemLike planks, ItemLike stairs, ItemLike slab, ItemLike pressurePlate, ItemLike sign, ItemLike door, ItemLike trapDoor, ItemLike strippedLog, ItemLike strippedWood, ItemLike fence, ItemLike fenceGate, ItemLike boat, ItemLike button, ItemLike ladder) {
        ICondition condition = new ModLoadedCondition(modId);
        sawmillConditional(consumer, Ingredient.of(log),planks,6,32,0.05f, condition, modId);
        if (stairs != null) sawmillConditional(consumer, Ingredient.of(log),stairs,6,32,0.05f, condition, modId);
        if (slab != null) sawmillConditional(consumer, Ingredient.of(log),slab,12,32,0.05f, condition, modId);
        if (pressurePlate != null) sawmillConditional(consumer, Ingredient.of(log),pressurePlate,3,32,0.05f, condition, modId);
        if (sign != null) sawmillConditional(consumer, Ingredient.of(log),sign,3,32,0.05f, condition, modId);
        if (door != null) sawmillConditional(consumer, Ingredient.of(log),door,3,32,0.05f, condition, modId);
        if (trapDoor != null) sawmillConditional(consumer, Ingredient.of(log),trapDoor,2,32,0.05f, condition, modId);
        if (strippedLog != null) sawmillConditional(consumer, Ingredient.of(log),strippedLog,1,16,0.01f, condition, modId);
        if (strippedWood != null) sawmillConditional(consumer, Ingredient.of(log),strippedWood,1,16,0.01f, condition, modId);
        if (fence != null) sawmillConditional(consumer, Ingredient.of(log),fence,4,32,0.05f, condition, modId);
        if (fenceGate != null) sawmillConditional(consumer, Ingredient.of(log),fenceGate,2,32,0.05f, condition, modId);
        if (boat != null) sawmillConditional(consumer, Ingredient.of(log),boat,1,32,0.05f, condition, modId);
        if (ladder != null) sawmillConditional(consumer, Ingredient.of(log),ladder, 24, 32, 0.05f, condition, modId);
        if (stairs != null) sawmillConditional(consumer, Ingredient.of(planks),stairs,1,16,0.01f, condition, modId,woodName + "_planks_to_stairs");
        if (slab != null) sawmillConditional(consumer, Ingredient.of(planks),slab,2,16,0.01f, condition, modId, woodName + "_planks_to_slab");
        if (button != null) sawmillConditional(consumer, Ingredient.of(planks),button,4,16,0.05f, condition, modId, woodName + "_planks_to_button");
        if (ladder != null) sawmillConditional(consumer, Ingredient.of(planks),ladder, 4, 16, 0.05f, condition, modId, woodName + "_planks_to_ladder");
        if (boat != null) sawmillConditional(consumer, Ingredient.of(boat),planks, 5, 32, 0f, condition, modId,woodName + "_boat_planks");
    }

    public static void sawmillConditional(RecipeOutput recipeOutput, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition, String modId) {
        sawmillConditional(recipeOutput,input,output,count,processTime,experience,condition,modId,null);
    }

    public static void sawmillConditional(RecipeOutput recipeOutput, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition, String modId, String recipeName) {
        new CompatSawmillRecipeBuilder(modId,input,output,count,processTime,experience,recipeName).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

}