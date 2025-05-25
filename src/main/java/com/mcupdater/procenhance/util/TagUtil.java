package com.mcupdater.procenhance.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TagUtil {

	public static ItemStack getItemStackForTag(TagKey<Item> tagKey) {
		var item = getItemForTag(tagKey);
		return item != null ? new ItemStack(item) : ItemStack.EMPTY;
	}

	@Nullable
	public static Item getItemForTag(TagKey<Item> tagKey) {
		// FUTURE TODO: Almost Unified integration

		return BuiltInRegistries.ITEM.getTag(tagKey)
				.flatMap(t -> t.stream().map(Holder::value).findFirst())
				.orElse(null);
	}
}
