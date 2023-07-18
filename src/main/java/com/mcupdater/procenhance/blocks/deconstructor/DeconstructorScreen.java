package com.mcupdater.procenhance.blocks.deconstructor;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterEntity;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DeconstructorScreen extends AbstractMachineScreen<DeconstructorEntity, DeconstructorMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/deconstructor.png");

    public DeconstructorScreen(DeconstructorMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.getGUIResourceLocation());
        int relX = this.leftPos;
        int relY = this.topPos;
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if (((AbstractMachineMenu)this.menu).isWorking()) {
            int progress = ((AbstractMachineMenu)this.menu).getWorkProgress();
            this.blit(poseStack, relX + 61, relY + 36, 176, 0, progress, 18);
        }
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }
}
