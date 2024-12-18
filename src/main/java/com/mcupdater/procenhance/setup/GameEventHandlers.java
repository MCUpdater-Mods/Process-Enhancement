package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.items.autopackager.AbstractPatternItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid= ProcessEnhancement.MODID, bus=EventBusSubscriber.Bus.GAME)
public class GameEventHandlers {

    @SubscribeEvent
    public static void datapackReload(final OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) { // Player is null when reload command is issued, otherwise it is a player joining.
            for (DeferredHolder<Item, ? extends Item> entry : Registration.PATTERNS.getEntries()) {
                if (entry.get() instanceof AbstractPatternItem patternItem)
                    patternItem.clearCache();
            }
        }
    }
}
