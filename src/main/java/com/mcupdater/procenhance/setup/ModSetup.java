package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.loot.functions.RetainEnchantmentsFunction;
import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.network.ChannelRegistration;
import com.mcupdater.procenhance.recipe.ConfigCondition;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
