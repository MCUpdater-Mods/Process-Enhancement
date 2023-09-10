package com.mcupdater.procenhance.integration;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
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

public class GrinderRecipeCategory implements IRecipeCategory<GrinderRecipe> {
    public static final RecipeType<GrinderRecipe> TYPE = RecipeType.create(ProcessEnhancement.MODID, "grinder", GrinderRecipe.class);
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProcessEnhancement.MODID, "textures/jei/grinder.png");

    private final IDrawable background;
    private final IDrawable icon;

    public GrinderRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0,0,170,84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.GRINDERT1_BLOCK.get()));
    }

    @Override
    public RecipeType<GrinderRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.processenhancement.grinder");
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
    public void setRecipe(IRecipeLayoutBuilder builder, GrinderRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3,3).addIngredients(recipe.getIngredients().get(0));

        for (int i = 0; i < recipe.getOutputs().size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 32 + (i % 7 * 18), 3 + i / 7 * 18).addItemStack(recipe.getOutputs().get(i).getA()).addTooltipCallback(new GrinderTooltipCallback(recipe,recipe.getOutputs().get(i)));
        }
    }
}
