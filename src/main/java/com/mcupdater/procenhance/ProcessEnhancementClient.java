package com.mcupdater.procenhance;

import com.mcupdater.procenhance.integration.PatchouliConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ProcessEnhancement.MODID, dist = Dist.CLIENT)
public class ProcessEnhancementClient {
	public ProcessEnhancementClient(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		if (ModList.get().isLoaded("patchouli")) PatchouliConfig.register();
	}

}
