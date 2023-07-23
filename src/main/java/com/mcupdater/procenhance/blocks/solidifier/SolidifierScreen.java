package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SolidifierScreen extends AbstractMachineScreen<AbstractSolidifierEntity, SolidifierMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/solidifier.png");

    public SolidifierScreen(SolidifierMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.getGUIResourceLocation());
        int relX = this.leftPos;
        int relY = this.topPos;
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }
}
