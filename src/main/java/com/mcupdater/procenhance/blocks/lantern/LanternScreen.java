package com.mcupdater.procenhance.blocks.lantern;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LanternScreen extends AbstractMachineScreen<LanternEntity, LanternMenu> {
	private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/blank.png");

	public LanternScreen(LanternMenu menu, Inventory inventory, Component name) {
		super(menu, inventory, name);
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}
}
