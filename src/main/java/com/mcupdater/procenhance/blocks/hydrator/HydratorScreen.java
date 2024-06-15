package com.mcupdater.procenhance.blocks.hydrator;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.gui.WidgetFluid;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HydratorScreen extends AbstractMachineScreen<HydratorEntity,HydratorMenu> {
	private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/machine_tank.png");
	private WidgetFluid fluidWidget;

	public HydratorScreen(HydratorMenu menu, Inventory inventory, Component name) {
		super(menu, inventory, name);
	}

	@Override
	public void registerWidgets() {
		fluidWidget = this.addRenderableWidget(new WidgetFluid(this.leftPos + 5, this.topPos + 14, 18, 58, menu.getBlockEntity().getFluidHandler(), 0));
		this.addExtraWidget(fluidWidget);
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}
}
