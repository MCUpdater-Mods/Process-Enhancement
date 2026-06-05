package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.battery.BatteryBlockItem;
import com.mcupdater.procenhance.blocks.tank.TankBlockItem;
import com.mcupdater.procenhance.capabilities.DummyEnergyHandler;
import com.mcupdater.procenhance.capabilities.ItemEnergyStorage;
import com.mcupdater.procenhance.grid.GridEnergyHandler;
import com.mcupdater.procenhance.grid.GridManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

@EventBusSubscriber(modid= ProcessEnhancement.MODID)
public class ModEventHandlers {

	@SubscribeEvent
	private static void registerCapabilities(RegisterCapabilitiesEvent event) {
		{ // BlockEntity Handlers
			{ // Item
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.HARVESTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.AUTOPACKAGER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BATTERYT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BATTERYT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BATTERYT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BATTERYT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BIOGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BIOGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BIOGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BIOGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BUFFER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.DECONSTRUCTOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.DEHYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.DISENCHANTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.FURNACET1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.FURNACET2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.FURNACET3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.FURNACET4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GRINDERT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GRINDERT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GRINDERT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GRINDERT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.HYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.LAVAGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.LAVAGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.LAVAGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.LAVAGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.MINERT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.MINERT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.MINERT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.MINERT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.PLANTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.SAWMILL_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.SOILMANAGER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.BASALTSOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.COBBLESTONESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.ANDESITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.CALCITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.DEEPSLATESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.DIORITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.ENDSTONESOLIDIFIER_ENTITY.get(),(blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.GRANITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.STONESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.TUFFSOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Registration.STONECUTTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getItemHandler().getItemHandler(side) : blockEntity.getItemHandler().getInternalHandler());
			}
			{ // Energy
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.HARVESTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.AUTOPACKAGER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BUFFER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.COPPERWIRE_ENTITY.get(), GridManager::getEnergyHandler);
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.CONCEALEDWIRE_ENTITY.get(), GridManager::getEnergyHandler);
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.CONCEALEDWIRE_WALL_ENTITY.get(), GridManager::getEnergyHandler);
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BATTERYT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BATTERYT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BATTERYT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BATTERYT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.CRUDEGENERATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.LAVAGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.LAVAGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.LAVAGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.LAVAGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BIOGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BIOGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BIOGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BIOGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.DECONSTRUCTOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.DEHYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.DISENCHANTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.FURNACET1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.FURNACET2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.FURNACET3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.FURNACET4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GRINDERT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GRINDERT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GRINDERT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GRINDERT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.HYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.MINERT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.MINERT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.MINERT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.MINERT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.PLANTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.PUMPT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.PUMPT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.PUMPT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.PUMPT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SAWMILL_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SOILMANAGER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.COMPACTSOLARGENERATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SOLARGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SOLARGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SOLARGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.SOLARGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.STONECUTTER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.BASALTSOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.COBBLESTONESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.ANDESITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.CALCITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.DEEPSLATESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.DIORITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.ENDSTONESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.GRANITESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.STONESOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.TUFFSOLIDIFIER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.ELECTRIC_LANTERN_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.COPPER_ELECTRIC_LANTERN_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.NETHER_ELECTRIC_LANTERN_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.TERRACOTTA_LANTERN_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getEnergyStorage().getEnergyHandler(side) : blockEntity.getEnergyStorage().getInternalHandler());
			}
			{ // Fluid
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.BUFFER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.DEHYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.HYDRATOR_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.LAVAGENERATORT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.LAVAGENERATORT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.LAVAGENERATORT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.LAVAGENERATORT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.PUMPT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.PUMPT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.PUMPT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.PUMPT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.TANKT1_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.TANKT2_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.TANKT3_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.TANKT4_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, Registration.SOILMANAGER_ENTITY.get(), (blockEntity, side) -> side != null ? blockEntity.getFluidHandler().getFluidHandler(side) : blockEntity.getFluidHandler().getInternalHandler());
			}
		}
		{ // Item Handlers
			{ // Energy
				event.registerItem(Capabilities.EnergyStorage.ITEM, (stack,unused) -> {
					BatteryBlockItem battery = (BatteryBlockItem) stack.getItem();
					return new ItemEnergyStorage(stack, battery.getMaxStorage(),battery.getMaxTransfer());
				},
						Registration.BATTERYT1_ITEM.get(),
						Registration.BATTERYT2_ITEM.get(),
						Registration.BATTERYT3_ITEM.get(),
						Registration.BATTERYT4_ITEM.get()
				);
			}
			{ // Fluid
				event.registerItem(Capabilities.FluidHandler.ITEM, (stack,unused) -> {
							TankBlockItem tank = (TankBlockItem) stack.getItem();
							return new FluidHandlerItemStack(Registration.STORED_FLUID, stack, tank.getCapacity());
						},
						Registration.TANKT1_ITEM.get(),
						Registration.TANKT2_ITEM.get(),
						Registration.TANKT3_ITEM.get(),
						Registration.TANKT4_ITEM.get()
				);
			}
		}
	}
}
