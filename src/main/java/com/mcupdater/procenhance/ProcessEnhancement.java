package com.mcupdater.procenhance;

import com.mcupdater.procenhance.setup.ClientSetup;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.ModSetup;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("processenhancement")
public class ProcessEnhancement {
    public static final String MODID = "processenhancement";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProcessEnhancement() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Registration.init(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
