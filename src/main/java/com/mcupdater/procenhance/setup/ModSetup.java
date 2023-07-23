package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.loot.functions.RetainEnchantmentsFunction;
import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.network.ChannelRegistration;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        ChannelRegistration.init();
        RetainEnergyFunction.load();
        RetainFluidFunction.load();
        RetainEnchantmentsFunction.load();
    }
}
