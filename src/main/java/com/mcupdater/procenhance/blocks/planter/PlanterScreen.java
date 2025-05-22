package com.mcupdater.procenhance.blocks.planter;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.gui.ConfigPanel;
import com.mcupdater.mculib.gui.TabConfig;
import com.mcupdater.mculib.gui.WidgetPower;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlanterScreen extends AbstractMachineScreen<PlanterEntity, PlanterMenu> {
	private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/planter.png");

	public PlanterScreen(PlanterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@Override
	public void registerWidgets() {
		addExtraWidget(this.addRenderableWidget(new WidgetPower(this.leftPos + 153, this.topPos + 5, 18, 25, menu.getEnergyHandler(), WidgetPower.Orientation.VERTICAL)));
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}
}
