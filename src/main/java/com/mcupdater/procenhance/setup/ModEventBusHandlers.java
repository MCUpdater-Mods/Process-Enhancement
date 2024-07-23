package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.DehydratorRecipe;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.HydratorRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = ProcessEnhancement.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusHandlers {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, SawmillRecipe.Type.ID), SawmillRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, GrinderRecipe.Type.ID), GrinderRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, HydratorRecipe.Type.ID), HydratorRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(ProcessEnhancement.MODID, DehydratorRecipe.Type.ID), DehydratorRecipe.Type.INSTANCE);
        });
    }

}
