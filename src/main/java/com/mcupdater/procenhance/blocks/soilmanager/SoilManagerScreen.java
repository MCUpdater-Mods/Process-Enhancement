package com.mcupdater.procenhance.blocks.soilmanager;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.gui.WidgetFluid;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoilManagerScreen extends AbstractMachineScreen<SoilManagerEntity, SoilManagerMenu> {
	private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/soil_manager.png");
	private static final ResourceLocation FILL = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "soilmanager/bonemeal");
	private WidgetFluid fluidWidget;

	public SoilManagerScreen(SoilManagerMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	public void registerWidgets() {
		super.registerWidgets();
		fluidWidget = this.addRenderableWidget(new WidgetFluid(this.leftPos + 5, this.topPos + 16, 18,52, menu.getBlockEntity().getFluidHandler().getInternalHandler(), 0));
		this.addExtraWidget(fluidWidget);
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int relX = this.leftPos;
		int relY = this.topPos;
		guiGraphics.blit(this.getGUIResourceLocation(), relX, relY, 0, 0, this.imageWidth, this.imageHeight);
		int fertilizer = this.menu.getFertilizer();
		guiGraphics.blitSprite(FILL, 128, 128, 0, 33 - fertilizer, relX + 79, relY + 17 + 33 - fertilizer, 20, fertilizer);
	}
}
