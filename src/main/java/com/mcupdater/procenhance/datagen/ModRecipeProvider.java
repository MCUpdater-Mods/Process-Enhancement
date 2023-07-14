package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.datagen.custom.BatteryUpgradeRecipeBuilder;
import com.mcupdater.procenhance.datagen.custom.GrinderRecipeBuilder;
import com.mcupdater.procenhance.datagen.custom.SawmillRecipeBuilder;
import com.mcupdater.procenhance.recipe.BatteryUpgradeRecipe;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
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
        crudeMachineRecipe(finishedRecipeConsumer, CRUDEGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.SMOOTH_STONE), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(finishedRecipeConsumer, SAWMILL_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.IRON_AXE));
        basicMachineRecipe(finishedRecipeConsumer, STONECUTTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE_BRICKS), Ingredient.of(Blocks.STONECUTTER));
        basicMachineRecipe(finishedRecipeConsumer, BUFFER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Tags.Items.CHESTS), Ingredient.of(Items.GLASS_BOTTLE));

        basicMachineRecipe(finishedRecipeConsumer, BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.BRICKS), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(finishedRecipeConsumer, INTERGENERATOR_BLOCK.get(), BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, ADVGENERATOR_BLOCK.get(), INTERGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, INDGENERATOR_BLOCK.get(), ADVGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, BASICLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.NETHER_BRICKS), Ingredient.of(Blocks.BLAST_FURNACE));
        upgradeMachineRecipe(finishedRecipeConsumer, INTERLAVAGENERATOR_BLOCK.get(), BASICLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, ADVLAVAGENERATOR_BLOCK.get(), INTERLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, INDLAVAGENERATOR_BLOCK.get(), ADVLAVAGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, BASICBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.DEEPSLATE_BRICKS), Ingredient.of(PLANT_DUST.get()));
        upgradeMachineRecipe(finishedRecipeConsumer, INTERBIOGENERATOR_BLOCK.get(), BASICBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, ADVBIOGENERATOR_BLOCK.get(), INTERBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, INDBIOGENERATOR_BLOCK.get(), ADVBIOGENERATOR_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, BASICBATTERY_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(CAPACITOR.get()));
        upgradeBatteryRecipe(finishedRecipeConsumer, INTBATTERY_BLOCK.get(), BASICBATTERY_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeBatteryRecipe(finishedRecipeConsumer, ADVBATTERY_BLOCK.get(), INTBATTERY_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeBatteryRecipe(finishedRecipeConsumer, INDBATTERY_BLOCK.get(), ADVBATTERY_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        crudeMachineRecipe(finishedRecipeConsumer, FURNACET1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        upgradeMachineRecipe(finishedRecipeConsumer, FURNACET2_BLOCK.get(), FURNACET1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, FURNACET3_BLOCK.get(), FURNACET2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, FURNACET4_BLOCK.get(), FURNACET3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, GRINDERT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));
        upgradeMachineRecipe(finishedRecipeConsumer, GRINDERT2_BLOCK.get(), GRINDERT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, GRINDERT3_BLOCK.get(), GRINDERT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, GRINDERT4_BLOCK.get(), GRINDERT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, TANKT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.GLASS));
        upgradeMachineRecipe(finishedRecipeConsumer, TANKT2_BLOCK.get(), TANKT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, TANKT3_BLOCK.get(), TANKT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, TANKT4_BLOCK.get(), TANKT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, PUMPT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Items.BUCKET));
        upgradeMachineRecipe(finishedRecipeConsumer, PUMPT2_BLOCK.get(), PUMPT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, PUMPT3_BLOCK.get(), PUMPT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, PUMPT4_BLOCK.get(), PUMPT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

        basicMachineRecipe(finishedRecipeConsumer, MINERT1_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Items.DIAMOND_PICKAXE));
        upgradeMachineRecipe(finishedRecipeConsumer, MINERT2_BLOCK.get(), MINERT1_BLOCK.get(), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.COPPER_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, MINERT3_BLOCK.get(), MINERT2_BLOCK.get(), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Blocks.IRON_BLOCK));
        upgradeMachineRecipe(finishedRecipeConsumer, MINERT4_BLOCK.get(), MINERT3_BLOCK.get(), Ingredient.of(Items.DIAMOND), Ingredient.of(Blocks.GOLD_BLOCK));

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
                .addOutput(new ItemStack(Items.DIAMOND, 1), 2)
                .save(finishedRecipeConsumer);
        grinder("basalt",Ingredient.of(Blocks.BASALT), 200, 0.1f)
                .addOutput(new ItemStack(Blocks.NETHERRACK, 1), 40)
                .addOutput(new ItemStack(Blocks.BLACKSTONE, 1), 40)
                .addOutput(new ItemStack(Blocks.SOUL_SAND, 1), 30)
                .addOutput(new ItemStack(Blocks.SOUL_SOIL, 1), 30)
                .addOutput(new ItemStack(Items.QUARTZ, 1), 20)
                .addOutput(new ItemStack(GOLD_DUST.get(), 1), 10)
                .addOutput(new ItemStack(Items.GLOWSTONE_DUST, 1), 10)
                .addOutput(new ItemStack(Blocks.GILDED_BLACKSTONE, 1), 5)
                .addOutput(new ItemStack(Items.NETHERITE_SCRAP, 1), 1)
                .save(finishedRecipeConsumer);
        grinder("soul_soil",Ingredient.of(Blocks.SOUL_SOIL), 200, 0.1f)
                .addOutput(new ItemStack(Blocks.WARPED_ROOTS, 1), 30)
                .addOutput(new ItemStack(Blocks.CRIMSON_ROOTS, 1), 30)
                .addOutput(new ItemStack(Items.NETHER_WART, 1), 1)
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
        grinder_single("warped_roots",Ingredient.of(Blocks.WARPED_ROOTS), new ItemStack(Items.CYAN_DYE,2), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("crimson_roots",Ingredient.of(Blocks.CRIMSON_ROOTS), new ItemStack(Items.RED_DYE,2), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("lily_pad",Ingredient.of(Blocks.LILY_PAD), new ItemStack(Items.GREEN_DYE,4), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("vines",Ingredient.of(Blocks.VINE), new ItemStack(Items.GREEN_DYE,2), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("gunpowder",Ingredient.of(Items.GUNPOWDER), new ItemStack(Items.GRAY_DYE,2), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("bone", Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL,6), 50,0.0f, finishedRecipeConsumer);
        grinder_single("blaze_rod", Ingredient.of(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER,4), 50,0.0f, finishedRecipeConsumer);
        grinder_single("flint", Ingredient.of(Items.FLINT), new ItemStack(Items.GUNPOWDER,1), 50,0.0f, finishedRecipeConsumer);
        grinder_single("wool", Ingredient.of(ItemTags.WOOL), new ItemStack(Items.STRING,4), 100, 0.01f, finishedRecipeConsumer);
        grinder_single("sugar_cane", Ingredient.of(Items.SUGAR_CANE), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("bamboo", Ingredient.of(Items.BAMBOO), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("kelp", Ingredient.of(Items.KELP), new ItemStack(PLANT_DUST.get(), 1), 50, 0.1f, finishedRecipeConsumer);
        grinder_single("copper_ingot", Ingredient.of(Items.COPPER_INGOT), new ItemStack(COPPER_DUST.get(), 1), 50, 0f, finishedRecipeConsumer);

        cookOre(finishedRecipeConsumer, IRON_DUST.get(), Items.IRON_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, COPPER_DUST.get(), Items.COPPER_INGOT, 0.7f);
        cookOre(finishedRecipeConsumer, GOLD_DUST.get(), Items.GOLD_INGOT, 1.0f);

        ShapedRecipeBuilder.shaped(CAPACITOR.get()).define('B',Ingredient.of(Items.BLUE_DYE)).define('C',Ingredient.of(Items.COPPER_INGOT)).define('P',Ingredient.of(Items.PAPER)).define('I',Ingredient.of(Items.IRON_NUGGET)).pattern("BBB").pattern("CPC").pattern("I I").unlockedBy("automatic", has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(DISENCHANTER_BLOCK.get()).define('L',Ingredient.of(Items.LAPIS_LAZULI)).define('A',Ingredient.of(Items.AMETHYST_SHARD)).define('B',Ingredient.of(Blocks.POLISHED_BLACKSTONE)).define('E',Ingredient.of(Blocks.ENCHANTING_TABLE)).pattern("LBA").pattern("BEB").pattern("ABL").unlockedBy("automatic", has(Blocks.ENCHANTING_TABLE)).save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(COPPERWIRE_BLOCKITEM.get(),16).define('C', Ingredient.of(Items.COPPER_INGOT)).pattern("CCC").unlockedBy("automatic",has(Items.COPPER_INGOT)).save(finishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(Items.SLIME_BALL).requires(Items.MILK_BUCKET,1).requires(PLANT_DUST.get(),1).unlockedBy("automatic",has(PLANT_DUST.get())).save(finishedRecipeConsumer);
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

    private void upgradeMachineRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Block baseMachine, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        ShapedRecipeBuilder.shaped(result).define('M', Ingredient.of(baseMachine)).define('U', upgradeItem1).define('u', upgradeItem2).pattern("UuU").pattern("uMu").pattern("UuU").unlockedBy("automatic", has(baseMachine)).save(finishedRecipeConsumer);
    }

    private void upgradeBatteryRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Block baseBattery, Ingredient upgradeItem1, Ingredient upgradeItem2) {
        BatteryUpgradeRecipeBuilder.shaped(result, BatteryUpgradeRecipe.SERIALIZER)
                .define('B', Ingredient.of(baseBattery))
                .define('U', upgradeItem1)
                .define('u', upgradeItem2)
                .pattern("UuU")
                .pattern("uBu")
                .pattern("UuU")
                .unlockedBy("has_battery", has(baseBattery))
                .save(finishedRecipeConsumer);
    }


    public static void sawmill(Consumer<FinishedRecipe> consumer, Ingredient input, ItemLike output, int count, int processTime, float experience, ICondition condition) {
        new SawmillRecipeBuilder(input,output,count,processTime,experience).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).addCondition(condition).save(consumer);
    }

    public static GrinderRecipeBuilder grinder(String recipeName, Ingredient input, int processTime, float experience) {
        return new GrinderRecipeBuilder(recipeName, input, processTime, experience).unlockedBy("has_grinder", has(Registration.GRINDERT1_BLOCK.get()));
    }
}
