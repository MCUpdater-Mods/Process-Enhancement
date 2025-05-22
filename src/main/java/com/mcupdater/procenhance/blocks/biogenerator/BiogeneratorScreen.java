package com.mcupdater.procenhance.blocks.biogenerator;

import com.mcupdater.mculib.gui.ConfigPanel;
import com.mcupdater.mculib.gui.TabConfig;
import com.mcupdater.mculib.gui.WidgetPower;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

public class BiogeneratorScreen extends AbstractContainerScreen<BiogeneratorMenu> {

    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/biogenerator.png");
    private ConfigPanel configPanel;
    private TabConfig configTab;

    public BiogeneratorScreen(BiogeneratorMenu menu, Inventory inventory, Component name) {
        super(menu,inventory,name);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new WidgetPower(this.leftPos + 153, this.topPos + 5, 18,71, menu.getEnergyHandler(), WidgetPower.Orientation.VERTICAL));
        this.configPanel = new ConfigPanel(this.menu, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
        this.configPanel.setVisible(false);
        this.configTab = this.addRenderableWidget(new TabConfig(this.leftPos - 22, this.topPos + 2,22,22, (mouseX, mouseY) -> {
            this.configPanel.setVisible(!this.configPanel.isVisible());
        }));
        this.configTab.setChild(this.configPanel);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        if (!this.configPanel.isVisible()) {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } else {
            renderNoSlots(guiGraphics, mouseX, mouseY, partialTicks);
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public void renderNoSlots(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int i = this.leftPos;
        int j = this.topPos;
        this.renderBg(guiGraphics, pPartialTick, pMouseX, pMouseY);
        NeoForge.EVENT_BUS.post(new ContainerScreenEvent.Render.Background(this, guiGraphics, pMouseX, pMouseY));
        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        }
        RenderSystem.disableDepthTest();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(i, j, 0.0D);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderLabels(guiGraphics, pMouseX, pMouseY);
        NeoForge.EVENT_BUS.post(new ContainerScreenEvent.Render.Foreground(this, guiGraphics, pMouseX, pMouseY));
        guiGraphics.pose().popPose();
        RenderSystem.enableDepthTest();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        if (!this.configPanel.isVisible()) {
            super.renderLabels(guiGraphics, pMouseX, pMouseY);
        }
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int relX = this.leftPos;
        int relY = this.topPos;
        guiGraphics.blit(GUI,relX,relY,0,0,this.imageWidth,this.imageHeight);
        if (this.menu.isBioFueled()) {
            int progress = this.menu.getBioFill();
            guiGraphics.blit(GUI, relX + 33, relY + 21, 2, 168, progress, 8);
        }
        if (this.menu.isCopperFueled()) {
            int progress = this.menu.getCopperFill();
            guiGraphics.blit(GUI, relX + 33, relY + 39, 2, 168, progress, 8);
        }
        if (this.menu.isGunpowderFueled()) {
            int progress = this.menu.getGunpowderFill();
            guiGraphics.blit(GUI, relX + 33, relY + 57, 2, 168, progress, 8);
        }
    }
}
