package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.recipe.result.RecipeResultType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class PERegistries {
	public static final Registry<RecipeResultType<?>> RECIPE_RESULT_TYPES = new RegistryBuilder<>(Keys.RECIPE_RESULT_TYPES).sync(true).create();

	public static void onRegisterRecipes(NewRegistryEvent event) {
		event.register(RECIPE_RESULT_TYPES);
	}

	public static final class Keys {
		public static final ResourceKey<Registry<RecipeResultType<?>>> RECIPE_RESULT_TYPES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID,"recipe_result_type"));
	}
}
