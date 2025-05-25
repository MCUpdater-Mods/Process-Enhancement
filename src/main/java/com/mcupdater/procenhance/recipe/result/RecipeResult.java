package com.mcupdater.procenhance.recipe.result;

import com.mcupdater.procenhance.setup.PERegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class RecipeResult {
	public static final Codec<RecipeResult> CODEC = PERegistries.RECIPE_RESULT_TYPES.byNameCodec().dispatch(RecipeResult::getType, RecipeResultType::codec);
	public static final StreamCodec<RegistryFriendlyByteBuf, RecipeResult> STREAM_CODEC = ByteBufCodecs.registry(PERegistries.Keys.RECIPE_RESULT_TYPES).dispatch(RecipeResult::getType, RecipeResultType::streamCodec);

	public static RecipeResult of(ItemStack itemStack) {
		return new ItemRecipeResult(itemStack);
	}

	public static RecipeResult of(TagKey<Item> tagKey) {
		return new TagRecipeResult(tagKey, 1);
	}

	public static RecipeResult of(TagKey<Item> tagKey, int count) {
		return new TagRecipeResult(tagKey, count);
	}

	public static RecipeResult of(TagKey<Item> tagKey, int count, DataComponentPatch patch) {
		return new TagRecipeResult(tagKey, count, patch);
	}

	public abstract ItemStack getItemStack();

	public abstract ItemStack[] getItemStacks();
	public abstract RecipeResultType<?> getType();
	public abstract RecipeResult copyWithCount(int count);
}
