package com.mcupdater.procenhance.network;


import com.mcupdater.procenhance.ProcessEnhancement;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ChannelRegistration {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(ProcessEnhancement.MODID).versioned("1.0");
        registrar.playBidirectional(RecipeChange.TYPE, RecipeChange.STREAM_CODEC, RecipeChange.PayloadHandler::handle);
    }
}
