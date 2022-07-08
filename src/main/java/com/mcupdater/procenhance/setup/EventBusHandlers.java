package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProcessEnhancement.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusHandlers {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, SawmillRecipe.Type.ID, SawmillRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, GrinderRecipe.Type.ID, GrinderRecipe.Type.INSTANCE);
    }
}
