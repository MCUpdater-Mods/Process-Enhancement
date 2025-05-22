package com.mcupdater.procenhance.blocks.concrete_mixer;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MixerScreen extends AbstractMachineScreen<MixerEntity,MixerMenu> {
	private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/concrete_mixer.png");

	public MixerScreen(MixerMenu menu, Inventory inventory, Component name) {
		super(menu, inventory, name);
	}

	@Override
	protected ResourceLocation getGUIResourceLocation() {
		return GUI;
	}
}
