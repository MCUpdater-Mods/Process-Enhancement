package com.mcupdater.procenhance.blocks.dehydrator;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.gui.WidgetFluid;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DehydratorScreen extends AbstractMachineScreen<DehydratorEntity, DehydratorMenu> {
	private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/machine_tank.png");
	private WidgetFluid fluidWidget;

	public DehydratorScreen(DehydratorMenu menu, Inventory inventory, Component name) {
		super(menu, inventory, name);
	}

	@Override
	public void registerWidgets() {
		super.registerWidgets();
		fluidWidget = this.addRenderableWidget(new WidgetFluid(this.leftPos + 5, this.topPos + 14, 18, 58, menu.getBlockEntity().getFluidHandler().getInternalHandler(), 0));
		this.addExtraWidget(fluidWidget);
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}
}
