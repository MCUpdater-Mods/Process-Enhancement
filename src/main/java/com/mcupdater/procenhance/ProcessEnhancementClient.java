package com.mcupdater.procenhance;

import com.mcupdater.procenhance.integration.PatchouliConfig;
import com.mcupdater.procenhance.render.ConcealedWireRenderer;
import com.mcupdater.procenhance.render.ConcealedWireWallRenderer;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = ProcessEnhancement.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ProcessEnhancement.MODID, value=Dist.CLIENT)
public class ProcessEnhancementClient {
	public ProcessEnhancementClient(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
//		if (ModList.get().isLoaded("patchouli")) PatchouliConfig.register();
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(Registration.CONCEALEDWIRE_ENTITY.get(), ConcealedWireRenderer::new);
		event.registerBlockEntityRenderer(Registration.CONCEALEDWIRE_WALL_ENTITY.get(), ConcealedWireWallRenderer::new);
		ProcessEnhancement.LOGGER.info("BERs registered");
	}

	@SubscribeEvent
	public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID,"item/book")));
	}
}
