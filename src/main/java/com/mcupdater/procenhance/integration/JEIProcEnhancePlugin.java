package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.*;
import com.mcupdater.procenhance.setup.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIProcEnhancePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SawmillRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new GrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HydratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DehydratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IModPlugin.super.registerVanillaCategoryExtensions(registration);
        registration.getCraftingCategory().addExtension(BatteryUpgradeRecipe.class, new BatteryUpgradeCraftingExtension());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Registration.SAWMILL_BLOCK.get()), SawmillRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.STONECUTTER_BLOCK.get()), RecipeTypes.STONECUTTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT1_BLOCK.get()), GrinderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT2_BLOCK.get()), GrinderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT3_BLOCK.get()), GrinderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.GRINDERT4_BLOCK.get()), GrinderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET1_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET2_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET3_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACET4_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.HYDRATOR_BLOCK.get()), HydratorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Registration.DEHYDRATOR_BLOCK.get()), DehydratorRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<SawmillRecipe> sawmillRecipes = recipeManager.getAllRecipesFor(Registration.SAWMILL_RECIPE.get())
                .stream().collect(ArrayList::new, (c, e) -> c.add(e.value()), ArrayList::addAll);
        List<GrinderRecipe> grinderRecipes = recipeManager.getAllRecipesFor(Registration.GRINDER_RECIPE.get())
                .stream().collect(ArrayList::new, (c,e) -> c.add(e.value()), ArrayList::addAll);
        List<HydratorRecipe> hydratorRecipes = recipeManager.getAllRecipesFor(Registration.HYDRATOR_RECIPE.get())
                .stream().collect(ArrayList::new, (c, e) -> c.add(e.value()), ArrayList::addAll);
        List<DehydratorRecipe> dehydratorRecipes = recipeManager.getAllRecipesFor(Registration.DEHYDRATOR_RECIPE.get())
                .stream().collect(ArrayList::new, (c, e) -> c.add(e.value()), ArrayList::addAll);
        registration.addRecipes(SawmillRecipeCategory.TYPE, sawmillRecipes);
        registration.addRecipes(GrinderRecipeCategory.TYPE, grinderRecipes);
        registration.addRecipes(HydratorRecipeCategory.TYPE, hydratorRecipes);
        registration.addRecipes(DehydratorRecipeCategory.TYPE, dehydratorRecipes);
    }

    public static ItemStack lookupOutput(Recipe<?> recipe) {
        return recipe.getResultItem(getLookupProvider());
    }

    public static RegistryAccess getLookupProvider() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("Level must not be null.");
        }
        return level.registryAccess();
    }
}
