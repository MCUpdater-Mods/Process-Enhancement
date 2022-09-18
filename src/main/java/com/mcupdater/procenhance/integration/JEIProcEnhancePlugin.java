package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.setup.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIProcEnhancePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProcessEnhancement.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SawmillRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new GrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Registration.SAWMILL_BLOCK.get()), new RecipeType<>(SawmillRecipeCategory.UID, SawmillRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(Registration.STONECUTTER_BLOCK.get()), RecipeTypes.STONECUTTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT1_BLOCK.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT2_BLOCK.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT3_BLOCK.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT4_BLOCK.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET1_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET2_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET3_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET4_BLOCK.get()), RecipeTypes.SMELTING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<SawmillRecipe> sawmillRecipes = recipeManager.getAllRecipesFor(SawmillRecipe.Type.INSTANCE);
        List<GrinderRecipe> grinderRecipes = recipeManager.getAllRecipesFor(GrinderRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(SawmillRecipeCategory.UID, SawmillRecipe.class), sawmillRecipes);
        registration.addRecipes(new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class), grinderRecipes);
    }
}
