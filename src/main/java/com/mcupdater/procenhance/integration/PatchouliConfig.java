package com.mcupdater.procenhance.integration;

import com.mcupdater.mculib.setup.Config;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliConfig {
    public static void register() {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        if (!api.isStub()) {
            //api.setConfigFlag("mculib:overdrive", Config.OVERDRIVE_ENABLED.get());
        }
    }
}
