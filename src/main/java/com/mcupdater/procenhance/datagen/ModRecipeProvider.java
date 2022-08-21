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
        crudeMachineRecipe(finishedRecipeConsumer, CRUDEGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.COBBLESTONE), Ingredient.of(Items.STICK));
        basicMachineRecipe(finishedRecipeConsumer, BASICGENERATOR_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.BRICK), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(finishedRecipeConsumer, BASICBATTERY_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(CAPACITOR.get()));
        crudeMachineRecipe(finishedRecipeConsumer, FURNACE_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE), Ingredient.of(Blocks.FURNACE));
        basicMachineRecipe(finishedRecipeConsumer, SAWMILL_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Items.IRON_AXE));
        basicMachineRecipe(finishedRecipeConsumer, GRINDER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Blocks.STONECUTTER));
        basicMachineRecipe(finishedRecipeConsumer, STONECUTTER_BLOCK.get(), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Blocks.STONE_BRICKS), Ingredient.of(Blocks.STONECUTTER));

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

    protected static void sawmill(Consumer<FinishedRecipe> consumer, Ingredient input, ItemLike output, int count, int processTime, float experience) {
        new SawmillRecipeBuilder(input,output,count,processTime,experience).unlockedBy("has_sawmill", has(Registration.SAWMILL_BLOCK.get())).save(consumer);
    }

    protected static GrinderRecipeBuilder grinder(String recipeName, Ingredient input, int processTime, float experience) {
        return new GrinderRecipeBuilder(recipeName, input, processTime, experience).unlockedBy("has_grinder", has(Registration.GRINDER_BLOCK.get()));
    }
}
