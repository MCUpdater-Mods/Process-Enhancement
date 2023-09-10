package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.items.autopackager.AbstractPatternItem;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ProcessEnhancement.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusHandlers {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, SawmillRecipe.Type.ID), SawmillRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, GrinderRecipe.Type.ID), GrinderRecipe.Type.INSTANCE);
        });
    }

}
