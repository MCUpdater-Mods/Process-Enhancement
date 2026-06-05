package com.mcupdater.procenhance.datagen;

import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.custom.*;
import com.mcupdater.procenhance.recipe.ConfigCondition;
import com.mcupdater.procenhance.recipe.result.ItemRecipeResult;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import com.mcupdater.procenhance.setup.Registration;
import dev.shadowsoffire.apotheosis.Apoth;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.*;
import net.neoforged.neoforge.fluids.FluidStack;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.block.set.BWGBlockSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;
import net.silentchaos512.gear.setup.SgBlocks;
import org.cyclops.integrateddynamics.RegistryEntries;

import java.util.List;
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

        basicMachineRecipe(recipeOutput, GENERATORT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.BRICKS), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(recipeOutput, GENERATORT2_BLOCK.get(), GENERATORT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, GENERATORT3_BLOCK.get(), GENERATORT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, GENERATORT4_BLOCK.get(), GENERATORT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, COMPACTSOLARGENERATOR_BLOCK.get(),4).define('C', Ingredient.of(Items.GRAY_CONCRETE)).define('$', Ingredient.of(CAPACITOR.get())).define('#', Ingredient.of(Blocks.DAYLIGHT_DETECTOR)).pattern("$#$").pattern("CCC").unlockedBy("automatic", has(Blocks.DAYLIGHT_DETECTOR)).save(recipeOutput);
        basicMachineRecipe(recipeOutput, SOLARGENERATORT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.DAYLIGHT_DETECTOR));
        upgradeMachineRecipe(recipeOutput, SOLARGENERATORT2_BLOCK.get(), SOLARGENERATORT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, SOLARGENERATORT3_BLOCK.get(), SOLARGENERATORT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, SOLARGENERATORT4_BLOCK.get(), SOLARGENERATORT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, LAVAGENERATORT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.NETHER_BRICKS), Ingredient.of(Blocks.BLAST_FURNACE));
        upgradeMachineRecipe(recipeOutput, LAVAGENERATORT2_BLOCK.get(), LAVAGENERATORT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, LAVAGENERATORT3_BLOCK.get(), LAVAGENERATORT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, LAVAGENERATORT4_BLOCK.get(), LAVAGENERATORT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, BIOGENERATORT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.DEEPSLATE_BRICKS), Ingredient.of(PLANT_DUST_TAG));
        upgradeMachineRecipe(recipeOutput, BIOGENERATORT2_BLOCK.get(), BIOGENERATORT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, BIOGENERATORT3_BLOCK.get(), BIOGENERATORT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, BIOGENERATORT4_BLOCK.get(), BIOGENERATORT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, BATTERYT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.REDSTONE_BLOCK));
        upgradeBatteryRecipe(recipeOutput, BATTERYT2_BLOCK.get(), BATTERYT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeBatteryRecipe(recipeOutput, BATTERYT3_BLOCK.get(), BATTERYT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeBatteryRecipe(recipeOutput, BATTERYT4_BLOCK.get(), BATTERYT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        crudeMachineRecipe(recipeOutput, FURNACET1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(recipeOutput, FURNACET2_BLOCK.get(), FURNACET1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, FURNACET3_BLOCK.get(), FURNACET2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, FURNACET4_BLOCK.get(), FURNACET3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(recipeOutput, GRINDERT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));
        upgradeMachineRecipe(recipeOutput, GRINDERT2_BLOCK.get(), GRINDERT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(recipeOutput, GRINDERT3_BLOCK.get(), GRINDERT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(recipeOutput, GRINDERT4_BLOCK.get(), GRINDERT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        crudeMachineRecipe(recipeOutput, TANKT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.GLASS));
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
        basicMachineRecipe(recipeOutput, SOILMANAGER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Blocks.BONE_BLOCK));

        basicMachineRecipe(recipeOutput, CONCRETEMIXER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE_BRICKS), Ingredient.of(Blocks.CRAFTING_TABLE));
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

        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_PLANKS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_STAIRS,6,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_SLAB,12,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_PRESSURE_PLATE,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Items.CHERRY_SIGN,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_DOOR,3,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_TRAPDOOR,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.STRIPPED_CHERRY_LOG,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.STRIPPED_CHERRY_WOOD,1,16,0.01f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_FENCE,4,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Blocks.CHERRY_FENCE_GATE,2,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(ItemTags.CHERRY_LOGS),Items.CHERRY_BOAT,1,32,0.05f, null);
        sawmill(recipeOutput, Ingredient.of(Blocks.CHERRY_PLANKS),Blocks.CHERRY_STAIRS,1,16,0.01f, null,"cherry_planks_to_stairs");
        sawmill(recipeOutput, Ingredient.of(Blocks.CHERRY_PLANKS),Blocks.CHERRY_SLAB,2,16,0.01f, null,"cherry_planks_to_slab");
        sawmill(recipeOutput, Ingredient.of(Blocks.CHERRY_PLANKS),Blocks.CHERRY_BUTTON,4,16,0.01f, null,"cherry_planks_to_button");

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
        // Test recipe
        /*
        grinder(Ingredient.of(Blocks.OAK_LOG), 1,0f,"test")
                .addOutput(Items.STICK.getDefaultInstance(), 1)
                .addOutput(getTag("c", "dusts/zinc"),1,100)
                .save(recipeOutput);
        */
        grinder(Ingredient.of(Blocks.COBBLESTONE),200,0.01f,"cobblestone").addOutput(new ItemStack(Blocks.GRAVEL,1), 1).save(recipeOutput);
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
                .addOutput(getTag("c","dusts/iron"), 1, 10)
                .addOutput(getTag("c","dusts/copper"),1,10)
                .addOutput(getTag("c","dusts/gold"), 1, 8)
                .addOutput(getTag("c","dusts/aluminum"),1,10)
                .addOutput(getTag("c","dusts/iridium"),1,1)
                .addOutput(getTag("c","dusts/lead"),1,9)
                .addOutput(getTag("c","dusts/nickel"),1,9)
                .addOutput(getTag("c","dusts/osmium"),1,7)
                .addOutput(getTag("c","dusts/platinum"),1,5)
                .addOutput(getTag("c","dusts/silver"),1,8)
                .addOutput(getTag("c","dusts/tin"),1,10)
                .addOutput(getTag("c","dusts/titanium"),1,5)
                .addOutput(getTag("c","dusts/uranium"),1,6)
                .addOutput(getTag("c","dusts/zinc"),1,10)
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
                .addOutput(getTag("c","dusts/gold"),1, 10)
                .addOutput(new ItemStack(Items.GLOWSTONE_DUST, 1), 10)
                .addOutput(new ItemStack(Blocks.GILDED_BLACKSTONE, 1), 5)
                .addOutput(new ItemStack(Items.NETHERITE_SCRAP, 1), 1)
                .addOutput(getTag("c","dusts/iesnium"),1,1)
                .save(recipeOutput);
        grinder(Ingredient.of(Blocks.SOUL_SOIL), 200, 0.1f,"soul_soil")
                .addOutput(new ItemStack(Blocks.WARPED_ROOTS, 1), 30)
                .addOutput(new ItemStack(Blocks.CRIMSON_ROOTS, 1), 30)
                .addOutput(new ItemStack(Items.NETHER_WART, 1), 1)
                .save(recipeOutput);
        /*
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
        */
        grinder_tagged_resource(recipeOutput, "iron", 1);
        grinder_tagged_resource(recipeOutput, "gold", 1);
        grinder_tagged_resource(recipeOutput, "copper", 2);
        // Compatibility additions - Not native Process Enhancement resources
        grinder_tagged_resource(recipeOutput, "aluminum", 1);
        grinder_tagged_resource(recipeOutput, "iesnium", 1);
        grinder_tagged_resource(recipeOutput, "iridium", 1);
        grinder_tagged_resource(recipeOutput, "lead", 1);
        grinder_tagged_resource(recipeOutput, "nickel", 1);
        grinder_tagged_resource(recipeOutput, "osmium", 1);
        grinder_tagged_resource(recipeOutput, "platinum", 1);
        grinder_tagged_resource(recipeOutput, "silver", 1);
        grinder_tagged_resource(recipeOutput, "tin", 1);
        grinder_tagged_resource(recipeOutput, "titanium", 1);
        grinder_tagged_resource(recipeOutput, "uranium", 1);
        grinder_tagged_resource(recipeOutput, "zinc", 1);
        grinder_tagged_resource(recipeOutput, "crimson_iron", 1);
        grinder_tagged_resource(recipeOutput, "azure_silver", 1);
        //
        /*
        grinder_oreblock(Ingredient.of(ItemTags.IRON_ORES),1, 200,0.05f, IRON_DUST_TAG, recipeOutput,"iron");
        grinder_oreblock(Ingredient.of(ItemTags.GOLD_ORES),1, 200,0.05f, GOLD_DUST_TAG, recipeOutput,"gold");
        grinder_oreblock(Ingredient.of(ItemTags.COPPER_ORES),2, 200,0.05f, COPPER_DUST_TAG, recipeOutput,"copper");
        */
        grinder_oreblock(Ingredient.of(ItemTags.COAL_ORES), 1, 200, 0.05f, Items.COAL, "coal").save(recipeOutput);
        grinder_oreblock(Ingredient.of(ItemTags.REDSTONE_ORES), 3, 200, 0.05f, Items.REDSTONE, "redstone").save(recipeOutput);
        grinder_oreblock(Ingredient.of(ItemTags.LAPIS_ORES), 4, 200, 0.05f, Items.LAPIS_LAZULI, "lapis").save(recipeOutput);
        grinder_oreblock(Ingredient.of(ItemTags.DIAMOND_ORES), 1, 200, 0.05f, Items.DIAMOND, "diamond").save(recipeOutput);
        grinder_oreblock(Ingredient.of(ItemTags.EMERALD_ORES), 1, 200, 0.05f, Items.EMERALD, "emerald").save(recipeOutput);
        grinder_oreblock(Ingredient.of(Items.NETHER_QUARTZ_ORE), 2, 200, 0.05f, Items.QUARTZ, "nether_quartz").save(recipeOutput);
        grinder_oreblock(Ingredient.of(Items.ANCIENT_DEBRIS), 1, 200, 0.05f, Items.NETHERITE_SCRAP, "ancient_debris").save(recipeOutput);
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
        grinder_single(Ingredient.of(Blocks.SMOOTH_BASALT), new ItemStack(Blocks.BASALT, 1), 50, 0.1f, recipeOutput,"smooth_basalt");
        grinder_single(Ingredient.of(Tags.Items.NETHERRACKS), new ItemStack(NETHER_DUST.get(), 1), 50, 0.1f, recipeOutput, "netherrack");
        grinder_single(Ingredient.of(Blocks.QUARTZ_BLOCK), new ItemStack(Items.QUARTZ,4), 50, 0.1f, recipeOutput, "quartz_block");
        grinder_single(Ingredient.of(Blocks.QUARTZ_PILLAR), new ItemStack(Items.QUARTZ,4), 50, 0.1f, recipeOutput, "quartz_pillar");
        grinder_single(Ingredient.of(Blocks.QUARTZ_BRICKS), new ItemStack(Items.QUARTZ,4), 50, 0.1f, recipeOutput, "quartz_bricks");
        grinder_single(Ingredient.of(Blocks.CHISELED_QUARTZ_BLOCK), new ItemStack(Items.QUARTZ,4), 50, 0.1f, recipeOutput, "chiseled_quartz");
        grinder_single(Ingredient.of(Blocks.SMOOTH_QUARTZ), new ItemStack(Items.QUARTZ,4), 50, 0.1f, recipeOutput, "smooth_quartz");

        hydrator(recipeOutput, Ingredient.of(ItemTags.DIRT), new FluidStack(Fluids.WATER,100), Blocks.MUD, 1, 32, null);
        hydrator(recipeOutput, Ingredient.of(Tags.Items.STONES), new FluidStack(Fluids.WATER,100), Blocks.MOSS_BLOCK, 1, 32, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.COBBLESTONE), new FluidStack(Fluids.WATER,100), Blocks.MOSSY_COBBLESTONE, 1, 32, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.STONE_BRICKS), new FluidStack(Fluids.WATER,100), Blocks.MOSSY_STONE_BRICKS, 1, 32, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.BLACK_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.BLACK_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.BLUE_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.BLUE_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.BROWN_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.BROWN_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.CYAN_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.CYAN_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.GRAY_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.GRAY_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.GREEN_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.GREEN_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.LIGHT_BLUE_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.LIGHT_BLUE_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.LIGHT_GRAY_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.LIGHT_GRAY_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.LIME_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.LIME_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.MAGENTA_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.MAGENTA_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.ORANGE_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.ORANGE_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.PINK_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.PINK_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.PURPLE_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.PURPLE_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.RED_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.RED_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.WHITE_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.WHITE_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.YELLOW_CONCRETE_POWDER), new FluidStack(Fluids.WATER,10), Blocks.YELLOW_CONCRETE, 1, 1, null);
        hydrator(recipeOutput, Ingredient.of(Blocks.SNOW_BLOCK), new FluidStack(Fluids.WATER,1000), Blocks.ICE, 1, 128, null);

        dehydrator(recipeOutput, Ingredient.of(Blocks.MUD), new FluidStack(Fluids.WATER, 50), Blocks.CLAY, 1, 32, null,"clay");
        dehydrator(recipeOutput, Ingredient.of(Blocks.CLAY), new FluidStack(Fluids.WATER, 50), Blocks.TERRACOTTA, 1, 32, null,"terracotta");
        dehydrator(recipeOutput, Ingredient.of(Items.KELP), new FluidStack(Fluids.WATER, 10), Items.DRIED_KELP, 1, 16, null, "dried_kelp");
        dehydrator(recipeOutput, Ingredient.of(Blocks.MAGMA_BLOCK), new FluidStack(Fluids.LAVA, 250), Blocks.BLACKSTONE, 1, 64, null,"blackstone");
        dehydrator(recipeOutput, Ingredient.of(NETHER_DUST.get()), new FluidStack(Fluids.LAVA, 100), Items.BONE_MEAL, 1, 64, null, "bone_meal");
        dehydrator(recipeOutput, Ingredient.of(NETHER_DUST_BLOCK.get()), new FluidStack(Fluids.LAVA, 1000), Items.BONE_BLOCK, 1, 512, null, "bone_block");

        cookOre(recipeOutput, "iron_dust", IRON_DUST_TAG, Items.IRON_INGOT, 0.7f);
        cookOre(recipeOutput, "copper_dust", COPPER_DUST_TAG, Items.COPPER_INGOT, 0.7f);
        cookOre(recipeOutput, "gold_dust", GOLD_DUST_TAG, Items.GOLD_INGOT, 1.0f);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,CAPACITOR.get()).define('B',Ingredient.of(Items.BLUE_DYE)).define('C',Ingredient.of(Items.COPPER_INGOT)).define('P',Ingredient.of(Items.PAPER)).define('I',Ingredient.of(Items.IRON_NUGGET)).pattern("BBB").pattern("CPC").pattern("I I").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DISENCHANTER_BLOCK.get()).define('L',Ingredient.of(Items.LAPIS_LAZULI)).define('A',Ingredient.of(Items.AMETHYST_SHARD)).define('B',Ingredient.of(Blocks.POLISHED_BLACKSTONE)).define('E',Ingredient.of(Blocks.ENCHANTING_TABLE)).pattern("LBA").pattern("BEB").pattern("ABL").unlockedBy("automatic", has(Blocks.ENCHANTING_TABLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DECONSTRUCTOR_BLOCK.get()).define('A', Ingredient.of(Items.AMETHYST_BLOCK)).define('E', Ingredient.of(Items.EMERALD)).define('G', Ingredient.of(GRINDERT3_BLOCK.get())).pattern("AEA").pattern("EGE").pattern("AEA").unlockedBy("automatic", has(GRINDERT3_BLOCK.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,COBBLESTONESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.COBBLESTONE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.COBBLESTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,BASALTSOLIDIFIER_BLOCK.get()).define('B',Ingredient.of(Blocks.BASALT)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('I', Ingredient.of(Blocks.BLUE_ICE)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).define('S', Ingredient.of(Blocks.SOUL_SOIL)).pattern("CBC").pattern("IPL").pattern("CSC").unlockedBy("automatic", has(Blocks.BLUE_ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ANDESITESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.ANDESITE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.ANDESITE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,CALCITESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.CALCITE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.CALCITE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DEEPSLATESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.DEEPSLATE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.DEEPSLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,DIORITESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.DIORITE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.DIORITE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,GRANITESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.GRANITE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.GRANITE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,STONESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.STONE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.STONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TUFFSOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.TUFF)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('W', Ingredient.of(Items.WATER_BUCKET)).define('L',Ingredient.of(Items.LAVA_BUCKET)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("WPL").pattern("CSC").unlockedBy("automatic", has(Blocks.TUFF)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ENDSTONESOLIDIFIER_BLOCK.get()).define('S',Ingredient.of(Blocks.END_STONE)).define('C', Ingredient.of(Items.COPPER_INGOT)).define('F', Ingredient.of(Items.CHORUS_FLOWER)).define('P', Ingredient.of(Items.IRON_PICKAXE)).pattern("CSC").pattern("FPF").pattern("CSC").unlockedBy("automatic", has(Blocks.END_STONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,COPPERWIRE_BLOCKITEM.get(),16).define('C', Ingredient.of(Items.COPPER_INGOT)).pattern("CCC").unlockedBy("automatic",has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SLIME_BALL).requires(Items.MILK_BUCKET,1).requires(PLANT_DUST.get(),1).unlockedBy("automatic",has(PLANT_DUST.get())).save(recipeOutput);
        basicMachineRecipe(recipeOutput,HYDRATOR_BLOCK.get(),Ingredient.of(Items.COPPER_INGOT),Ingredient.of(Items.IRON_INGOT),Ingredient.of(Items.GLASS_BOTTLE));
        basicMachineRecipe(recipeOutput,DEHYDRATOR_BLOCK.get(),Ingredient.of(Items.COPPER_INGOT),Ingredient.of(Items.IRON_INGOT),Ingredient.of(Items.POINTED_DRIPSTONE));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,ELECTRIC_LANTERN_BLOCK.get(), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GLASS)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.GLASS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,COPPER_ELECTRIC_LANTERN_BLOCK.get(), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GLASS)).define('D',Ingredient.of(COPPER_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.GLASS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,NETHER_ELECTRIC_LANTERN_BLOCK.get(), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GLASS)).define('D',Ingredient.of(NETHER_DUST.get())).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.GLASS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,PRIDE_ELECTRIC_LANTERN_BLOCK.get(), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GLASS)).define('D',Ingredient.of(IRON_DUST_TAG)).define('E', Ingredient.of(Items.EGG)).pattern("CDC").pattern("GEG").pattern("CDC").unlockedBy("automatic", has(Items.GLASS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CHISEL_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('I', Ingredient.of(Items.IRON_INGOT)).pattern("SSI").unlockedBy("automatic", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STAIRMAKER_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('I', Ingredient.of(Items.IRON_INGOT)).pattern("ISS").unlockedBy("automatic", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NETHER_DUST_BLOCK.get(), 1).requires(NETHER_DUST.get(), 9).unlockedBy("automatic", has(NETHER_DUST.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WOODEN_CRUSHER_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('#', Ingredient.of(ItemTags.LOGS)).pattern(" ##").pattern(" ##").pattern("S  ").unlockedBy("automatic", has(ItemTags.LOGS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STONE_CRUSHER_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('#', Ingredient.of(Items.SMOOTH_STONE)).pattern(" ##").pattern(" ##").pattern("S  ").unlockedBy("automatic", has(ItemTags.LOGS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRON_CRUSHER_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('#', Ingredient.of(Items.IRON_BLOCK)).pattern(" ##").pattern(" ##").pattern("S  ").unlockedBy("automatic", has(ItemTags.LOGS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_CRUSHER_ITEM.get(),1).define('S', Ingredient.of(Items.STICK)).define('#', Ingredient.of(Items.DIAMOND_BLOCK)).pattern(" ##").pattern(" ##").pattern("S  ").unlockedBy("automatic", has(ItemTags.LOGS)).save(recipeOutput);
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(DIAMOND_CRUSHER_ITEM.get()),
                Ingredient.of(Items.NETHERITE_BLOCK),
                RecipeCategory.TOOLS,NETHERITE_CRUSHER_ITEM.asItem()
        ).unlocks("automatic", has(DIAMOND_CRUSHER_ITEM.get())).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID,"smithing/netherite_crusher"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WRENCH_ITEM.get(), 1).define('B', Ingredient.of(Items.BRICK)).define('I', Ingredient.of(Items.IRON_INGOT)).pattern(" I ").pattern(" BI").pattern("B  ").unlockedBy("automatic", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, CONCEALEDWIRE_BLOCK.get(), 8).requires(Ingredient.of(COPPERWIRE_BLOCKITEM.get()),8).requires(Ingredient.of(Items.WHITE_CONCRETE_POWDER)).unlockedBy("automatic", has(COPPERWIRE_BLOCKITEM.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CONCEALEDWIRE_WALL_BLOCK.get(), 6).define('#', Ingredient.of(CONCEALEDWIRE_BLOCKITEM.get())).pattern("###").pattern("###").unlockedBy("automatic", has(CONCEALEDWIRE_BLOCKITEM.get())).save(recipeOutput);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.REDSTONE, CONCEALEDWIRE_WALL_BLOCKITEM.get(), CONCEALEDWIRE_BLOCKITEM);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.BLACK), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.BLACK_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.BLACK_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.WHITE), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.WHITE_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.WHITE_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.GRAY), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GRAY_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.GRAY_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.LIGHT_GRAY), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.LIGHT_GRAY_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.BLUE), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.BLUE_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.BLUE_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.LIGHT_BLUE), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.LIGHT_BLUE_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.GREEN), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.GREEN_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.GREEN_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.LIME), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.LIME_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.LIME_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.BROWN), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.BROWN_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.BROWN_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.ORANGE), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.ORANGE_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.ORANGE_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.CYAN), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.CYAN_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.CYAN_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.MAGENTA), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.MAGENTA_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.MAGENTA_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.PURPLE), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.PURPLE_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.PURPLE_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.PINK), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.PINK_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.PINK_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.RED), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.RED_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.RED_GLAZED_TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,TERRACOTTA_LANTERN_BLOCK.get(DyeColor.YELLOW), 16).define('C',Ingredient.of(Blocks.GRAY_CONCRETE)).define('G',Ingredient.of(Blocks.YELLOW_GLAZED_TERRACOTTA)).define('D',Ingredient.of(IRON_DUST_TAG)).pattern("CDC").pattern("GDG").pattern("CDC").unlockedBy("automatic", has(Items.YELLOW_GLAZED_TERRACOTTA)).save(recipeOutput);


        // Add chisel recipes
        chisel(recipeOutput, Ingredient.of(Blocks.STONE), (BlockItem)Blocks.STONE_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.STONE_BRICKS), (BlockItem)Blocks.CHISELED_STONE_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.GRANITE), (BlockItem)Blocks.POLISHED_GRANITE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.DIORITE), (BlockItem)Blocks.POLISHED_DIORITE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.ANDESITE), (BlockItem)Blocks.POLISHED_ANDESITE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.DEEPSLATE), (BlockItem)Blocks.POLISHED_DEEPSLATE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.POLISHED_DEEPSLATE), (BlockItem)Blocks.DEEPSLATE_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.TUFF), (BlockItem)Blocks.POLISHED_TUFF.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.POLISHED_TUFF), (BlockItem)Blocks.TUFF_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.PACKED_MUD), (BlockItem)Blocks.MUD_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.SANDSTONE), (BlockItem)Blocks.CUT_SANDSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.CUT_SANDSTONE), (BlockItem)Blocks.CHISELED_SANDSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.RED_SANDSTONE), (BlockItem)Blocks.CUT_RED_SANDSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.CUT_RED_SANDSTONE), (BlockItem)Blocks.CHISELED_RED_SANDSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.PRISMARINE), (BlockItem)Blocks.PRISMARINE_BRICKS.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.BASALT), (BlockItem)Blocks.POLISHED_BASALT.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.BLACKSTONE), (BlockItem)Blocks.POLISHED_BLACKSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.POLISHED_BLACKSTONE), (BlockItem)Blocks.CHISELED_POLISHED_BLACKSTONE.asItem(), null);
        chisel(recipeOutput, Ingredient.of(Blocks.END_STONE), (BlockItem)Blocks.END_STONE_BRICKS.asItem(), null);

        // Add stairmaker recipes
        stairmaker(recipeOutput, Ingredient.of(Blocks.OAK_PLANKS), (BlockItem)Blocks.OAK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.SPRUCE_PLANKS), (BlockItem)Blocks.SPRUCE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.BIRCH_PLANKS), (BlockItem)Blocks.BIRCH_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.JUNGLE_PLANKS), (BlockItem)Blocks.JUNGLE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.ACACIA_PLANKS), (BlockItem)Blocks.ACACIA_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.DARK_OAK_PLANKS), (BlockItem)Blocks.DARK_OAK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.MANGROVE_PLANKS), (BlockItem)Blocks.MANGROVE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.CHERRY_PLANKS), (BlockItem)Blocks.CHERRY_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.BAMBOO_PLANKS), (BlockItem)Blocks.BAMBOO_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.BAMBOO_MOSAIC), (BlockItem)Blocks.BAMBOO_MOSAIC_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.CRIMSON_PLANKS), (BlockItem)Blocks.CRIMSON_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WARPED_PLANKS), (BlockItem)Blocks.WARPED_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.STONE), (BlockItem)Blocks.STONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.COBBLESTONE), (BlockItem)Blocks.COBBLESTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.MOSSY_COBBLESTONE), (BlockItem)Blocks.MOSSY_COBBLESTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.STONE_BRICKS), (BlockItem)Blocks.STONE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.MOSSY_STONE_BRICKS), (BlockItem)Blocks.MOSSY_STONE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.GRANITE), (BlockItem)Blocks.GRANITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_GRANITE), (BlockItem)Blocks.POLISHED_GRANITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.DIORITE), (BlockItem)Blocks.DIORITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_DIORITE), (BlockItem)Blocks.POLISHED_DIORITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.ANDESITE), (BlockItem)Blocks.ANDESITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_ANDESITE), (BlockItem)Blocks.POLISHED_ANDESITE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.COBBLED_DEEPSLATE), (BlockItem)Blocks.COBBLED_DEEPSLATE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_DEEPSLATE), (BlockItem)Blocks.POLISHED_DEEPSLATE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.DEEPSLATE), (BlockItem)Blocks.POLISHED_DEEPSLATE_STAIRS.asItem(), null, "deepslate_stairs");
        stairmaker(recipeOutput, Ingredient.of(Blocks.DEEPSLATE_BRICKS), (BlockItem)Blocks.DEEPSLATE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.DEEPSLATE_TILES), (BlockItem)Blocks.DEEPSLATE_TILE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.TUFF), (BlockItem)Blocks.TUFF_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_TUFF), (BlockItem)Blocks.POLISHED_TUFF_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.TUFF_BRICKS), (BlockItem)Blocks.TUFF_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.BRICKS), (BlockItem)Blocks.BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.MUD_BRICKS), (BlockItem)Blocks.MUD_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.SANDSTONE), (BlockItem)Blocks.SANDSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.SMOOTH_SANDSTONE), (BlockItem)Blocks.SMOOTH_SANDSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.RED_SANDSTONE), (BlockItem)Blocks.RED_SANDSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.SMOOTH_RED_SANDSTONE), (BlockItem)Blocks.SMOOTH_RED_SANDSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.PRISMARINE), (BlockItem)Blocks.PRISMARINE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.PRISMARINE_BRICKS), (BlockItem)Blocks.PRISMARINE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.DARK_PRISMARINE), (BlockItem)Blocks.DARK_PRISMARINE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.NETHER_BRICKS), (BlockItem)Blocks.NETHER_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.RED_NETHER_BRICKS), (BlockItem)Blocks.RED_NETHER_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.BLACKSTONE), (BlockItem)Blocks.BLACKSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_BLACKSTONE), (BlockItem)Blocks.POLISHED_BLACKSTONE_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.POLISHED_BLACKSTONE_BRICKS), (BlockItem)Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.END_STONE_BRICKS), (BlockItem)Blocks.END_STONE_BRICK_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.END_STONE), (BlockItem)Blocks.END_STONE_BRICK_STAIRS.asItem(), null,"end_stone");
        stairmaker(recipeOutput, Ingredient.of(Blocks.PURPUR_BLOCK), (BlockItem)Blocks.PURPUR_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.QUARTZ_BLOCK), (BlockItem)Blocks.QUARTZ_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.SMOOTH_QUARTZ), (BlockItem)Blocks.SMOOTH_QUARTZ_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.CUT_COPPER), (BlockItem)Blocks.CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.EXPOSED_CUT_COPPER), (BlockItem)Blocks.EXPOSED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WEATHERED_CUT_COPPER), (BlockItem)Blocks.WEATHERED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.OXIDIZED_CUT_COPPER), (BlockItem)Blocks.OXIDIZED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WAXED_CUT_COPPER), (BlockItem)Blocks.WAXED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WAXED_EXPOSED_CUT_COPPER), (BlockItem)Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WAXED_WEATHERED_CUT_COPPER), (BlockItem)Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS.asItem(), null);
        stairmaker(recipeOutput, Ingredient.of(Blocks.WAXED_OXIDIZED_CUT_COPPER), (BlockItem)Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS.asItem(), null);

        // Add mod compatibility recipes
        // === Biomes We've Gone ===
        for (BWGWoodSet woodSet : BWGWoodSet.woodsets()) {
            generateConditionalSawmillRecipes(recipeOutput, "biomeswevegone", woodSet.name(), woodSet.logstem(), woodSet.planks(), woodSet.stairs(), woodSet.slab(), woodSet.pressurePlate(), woodSet.sign(), woodSet.door(), woodSet.trapdoor(), woodSet.strippedLogStem(), woodSet.strippedWood(), woodSet.fence(), woodSet.fenceGate(), woodSet.boatItem() != null ? woodSet.boatItem().get() : null, woodSet.button(), null);
        }
        for (BWGSandSet sandSet : BWGSandSet.getSandSets()) {
            stairmakerConditional(recipeOutput, Ingredient.of(sandSet.getSandstone()), (BlockItem)sandSet.getSandstoneStairs().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
            stairmakerConditional(recipeOutput, Ingredient.of(sandSet.getSmoothSandstone()), (BlockItem)sandSet.getSmoothSandstoneStairs().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
            chiselConditional(recipeOutput, Ingredient.of(sandSet.getSandstone()), (BlockItem)sandSet.getCutSandstone().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
            chiselConditional(recipeOutput, Ingredient.of(sandSet.getCutSandstone()), (BlockItem)sandSet.getChiseledSandstone().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        }
        for (BWGBlockSet blockSet : BWGBlockSet.getBlockSets()) {
            stairmakerConditional(recipeOutput, Ingredient.of(blockSet.getBase()), (BlockItem)blockSet.getStairs().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        }
        stairmakerConditional(recipeOutput, Ingredient.of(BWGBlocks.CATTAIL_THATCH.get()), (BlockItem)BWGBlocks.CATTAIL_THATCH_STAIRS.get().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        chiselConditional(recipeOutput, Ingredient.of(BWGBlocks.DACITE_SET.getBase()), (BlockItem)BWGBlocks.DACITE_BRICKS_SET.getBase().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        chiselConditional(recipeOutput, Ingredient.of(BWGBlocks.DACITE_BRICKS_SET.getBase()), (BlockItem)BWGBlocks.DACITE_TILE_SET.getBase().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        chiselConditional(recipeOutput, Ingredient.of(BWGBlocks.RED_ROCK_SET.getBase()), (BlockItem)BWGBlocks.RED_ROCK_BRICKS_SET.getBase().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");
        chiselConditional(recipeOutput, Ingredient.of(BWGBlocks.PACKED_PALE_MUD.get()), (BlockItem)BWGBlocks.PALE_MUD_BRICKS_SET.getBase().asItem(), new ModLoadedCondition("biomeswevegone"), "biomeswevegone");

        // === Ars Nouveau ===
        generateConditionalSawmillRecipes(recipeOutput, "ars_nouveau", "archwood", BlockRegistry.BLAZING_LOG.get(), BlockRegistry.ARCHWOOD_PLANK.get(), BlockRegistry.ARCHWOOD_STAIRS.get(), BlockRegistry.ARCHWOOD_SLABS.get(), BlockRegistry.ARCHWOOD_PPlate.get(), null, BlockRegistry.ARCHWOOD_DOOR.get(), BlockRegistry.ARCHWOOD_TRAPDOOR.get(), BlockRegistry.STRIPPED_AWLOG_RED.get(), BlockRegistry.STRIPPED_AWWOOD_RED.get(), BlockRegistry.ARCHWOOD_FENCE.get(), BlockRegistry.ARCHWOOD_FENCE_GATE.get(), null, BlockRegistry.ARCHWOOD_BUTTON.get(), null);
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_PLANK.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau","cascading_archwood_planks");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.STRIPPED_AWLOG_BLUE.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.STRIPPED_AWWOOD_BLUE.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_STAIRS.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_stairs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_SLABS.get(), 12, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_slabs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_PPlate.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_pplates");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_DOOR.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_doors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_TRAPDOOR.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_trapdoors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE.get(), 4, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_fence");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.CASCADING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE_GATE.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "cascading_archwood_fencegate");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_PLANK.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_planks");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.STRIPPED_AWLOG_GREEN.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.STRIPPED_AWWOOD_GREEN.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_STAIRS.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_stairs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_SLABS.get(), 12, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_slabs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_PPlate.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_pplates");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_DOOR.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_doors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_TRAPDOOR.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_trapdoors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE.get(), 4, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_fence");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.FLOURISHING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE_GATE.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flourishing_archwood_fencegate");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_PLANK.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau","vexing_archwood_planks");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.STRIPPED_AWLOG_PURPLE.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.STRIPPED_AWWOOD_PURPLE.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_STAIRS.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_stairs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_SLABS.get(), 12, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_slabs");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_PPlate.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_pplates");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_DOOR.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_doors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_TRAPDOOR.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_trapdoors");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE.get(), 4, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_fence");
        sawmillConditional(recipeOutput, Ingredient.of(BlockRegistry.VEXING_LOG.get()), BlockRegistry.ARCHWOOD_FENCE_GATE.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "vexing_archwood_fencegate");
        for (String prefix : LibBlockNames.DECORATIVE_SOURCESTONE) {
            stairmakerConditional(recipeOutput, Ingredient.of(BlockRegistry.getBlock(prefix)), (BlockItem)BlockRegistry.getBlock(prefix + "_stairs").asItem(), new ModLoadedCondition("ars_nouveau"), "ars_nouveau");
        }

        // === Ars Elemental ===
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_PLANK.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_elemental"), "ars_elemental","flashing_archwood_planks");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), ModItems.FLASHING_ARCHWOOD_LOG_STRIPPED.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_elemental"), "ars_elemental");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), ModItems.FLASHING_ARCHWOOD_STRIPPED.get(), 1, 16, 0.01f, new ModLoadedCondition("ars_elemental"), "ars_elemental");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_STAIRS.get(), 6, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_stairs");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_SLABS.get(), 12, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_slabs");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_PPlate.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_pplates");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_DOOR.get(), 3, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_doors");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_TRAPDOOR.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_trapdoors");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_FENCE.get(), 4, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_fence");
        sawmillConditional(recipeOutput, Ingredient.of(ModItems.FLASHING_ARCHWOOD_LOG.get()), BlockRegistry.ARCHWOOD_FENCE_GATE.get(), 2, 32, 0.05f, new ModLoadedCondition("ars_nouveau"), "ars_nouveau", "flashing_archwood_fencegate");

        // === Apotheosis ===
        grinderConditional(recipeOutput, Ingredient.of(Apoth.Items.GEM.value()), 200, 0.05f, new ModLoadedCondition("apotheosis"), "apotheosis", "gem_dust")
                .addOutput(new ItemStack(Apoth.Items.GEM_DUST, 1), 1)
                .save(recipeOutput);
        RecipeOutput conditionalRecipeOutput = recipeOutput.withConditions(List.of(new ModLoadedCondition("apotheosis")).toArray(new ICondition[0]));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Apoth.Items.IRON_UPGRADE_SMITHING_TEMPLATE.value()), Ingredient.of(STONE_CRUSHER_ITEM.get()), Ingredient.of(Items.IRON_BLOCK), RecipeCategory.TOOLS, IRON_CRUSHER_ITEM.get()).unlocks("automatic", has(Apoth.Items.IRON_UPGRADE_SMITHING_TEMPLATE.value())).save(conditionalRecipeOutput,ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "smithing/compat/apotheosis/iron_crusher"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Apoth.Items.DIAMOND_UPGRADE_SMITHING_TEMPLATE.value()), Ingredient.of(IRON_CRUSHER_ITEM.get()), Ingredient.of(Items.DIAMOND_BLOCK), RecipeCategory.TOOLS, DIAMOND_CRUSHER_ITEM.get()).unlocks("automatic", has(Apoth.Items.IRON_UPGRADE_SMITHING_TEMPLATE.value())).save(conditionalRecipeOutput,ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "smithing/compat/apotheosis/diamond_crusher"));

        // Silent Gear
        generateConditionalSawmillRecipes(recipeOutput, "silentgear", "netherwood", SgBlocks.NETHERWOOD_LOG.get(), SgBlocks.NETHERWOOD_PLANKS.get(), SgBlocks.NETHERWOOD_STAIRS.get(), SgBlocks.NETHERWOOD_SLAB.get(), null, null, SgBlocks.NETHERWOOD_DOOR.get(), SgBlocks.NETHERWOOD_TRAPDOOR.get(), SgBlocks.STRIPPED_NETHERWOOD_LOG.get(), SgBlocks.STRIPPED_NETHERWOOD_WOOD.get(), SgBlocks.NETHERWOOD_FENCE.get(), SgBlocks.NETHERWOOD_FENCE_GATE.get(), null, null, null);

        // Occultism
        generateConditionalSawmillRecipes(recipeOutput, "occultism", "otherworld", OccultismBlocks.OTHERWORLD_LOG.get(), OccultismBlocks.OTHERPLANKS.get(), OccultismBlocks.OTHERPLANKS_STAIRS.get(), OccultismBlocks.OTHERPLANKS_SLAB.get(), OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get(), OccultismBlocks.OTHERPLANKS_SIGN.get(), OccultismBlocks.OTHERPLANKS_DOOR.get(), OccultismBlocks.OTHERPLANKS_TRAPDOOR.get(), OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get(), OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get(), OccultismBlocks.OTHERPLANKS_FENCE.get(), OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(), null, OccultismBlocks.OTHERPLANKS_BUTTON.get(), null);
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERSTONE), (BlockItem)OccultismBlocks.OTHERSTONE_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERCOBBLESTONE), (BlockItem)OccultismBlocks.OTHERCOBBLESTONE_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.POLISHED_OTHERSTONE), (BlockItem)OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERSTONE_BRICKS), (BlockItem)OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERROCK), (BlockItem)OccultismBlocks.OTHERROCK_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERROCK_BRICKS), (BlockItem)OccultismBlocks.OTHERROCK_BRICKS_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERCOBBLEROCK), (BlockItem)OccultismBlocks.OTHERCOBBLEROCK_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        stairmakerConditional(recipeOutput, Ingredient.of(OccultismBlocks.POLISHED_OTHERROCK), (BlockItem)OccultismBlocks.POLISHED_OTHERROCK_STAIRS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        chiselConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERSTONE), (BlockItem)OccultismBlocks.OTHERSTONE_BRICKS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        chiselConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERSTONE_BRICKS), (BlockItem)OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        chiselConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERROCK), (BlockItem)OccultismBlocks.OTHERROCK_BRICKS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        chiselConditional(recipeOutput, Ingredient.of(OccultismBlocks.OTHERROCK_BRICKS), (BlockItem)OccultismBlocks.CHISELED_OTHERROCK_BRICKS.asItem(), new ModLoadedCondition("occultism"), "occultism");
        grinder_oreblock(Ingredient.of(OccultismBlocks.IESNIUM_ORE_NATURAL.get()), 1, 200, 0.05f, OccultismItems.IESNIUM_DUST.asItem(), "natural_iesnium_ore")
                .save(recipeOutput.withConditions(new ModLoadedCondition("occultism")));

        // === Integrated Dynamics ===
        generateConditionalSawmillRecipes(recipeOutput, "integrateddynamics", "menril", RegistryEntries.BLOCK_MENRIL_LOG.get(), RegistryEntries.BLOCK_MENRIL_PLANKS.get(), RegistryEntries.BLOCK_MENRIL_PLANKS_STAIRS.get(), null, null, null, null, null,RegistryEntries.BLOCK_MENRIL_LOG_STRIPPED.get(),RegistryEntries.BLOCK_MENRIL_WOOD_STRIPPED.get(),null,null,null,null,null);
        sawmillConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_MENRIL_LOG_FILLED.get()), RegistryEntries.BLOCK_MENRIL_PLANKS.get(), 6, 32, 0.05f, new ModLoadedCondition("integrateddynamics"), "integrateddynamics","filled_menril_planks");
        sawmillConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_MENRIL_LOG_FILLED.get()), RegistryEntries.BLOCK_MENRIL_PLANKS_STAIRS.get(), 6, 32, 0.05f, new ModLoadedCondition("integrateddynamics"), "integrateddynamics", "filled_menril_stairs");
        sawmillConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_MENRIL_LOG_FILLED.get()), RegistryEntries.BLOCK_MENRIL_LOG_STRIPPED.get(), 1, 16, 0.01f, new ModLoadedCondition("integrateddynamics"), "integrateddynamics", "filled_menril_log_stripped");
        sawmillConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_MENRIL_LOG_FILLED.get()), RegistryEntries.BLOCK_MENRIL_WOOD_STRIPPED.get(), 1, 16, 0.01f, new ModLoadedCondition("integrateddynamics"), "integrateddynamics", "filled_menril_wood_stripped");
        chiselConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_CRYSTALIZED_MENRIL_BLOCK.get()), (BlockItem)RegistryEntries.BLOCK_CRYSTALIZED_MENRIL_BRICK.get().asItem(), new ModLoadedCondition("integrateddynamics"), "integrateddynamics");
        chiselConditional(recipeOutput, Ingredient.of(RegistryEntries.BLOCK_CRYSTALIZED_CHORUS_BLOCK.get()), (BlockItem)RegistryEntries.BLOCK_CRYSTALIZED_CHORUS_BRICK.get().asItem(), new ModLoadedCondition("integrateddynamics"), "integrateddynamics");
    }

    private void hydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidInput, ItemLike output, int count, int processTime, ICondition condition) {
        hydrator(recipeOutput, itemInput, fluidInput, output, count, processTime, condition, null);
    }

    private void hydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidInput, ItemLike output, int count, int processTime, ICondition condition, String recipeName) {
        new HydratorRecipeBuilder(itemInput,fluidInput,output,count,processTime,recipeName).unlockedBy("has_hydrator", has(Registration.HYDRATOR_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

    private void dehydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidOutput, ItemLike output, int count, int processTime, ICondition condition) {
        dehydrator(recipeOutput,itemInput,fluidOutput,output,count,processTime,condition,null);
    }

    private void dehydrator(RecipeOutput recipeOutput, Ingredient itemInput, FluidStack fluidOutput, ItemLike output, int count, int processTime, ICondition condition, String recipeName) {
        new DehydratorRecipeBuilder(itemInput,fluidOutput,output,count,processTime,recipeName).unlockedBy("has_dehydrator", has(Registration.DEHYDRATOR_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

    private void grinder_tagged_resource(RecipeOutput recipeOutput, String resourceName, int multiplier) {
        TagKey<Item> oreBlock = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ores/" + resourceName));
        TagKey<Item> rawOre = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "raw_materials/" + resourceName));
        TagKey<Item> rawOreBlock = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_" + resourceName));
        TagKey<Item> dust = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/" + resourceName));
        TagKey<Item> ingot = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/" + resourceName));

        grinder_oreblock(oreBlock, multiplier, 200, 0.05f, dust, recipeOutput, "ore_" + resourceName);
        grinder_raw_to_dust(recipeOutput, rawOre, dust, resourceName);
        grinder_single(ingot, dust, 1, 50, 0, recipeOutput, "ingot_" + resourceName);
        grinder(Ingredient.of(rawOreBlock), 200, 0.05f, "raw_block_" + resourceName)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreBlock)))
                .addCondition(new NotCondition(new TagEmptyCondition(dust)))
                .addOutput(dust, 18, 1)
                .save(recipeOutput);
    }

    private void grinder_raw_to_dust(RecipeOutput recipeOutput, TagKey<Item> raw, TagKey<Item> dust, String resourceName) {
        grinder(Ingredient.of(raw), 200, 0.05f, "raw_" + resourceName)
                .addCondition(new NotCondition(new TagEmptyCondition(raw)))
                .addCondition(new NotCondition(new TagEmptyCondition(dust)))
                .addOutput(dust, 1, 2)
                .addOutput(dust, 2, 1)
                .save(recipeOutput);
    }

    private GrinderRecipeBuilder grinder_oreblock(Ingredient input, int multiplier, int processTime, float experience, Item output, String recipeName) {
        return grinder(input, processTime, experience, recipeName)
                .addOutput(new ItemStack(output, 3 * multiplier), 55)
                .addOutput(new ItemStack(output, 4 * multiplier), 25)
                .addOutput(new ItemStack(output, 5 * multiplier), 18)
                .addOutput(new ItemStack(output, 6 * multiplier), 2);
    }

    private void grinder_oreblock(TagKey<Item> input, int multiplier, int processTime, float experience, TagKey<Item> output, RecipeOutput recipeOutput, String recipeName) {
        grinder(Ingredient.of(input), processTime, experience, recipeName)
                .addCondition(new NotCondition(new TagEmptyCondition(input)))
                .addCondition(new NotCondition(new TagEmptyCondition(output)))
                .addOutput(output, 3 * multiplier, 55)
                .addOutput(output, 4 * multiplier, 25)
                .addOutput(output, 5 * multiplier, 18)
                .addOutput(output, 6 * multiplier, 2)
                .save(recipeOutput);
    }


    private void grinder_single(Ingredient input, ItemStack output, int processTime, float experience, RecipeOutput recipeOutput, String recipeName) {
        grinder(input,processTime,experience, recipeName)
                .addOutput(output,1)
                .save(recipeOutput);
    }

    private void grinder_single(TagKey<Item> input, TagKey<Item> output, int count, int processTime, float experience, RecipeOutput recipeOutput, String recipeName) {
        grinder(Ingredient.of(input),processTime,experience, recipeName)
                .addCondition(new NotCondition(new TagEmptyCondition(input)))
                .addCondition(new NotCondition(new TagEmptyCondition(output)))
                .addOutput(output, count,1)
                .save(recipeOutput);
    }

    private void cookOre(RecipeOutput recipeOutput, String itemName, ItemLike input, Item output, float experience) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input),RecipeCategory.MISC,output,experience, 200).unlockedBy("has_" + itemName, has(input)).save(recipeOutput, BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_smelting_" + itemName);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input),RecipeCategory.MISC,output,experience,100).unlockedBy("has_" + itemName, has(input)).save(recipeOutput, BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_blasting_" + itemName);
    }

    private void cookOre(RecipeOutput recipeOutput, String itemName, TagKey<Item> input, Item output, float experience) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input),RecipeCategory.MISC,output,experience, 200).unlockedBy("has_" + itemName, has(input)).save(recipeOutput, BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_smelting_" + itemName);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input),RecipeCategory.MISC,output,experience,100).unlockedBy("has_" + itemName, has(input)).save(recipeOutput, BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_blasting_" + itemName);
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
                .define('R', Ingredient.of(CAPACITOR.get()))
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
        new SawmillRecipeBuilder(input,new ItemRecipeResult(output,count),processTime,experience,recipeName)
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
        if (stairs != null) {
            sawmillConditional(consumer, Ingredient.of(log),stairs,6,32,0.05f, condition, modId);
            stairmakerConditional(consumer, Ingredient.of(planks), (BlockItem)stairs.asItem(), condition, modId);
        }
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
        sawmillConditional(recipeOutput, input, new ItemRecipeResult(output,count),processTime,experience,condition,modId,recipeName);
    }

    public static void sawmillConditional(RecipeOutput recipeOutput, Ingredient input, RecipeResult result, int processTime, float experience, ICondition condition, String modId, String recipeName) {
        new SawmillRecipeBuilder(modId,input,result,processTime,experience,recipeName).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).addCondition(condition).save(recipeOutput);
    }

    public static GrinderRecipeBuilder grinderConditional(RecipeOutput recipeOutput, Ingredient input, int processTime, float experience, ICondition condition, String modId, String recipeName) {
        return new GrinderRecipeBuilder(modId, input, processTime, experience, recipeName)
                .unlockedBy("has_grinder", has(Registration.GRINDERT1_BLOCK.get()))
                .addCondition(condition);
    }

    private void chisel(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition) {
        chisel(recipeOutput, input, result, condition, null);
    }

    public static void chisel(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String recipeName) {
        new ChiselRecipeBuilder(input, result, recipeName)
                .unlockedBy("has_chisel", has(CHISEL_ITEM.get()))
                .addCondition(condition)
                .save(recipeOutput);
    }

    private void chiselConditional(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String modId) {
        chiselConditional(recipeOutput, input, result, condition, modId, null);
    }

    public static void chiselConditional(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String modId, String recipeName) {
        new ChiselRecipeBuilder(modId, input, result, recipeName)
                .unlockedBy("has_chisel", has(CHISEL_ITEM.get()))
                .addCondition(condition)
                .save(recipeOutput);
    }

    private void stairmaker(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition) {
        stairmaker(recipeOutput, input, result, condition, null);
    }

    public static void stairmaker(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String recipeName) {
        new StairmakerRecipeBuilder(input, result, recipeName)
                .unlockedBy("has_stairmaker", has(STAIRMAKER_ITEM.get()))
                .addCondition(condition)
                .save(recipeOutput);
    }

    public static void stairmakerConditional(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String modId) {
        stairmakerConditional(recipeOutput, input, result, condition, modId, null);
    }

    public static void stairmakerConditional(RecipeOutput recipeOutput, Ingredient input, BlockItem result, ICondition condition, String modId, String recipeName) {
        new StairmakerRecipeBuilder(modId, input, result, recipeName)
                .unlockedBy("has_stairmaker", has(STAIRMAKER_ITEM.get()))
                .addCondition(condition)
                .save(recipeOutput);
    }

    public static TagKey<Item> getTag(String namespace, String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
    }
}