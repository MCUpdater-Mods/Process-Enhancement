package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.DehydratorRecipe;
import com.mcupdater.procenhance.setup.Registration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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

public class DehydratorRecipeCategory implements IRecipeCategory<DehydratorRecipe> {
    public static final RecipeType<DehydratorRecipe> TYPE = RecipeType.create(ProcessEnhancement.MODID, "dehydrator", DehydratorRecipe.class);
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProcessEnhancement.MODID, "textures/jei/dehydrator.png");

    private final IDrawable background;
    private final IDrawable icon;

    public DehydratorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0,0,80,26);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.HYDRATOR_BLOCK.get()));
    }

    @Override
    public RecipeType<DehydratorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.processenhancement.dehydrator");
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
    public void setRecipe(IRecipeLayoutBuilder builder, DehydratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10,5).addIngredients(recipe.getItemIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 46, 5).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 3).setFluidRenderer(1000, true,5,19).addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidOutput());
    }

}
