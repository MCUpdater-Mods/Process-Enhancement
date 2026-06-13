package com.mcupdater.procenhance.setup;

import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.modonomicon.ModBookProvider;
import com.mcupdater.procenhance.grid.GridManager;
import com.mcupdater.procenhance.items.autopackager.AbstractPatternItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid= ProcessEnhancement.MODID)
public class GameEventHandlers {

    @SubscribeEvent
    public static void datapackReload(final OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) { // Player is null when reload command is issued, otherwise it is a player joining.
            for (DeferredHolder<Item, ? extends Item> entry : Registration.PATTERNS.getEntries()) {
                if (entry.get() instanceof AbstractPatternItem patternItem)
                    patternItem.clearCache();
            }
        }
    }

    @SubscribeEvent
    private static void serverStarted(ServerStartedEvent event) {
        ProcessEnhancement.LOGGER.info("Server started");
        event.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(GridManager::new, GridManager::load),"pe_grid");
        GridManager.setLoaded();
        ProcessEnhancement.serverInstance = event.getServer();
        LoaderRegistry.registerDynamicTextMacroLoader(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, ModBookProvider.ID), () -> {
            Map<String, String> macros = new HashMap<>();
            macros.put( "value.crude_generator", Config.CRUDE_GENERATOR_PER_TICK.get().toString());
            macros.put( "value.generator_t1", Integer.toString(Config.BASIC_GENERATOR_PER_TICK.get() * 1));
            macros.put( "value.generator_t2", Integer.toString(Config.BASIC_GENERATOR_PER_TICK.get() * 2));
            macros.put( "value.generator_t3", Integer.toString(Config.BASIC_GENERATOR_PER_TICK.get() * 4));
            macros.put( "value.generator_t4", Integer.toString(Config.BASIC_GENERATOR_PER_TICK.get() * 8));
            macros.put( "value.lava_generator_t1", Integer.toString(Config.BASIC_LAVA_GENERATOR_PER_TICK.get() * 1));
            macros.put( "value.lava_generator_t2", Integer.toString(Config.BASIC_LAVA_GENERATOR_PER_TICK.get() * 2));
            macros.put( "value.lava_generator_t3", Integer.toString(Config.BASIC_LAVA_GENERATOR_PER_TICK.get() * 4));
            macros.put( "value.lava_generator_t4", Integer.toString(Config.BASIC_LAVA_GENERATOR_PER_TICK.get() * 8));
            macros.put( "value.biogenerator_t1", Integer.toString(Config.BASIC_BIOGENERATOR_PER_TICK.get() * 1));
            macros.put( "value.biogenerator_t2", Integer.toString(Config.BASIC_BIOGENERATOR_PER_TICK.get() * 2));
            macros.put( "value.biogenerator_t3", Integer.toString(Config.BASIC_BIOGENERATOR_PER_TICK.get() * 4));
            macros.put( "value.biogenerator_t4", Integer.toString(Config.BASIC_BIOGENERATOR_PER_TICK.get() * 8));
            macros.put( "value.solar_generator_t1", Integer.toString(Config.BASIC_SOLAR_GENERATOR_PER_TICK.get()));
            macros.put( "value.solar_generator_t2", Integer.toString(Config.BASIC_SOLAR_GENERATOR_PER_TICK.get() * 2));
            macros.put( "value.solar_generator_t3", Integer.toString(Config.BASIC_SOLAR_GENERATOR_PER_TICK.get() * 4));
            macros.put( "value.solar_generator_t4", Integer.toString(Config.BASIC_SOLAR_GENERATOR_PER_TICK.get() * 8));
            macros.put( "value.solidifiers", Integer.toString(Config.SOLIDIFIER_ENERGY_PER_TICK.get()));
            macros.put( "value.grinder_t1", Integer.toString(Config.GRINDER_ENERGY_PER_TICK.get() * 1));
            macros.put( "value.grinder_t2", Integer.toString(Config.GRINDER_ENERGY_PER_TICK.get() * 2));
            macros.put( "value.grinder_t3", Integer.toString(Config.GRINDER_ENERGY_PER_TICK.get() * 4));
            macros.put( "value.grinder_t4", Integer.toString(Config.GRINDER_ENERGY_PER_TICK.get() * 8));
            macros.put( "value.furnace_t1", Integer.toString(Config.FURNACE_ENERGY_PER_TICK.get() * 1));
            macros.put( "value.furnace_t2", Integer.toString(Config.FURNACE_ENERGY_PER_TICK.get() * 2));
            macros.put( "value.furnace_t3", Integer.toString(Config.FURNACE_ENERGY_PER_TICK.get() * 4));
            macros.put( "value.furnace_t4", Integer.toString(Config.FURNACE_ENERGY_PER_TICK.get() * 8));
            macros.put( "value.miner_t1", Integer.toString(Config.MINER_ENERGY_PER_TICK.get() * 1));
            macros.put( "value.miner_t2", Integer.toString(Config.MINER_ENERGY_PER_TICK.get() * 2));
            macros.put( "value.miner_t3", Integer.toString(Config.MINER_ENERGY_PER_TICK.get() * 4));
            macros.put( "value.miner_t4", Integer.toString(Config.MINER_ENERGY_PER_TICK.get() * 8));
            macros.put( "value.pump_t1", Integer.toString(Config.PUMP_ENERGY_PER_TICK.get() * 1));
            macros.put( "value.pump_t2", Integer.toString(Config.PUMP_ENERGY_PER_TICK.get() * 2));
            macros.put( "value.pump_t3", Integer.toString(Config.PUMP_ENERGY_PER_TICK.get() * 4));
            macros.put( "value.pump_t4", Integer.toString(Config.PUMP_ENERGY_PER_TICK.get() * 8));
            macros.put( "value.sawmill", Integer.toString(Config.SAWMILL_ENERGY_PER_TICK.get()));
            macros.put( "value.stonecutter", Integer.toString(Config.STONECUTTER_ENERGY_PER_TICK.get()));
            macros.put( "value.autopackager", Integer.toString(Config.AUTOPACKAGER_ENERGY_PER_TICK.get()));
            macros.put( "value.concrete_mixer", Integer.toString(Config.CONCRETEMIXER_ENERGY_PER_TICK.get()));
            macros.put( "value.hydrator", Integer.toString(Config.HYDRATOR_ENERGY_PER_TICK.get()));
            macros.put( "value.dehydrator", Integer.toString(Config.DEHYDRATOR_ENERGY_PER_TICK.get()));
            macros.put( "value.deconstructor", Integer.toString(Config.DECONSTRUCTOR_ENERGY_PER_TICK.get()));
            macros.put( "value.autoharvester", Integer.toString(Config.AUTOHARVESTER_ENERGY_PER_TICK.get()));
            macros.put( "value.planter", Integer.toString(Config.PLANTER_ENERGY_PER_TICK.get()));
            macros.put( "value.soil_manager", Integer.toString(Config.SOILMANAGER_ENERGY_PER_TICK.get()));
            return macros;
        });
    }

}
