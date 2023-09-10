package com.mcupdater.procenhance.blocks.pump;

import com.mcupdater.mculib.gui.ConfigPanel;
import com.mcupdater.mculib.gui.TabConfig;
import com.mcupdater.mculib.gui.WidgetFluid;
import com.mcupdater.mculib.gui.WidgetPower;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PumpScreen extends AbstractContainerScreen<PumpMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/blank.png");
    private ConfigPanel configPanel;
    private TabConfig configTab;
    private WidgetFluid fluidWidget;
    private WidgetPower powerWidget;

    public PumpScreen(PumpMenu menu, Inventory inventory, Component name) {
        super(menu,inventory,name);
    }

    @Override
    protected void init() {
        super.init();
        powerWidget = this.addRenderableWidget(new WidgetPower(this.leftPos + 153, this.topPos + 5, 18, 71, menu.getEnergyHandler(), WidgetPower.Orientation.VERTICAL));
        fluidWidget = this.addRenderableWidget(new WidgetFluid(this.leftPos + 5, this.topPos + 14, this.imageWidth - 38, 58, menu.getBlockEntity().getFluidHandler(), 0));
        this.configPanel = new ConfigPanel(this.menu, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
        this.configPanel.setVisible(false);
        this.configTab = this.addRenderableWidget(new TabConfig(this.leftPos - 22, this.topPos + 2, 22, 22, (mouseX, mouseY) -> {
            this.configPanel.setVisible(!this.configPanel.isVisible());
            this.powerWidget.visible = !this.powerWidget.visible;
            this.fluidWidget.visible = !this.fluidWidget.visible;
        }));
        this.configTab.setChild(this.configPanel);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        if (!this.configPanel.isVisible()) {
            super.render(poseStack, mouseX, mouseY, partialTicks);
        } else {
            renderNoSlots(poseStack, mouseX, mouseY, partialTicks);
        }
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    public void renderNoSlots(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int i = this.leftPos;
        int j = this.topPos;
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ContainerScreenEvent.Render.Background(this, pPoseStack, pMouseX, pMouseY));
        RenderSystem.disableDepthTest();
        for(Widget widget : this.renderables) {
            widget.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(i, j, 0.0D);
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderLabels(pPoseStack, pMouseX, pMouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ContainerScreenEvent.Render.Foreground(this, pPoseStack, pMouseX, pMouseY));
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableDepthTest();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (!this.configPanel.isVisible()) {
            super.renderLabels(pPoseStack, pMouseX, pMouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = this.leftPos;
        int relY = this.topPos;
        this.blit(poseStack,relX,relY,0,0,this.imageWidth,this.imageHeight);
    }
}
