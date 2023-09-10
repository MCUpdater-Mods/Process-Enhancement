package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.loot.functions.RetainEnchantmentsFunction;
import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.network.ChannelRegistration;
import com.mcupdater.procenhance.recipe.ConfigCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
        });
        ChannelRegistration.init();
        RetainEnergyFunction.load();
        RetainFluidFunction.load();
        RetainEnchantmentsFunction.load();
    }
}
