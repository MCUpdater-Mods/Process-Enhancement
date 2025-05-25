package com.mcupdater.procenhance;

import com.mcupdater.procenhance.integration.PatchouliConfig;
import com.mcupdater.procenhance.network.ChannelRegistration;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.ModSetup;
import com.mcupdater.procenhance.setup.PERegistries;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(ProcessEnhancement.MODID)
public class ProcessEnhancement {
    public static final String MODID = "processenhancement";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProcessEnhancement(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        modEventBus.addListener(PERegistries::onRegisterRecipes);
        Registration.init(modEventBus);

        modEventBus.addListener(ChannelRegistration::register);
    }

}
