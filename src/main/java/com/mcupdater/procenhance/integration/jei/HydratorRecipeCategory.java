package com.mcupdater.procenhance.integration.jei;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.HydratorRecipe;
import com.mcupdater.procenhance.setup.Registration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HydratorRecipeCategory implements IRecipeCategory<HydratorRecipe> {
    public static final RecipeType<HydratorRecipe> TYPE = RecipeType.create(ProcessEnhancement.MODID, "hydrator", HydratorRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/jei/hydrator.png");

    private final IDrawable icon;

    public HydratorRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.HYDRATOR_BLOCK.get()));
    }

    @Override
    public RecipeType<HydratorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.processenhancement.hydrator");
    }

    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public void draw(HydratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(TEXTURE, 0,0,0,0,getWidth(), getHeight());
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HydratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 17,5).addIngredients(recipe.getItemIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 3).setFluidRenderer(1000, true,5,19).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.getFluidIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 5).addItemStack(JEIProcEnhancePlugin.lookupOutput(recipe));
    }

}
