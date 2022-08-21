package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.network.ChannelRegistration;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        ChannelRegistration.init();
    }
}
