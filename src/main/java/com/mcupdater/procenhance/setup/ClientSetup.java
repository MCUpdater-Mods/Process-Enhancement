package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.blocks.battery.BatteryScreen;
import com.mcupdater.procenhance.blocks.biogenerator.BiogeneratorScreen;
import com.mcupdater.procenhance.blocks.buffer.BufferScreen;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorScreen;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterScreen;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceScreen;
import com.mcupdater.procenhance.blocks.generator.GeneratorScreen;
import com.mcupdater.procenhance.blocks.grinder.GrinderScreen;
import com.mcupdater.procenhance.blocks.lava_generator.LavaGeneratorScreen;
import com.mcupdater.procenhance.blocks.miner.MinerScreen;
import com.mcupdater.procenhance.blocks.pump.PumpScreen;
import com.mcupdater.procenhance.blocks.sawmill.SawmillScreen;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterScreen;
import com.mcupdater.procenhance.blocks.tank.TankScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        for (RegistryObject<Block> machine : Registration.MACHINES.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(machine.get(), RenderType.cutoutMipped());
        }
        for (RegistryObject<Block> machine : Registration.BATTERIES.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(machine.get(), RenderType.cutoutMipped());
        }
        ItemBlockRenderTypes.setRenderLayer(Registration.COPPERWIRE_BLOCK.get(), RenderType.cutout());

        MenuScreens.register(Registration.CRUDEGENERATOR_MENU.get(), CrudeGeneratorScreen::new);
        MenuScreens.register(Registration.GENERATOR_MENU.get(), GeneratorScreen::new);
        MenuScreens.register(Registration.LAVAGENERATOR_MENU.get(), LavaGeneratorScreen::new);
        MenuScreens.register(Registration.BIOGENERATOR_MENU.get(), BiogeneratorScreen::new);
        MenuScreens.register(Registration.BATTERY_MENU.get(), BatteryScreen::new);
        MenuScreens.register(Registration.FURNACE_MENU.get(), ElectricFurnaceScreen::new);
        MenuScreens.register(Registration.SAWMILL_MENU.get(), SawmillScreen::new);
        MenuScreens.register(Registration.GRINDER_MENU.get(), GrinderScreen::new);
        MenuScreens.register(Registration.STONECUTTER_MENU.get(), ElectricStonecutterScreen::new);
        MenuScreens.register(Registration.BUFFER_MENU.get(), BufferScreen::new);
        MenuScreens.register(Registration.TANK_MENU.get(), TankScreen::new);
        MenuScreens.register(Registration.PUMP_MENU.get(), PumpScreen::new);
        MenuScreens.register(Registration.MINER_MENU.get(), MinerScreen::new);
        MenuScreens.register(Registration.DISENCHANTER_MENU.get(), DisenchanterScreen::new);
    }
}
