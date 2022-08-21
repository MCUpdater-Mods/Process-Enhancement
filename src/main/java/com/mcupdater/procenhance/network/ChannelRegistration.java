package com.mcupdater.procenhance.network;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ChannelRegistration {
    private static final String PROTOCOL = "1";
    public static SimpleChannel STONECUTTER_RECIPE_CHANGE;
    public static SimpleChannel SAWMILL_RECIPE_CHANGE;

    public static void init() {
        STONECUTTER_RECIPE_CHANGE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ProcessEnhancement.MODID, "stonecutter_recipe_change"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

        STONECUTTER_RECIPE_CHANGE.registerMessage(0,
                RecipeChangeStonecutterPacket.class,
                RecipeChangeStonecutterPacket::toBytes,
                RecipeChangeStonecutterPacket::fromBytes,
                RecipeChangeStonecutterPacket::handle
                );

        SAWMILL_RECIPE_CHANGE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ProcessEnhancement.MODID, "sawmill_recipe_change"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

        SAWMILL_RECIPE_CHANGE.registerMessage(0,
                RecipeChangeSawmillPacket.class,
                RecipeChangeSawmillPacket::toBytes,
                RecipeChangeSawmillPacket::fromBytes,
                RecipeChangeSawmillPacket::handle
        );
    }
}
