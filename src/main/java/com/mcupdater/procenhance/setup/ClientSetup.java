package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceScreen;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(Registration.BASICGENERATOR_BLOCK.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(Registration.FURNACE_BLOCK.get(), RenderType.cutoutMipped());

        MenuScreens.register(Registration.BASICGENERATOR_MENU.get(), BasicGeneratorScreen::new);
        MenuScreens.register(Registration.FURNACE_MENU.get(), ElectricFurnaceScreen::new);
    }
}