package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.items.autopackager.AbstractPatternItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusHandlers {
    @SubscribeEvent
    public static void datapackReload(final OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) { // Player is null when reload command is issued, otherwise it is a player joining.
            for (RegistryObject<Item> entry : Registration.PATTERNS.getEntries()) {
                if (entry.get() instanceof AbstractPatternItem patternItem)
                    patternItem.clearCache();
            }
        }
    }
}
