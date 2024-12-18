package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.setup.Registration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.mcupdater.procenhance.integration.JEIProcEnhancePlugin.getLookupProvider;

public class SawmillRecipeCategory implements IRecipeCategory<SawmillRecipe> {
    public static final RecipeType<SawmillRecipe> TYPE = RecipeType.create(ProcessEnhancement.MODID, "sawmill", SawmillRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/jei/machine.png");

    private final IDrawable background;
    private final IDrawable icon;

    public SawmillRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0,0,80,26);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.SAWMILL_BLOCK.get()));
    }

    @Override
    public RecipeType<SawmillRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.processenhancement.sawmill");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SawmillRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 17,5).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 5).addItemStack(recipe.getResultItem(getLookupProvider()));
    }

}
