package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.blocks.basic_battery.BasicBatteryScreen;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorScreen;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceScreen;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorScreen;
import com.mcupdater.procenhance.blocks.grinder.GrinderScreen;
import com.mcupdater.procenhance.blocks.sawmill.SawmillScreen;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterScreen;
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
        ItemBlockRenderTypes.setRenderLayer(Registration.COPPERWIRE_BLOCK.get(), RenderType.cutout());

        MenuScreens.register(Registration.CRUDEGENERATOR_MENU.get(), CrudeGeneratorScreen::new);
        MenuScreens.register(Registration.BASICGENERATOR_MENU.get(), BasicGeneratorScreen::new);
        MenuScreens.register(Registration.BASICBATTERY_MENU.get(), BasicBatteryScreen::new);
        MenuScreens.register(Registration.FURNACE_MENU.get(), ElectricFurnaceScreen::new);
        MenuScreens.register(Registration.SAWMILL_MENU.get(), SawmillScreen::new);
        MenuScreens.register(Registration.GRINDER_MENU.get(), GrinderScreen::new);
        MenuScreens.register(Registration.STONECUTTER_MENU.get(), ElectricStonecutterScreen::new);
    }
}
