package com.mcupdater.procenhance.integration.jade;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.concealed_wire.ConcealedWireBlock;
import com.mcupdater.procenhance.blocks.concealed_wire.ConcealedWireEntity;
import com.mcupdater.procenhance.blocks.concealed_wire.ConcealedWireWallBlock;
import com.mcupdater.procenhance.blocks.concealed_wire.ConcealedWireWallEntity;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireBlock;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class PEPlugin implements IWailaPlugin {
	public static ResourceLocation GRID = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grid");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(GridComponentProvider.INSTANCE, CopperWireEntity.class);
		registration.registerBlockDataProvider(GridComponentProvider.INSTANCE, ConcealedWireEntity.class);
		registration.registerBlockDataProvider(GridComponentProvider.INSTANCE, ConcealedWireWallEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(GridComponentProvider.INSTANCE, CopperWireBlock.class);
		registration.registerBlockComponent(GridComponentProvider.INSTANCE, ConcealedWireBlock.class);
		registration.registerBlockComponent(GridComponentProvider.INSTANCE, ConcealedWireWallBlock.class);
	}
}
