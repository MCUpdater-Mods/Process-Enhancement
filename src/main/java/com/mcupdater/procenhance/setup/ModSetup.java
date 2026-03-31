package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.autoharvester.HarvesterScreen;
import com.mcupdater.procenhance.blocks.autopackager.PackagerScreen;
import com.mcupdater.procenhance.blocks.battery.BatteryScreen;
import com.mcupdater.procenhance.blocks.biogenerator.BiogeneratorScreen;
import com.mcupdater.procenhance.blocks.buffer.BufferScreen;
import com.mcupdater.procenhance.blocks.concrete_mixer.MixerScreen;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorScreen;
import com.mcupdater.procenhance.blocks.deconstructor.DeconstructorScreen;
import com.mcupdater.procenhance.blocks.dehydrator.DehydratorScreen;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterScreen;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceScreen;
import com.mcupdater.procenhance.blocks.generator.GeneratorScreen;
import com.mcupdater.procenhance.blocks.grinder.GrinderScreen;
import com.mcupdater.procenhance.blocks.hydrator.HydratorScreen;
import com.mcupdater.procenhance.blocks.lava_generator.LavaGeneratorScreen;
import com.mcupdater.procenhance.blocks.miner.MinerScreen;
import com.mcupdater.procenhance.blocks.planter.PlanterScreen;
import com.mcupdater.procenhance.blocks.pump.PumpScreen;
import com.mcupdater.procenhance.blocks.sawmill.SawmillScreen;
import com.mcupdater.procenhance.blocks.soilmanager.SoilManagerScreen;
import com.mcupdater.procenhance.blocks.solar_generator.SolarScreen;
import com.mcupdater.procenhance.blocks.solidifier.SolidifierScreen;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterScreen;
import com.mcupdater.procenhance.blocks.tank.TankScreen;
import net.minecraft.client.renderer.BiomeColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(value=Dist.CLIENT, modid=ProcessEnhancement.MODID, bus=EventBusSubscriber.Bus.MOD)
public class ModSetup {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == com.mcupdater.mculib.setup.MCULibRegistration.ITEM_GROUP.get()) {
            Registration.MACHINES.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.BATTERIES.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.TANKS.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.MINERS.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.BLOCKS.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.PATTERNS.getEntries().stream().forEach(entry -> event.accept(entry.get()));
            Registration.ITEMS.getEntries().stream().forEach(entry -> event.accept(entry.get()));
        }
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(Registration.CRUDEGENERATOR_MENU.get(), CrudeGeneratorScreen::new);
        event.register(Registration.GENERATOR_MENU.get(), GeneratorScreen::new);
        event.register(Registration.LAVAGENERATOR_MENU.get(), LavaGeneratorScreen::new);
        event.register(Registration.BIOGENERATOR_MENU.get(), BiogeneratorScreen::new);
        event.register(Registration.BATTERY_MENU.get(), BatteryScreen::new);
        event.register(Registration.FURNACE_MENU.get(), ElectricFurnaceScreen::new);
        event.register(Registration.SAWMILL_MENU.get(), SawmillScreen::new);
        event.register(Registration.GRINDER_MENU.get(), GrinderScreen::new);
        event.register(Registration.STONECUTTER_MENU.get(), ElectricStonecutterScreen::new);
        event.register(Registration.BUFFER_MENU.get(), BufferScreen::new);
        event.register(Registration.TANK_MENU.get(), TankScreen::new);
        event.register(Registration.PUMP_MENU.get(), PumpScreen::new);
        event.register(Registration.MINER_MENU.get(), MinerScreen::new);
        event.register(Registration.DISENCHANTER_MENU.get(), DisenchanterScreen::new);
        event.register(Registration.DECONSTRUCTOR_MENU.get(), DeconstructorScreen::new);
        event.register(Registration.SOLIDIFIER_MENU.get(), SolidifierScreen::new);
        event.register(Registration.AUTOPACKAGER_MENU.get(), PackagerScreen::new);
        event.register(Registration.HYDRATOR_MENU.get(), HydratorScreen::new);
        event.register(Registration.DEHYDRATOR_MENU.get(), DehydratorScreen::new);
        event.register(Registration.HARVESTER_MENU.get(), HarvesterScreen::new);
        event.register(Registration.PLANTER_MENU.get(), PlanterScreen::new);
        event.register(Registration.SOILMANAGER_MENU.get(), SoilManagerScreen::new);
        event.register(Registration.CONCRETEMIXER_MENU.get(), MixerScreen::new);
        event.register(Registration.SOLARGENERATOR_MENU.get(), SolarScreen::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Block event) {
        event.register((blockState, level, blockPos, index) -> {
            if (level != null) {
                if (blockPos != null) {
                    switch (index) {
                        case 1:
                            return BiomeColors.getAverageWaterColor(level, blockPos);
                    }
                }
            }
            return -1;
        }, Registration.COBBLESTONESOLIDIFIER_BLOCK.get(),
                Registration.ANDESITESOLIDIFIER_BLOCK.get(),
                Registration.CALCITESOLIDIFIER_BLOCK.get(),
                Registration.DEEPSLATESOLIDIFIER_BLOCK.get(),
                Registration.DIORITESOLIDIFIER_BLOCK.get(),
                Registration.GRANITESOLIDIFIER_BLOCK.get(),
                Registration.STONESOLIDIFIER_BLOCK.get(),
                Registration.TUFFSOLIDIFIER_BLOCK.get()
                );
    }
}
