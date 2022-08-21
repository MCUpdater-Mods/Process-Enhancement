package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class SawmillScreen extends AbstractMachineScreen<SawmillEntity,SawmillMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/cutter.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int SCROLLER_X_OFFSET = 103;
    private static final int SCROLLER_Y_OFFSET = 9;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_ROWS = 3;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int RECIPES_X = 36;
    private static final int RECIPES_Y = 14;
    private boolean displayRecipes;
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;

    public SawmillScreen(SawmillMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
        menu.registerUpdateListener(this::containerChanged);
        --this.titleLabelY;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.getGUIResourceLocation());
        int relX = this.leftPos;
        int relY = this.topPos;
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        int scrollPos = (int)(41.0F * this.scrollOffs);
        this.blit(poseStack, relX + 103, relY + 15 + scrollPos, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int recipeX = relX + RECIPES_X;
        int recipeY = relY + RECIPES_Y;
        int lastVisibleIndex = this.startIndex + 12;
        this.renderButtons(poseStack, mouseX, mouseY, recipeX, recipeY, lastVisibleIndex);
        this.renderRecipes(recipeX, recipeY, lastVisibleIndex);
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    private void renderButtons(PoseStack pPoseStack, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY, int pLastVisibleIndex) {
        for (int i = this.startIndex; i < pLastVisibleIndex && i < this.menu.getNumRecipes(); ++i) {
            int position = i - this.startIndex;
            int x = pOffsetX + position % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
            int row = position / RECIPES_COLUMNS;
            int y = pOffsetY + row * RECIPES_IMAGE_SIZE_HEIGHT + 2;
            int imageHeightOffset = this.imageHeight;
            if (i == this.menu.getSelectedRecipeIndex()) {
                imageHeightOffset += RECIPES_IMAGE_SIZE_HEIGHT;
            } else if (pMouseX >= x && pMouseY >= y && pMouseX < x + RECIPES_IMAGE_SIZE_WIDTH && pMouseY < y + RECIPES_IMAGE_SIZE_HEIGHT) {
                imageHeightOffset += (RECIPES_IMAGE_SIZE_HEIGHT * 2);
            }

            this.blit(pPoseStack, x, y - 1, 0, imageHeightOffset, RECIPES_IMAGE_SIZE_WIDTH, RECIPES_IMAGE_SIZE_HEIGHT);
        }
    }

    private void renderRecipes(int pLeft, int pTop, int pLastVisibleIndex) {
        List<SawmillRecipe> recipes = this.menu.getRecipes();

        for (int i = this.startIndex; i < pLastVisibleIndex && i < this.menu.getNumRecipes(); ++i) {
            int position = i - this.startIndex;
            int x = pLeft + position % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
            int row = position / RECIPES_COLUMNS;
            int y = pTop + row * RECIPES_IMAGE_SIZE_HEIGHT + 2;
            this.minecraft.getItemRenderer().renderAndDecorateItem(recipes.get(i).getResultItem(), x, y);
        }
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (this.displayRecipes) {
            int relX = this.leftPos + RECIPES_X;
            int relY = this.topPos + RECIPES_Y;
            int lastIndex = this.startIndex + 12;
            List<SawmillRecipe> recipes = this.menu.getRecipes();

            for(int index = this.startIndex; index < lastIndex && index < this.menu.getNumRecipes(); ++index) {
                int position = index - this.startIndex;
                int x = relX + position % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
                int y = relY + position / RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_HEIGHT + 2;
                if (pX >= x && pX < x + RECIPES_IMAGE_SIZE_WIDTH && pY >= y && pY < y + RECIPES_IMAGE_SIZE_HEIGHT) {
                    this.renderTooltip(pPoseStack, recipes.get(index).getResultItem(), pX, pY);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int relX = this.leftPos + RECIPES_X;
            int relY = this.topPos + RECIPES_Y;
            int lastIndex = this.startIndex + 12;

            for (int index = this.startIndex; index < lastIndex; ++index) {
                int position = index - this.startIndex;
                double x = pMouseX - (double)(relX + position % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH);
                double y = pMouseY - (double)(relY + position / RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_HEIGHT);
                if (x >= 0.0D && y >= 0.0D && x < (double)RECIPES_IMAGE_SIZE_WIDTH && y < (double)RECIPES_IMAGE_SIZE_HEIGHT && this.menu.clickMenuButton(this.minecraft.player, position)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, position);
                    return true;
                }
            }

            relX = this.leftPos + SCROLLER_X_OFFSET;
            relY = this.topPos + SCROLLER_Y_OFFSET;
            if (pMouseX >= (double)relX && pMouseX < (double)(relX + SCROLLER_WIDTH) && pMouseY >= (double)relY && pMouseY < (double)(relY + SCROLLER_HEIGHT)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int top = this.topPos + 14;
            int bottom = top + SCROLLER_FULL_HEIGHT;
            this.scrollOffs = ((float)pMouseY - (float)top - 7.5F) / ((float)(bottom - top) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * RECIPES_COLUMNS;
            return true;
        } else {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            float f = (float)pDelta / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }
}

