package com.mcupdater.procenhance.network;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ChannelRegistration {
    private static final String PROTOCOL = "1";
    public static SimpleChannel RECIPE_CHANGE;

    public static void init() {
        RECIPE_CHANGE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ProcessEnhancement.MODID, "recipe_change"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

        RECIPE_CHANGE.registerMessage(0,
                RecipeChangeStonecutterPacket.class,
                RecipeChangeStonecutterPacket::toBytes,
                RecipeChangeStonecutterPacket::fromBytes,
                RecipeChangeStonecutterPacket::handle
                );
    }
}
