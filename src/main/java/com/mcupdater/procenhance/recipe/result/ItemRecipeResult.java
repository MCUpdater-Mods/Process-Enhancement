package com.mcupdater.procenhance.recipe.result;

import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class ItemRecipeResult extends RecipeResult {
	public static final MapCodec<ItemRecipeResult> INGREDIENT_COMPAT_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
			ItemStack.ITEM_NON_AIR_CODEC.fieldOf("item").forGetter(t -> t.itemStack.getItemHolder()),
			Codec.INT.fieldOf("count").forGetter(t -> t.itemStack.getCount()),
			DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(t -> t.itemStack.getComponentsPatch())
	).apply(builder, (item, count, components) -> new ItemRecipeResult(new ItemStack(item, count, components))));

	public static final MapCodec<ItemRecipeResult> ITEM_STACK_COMPAT_CODEC = MapCodec.assumeMapUnsafe(ItemStack.STRICT_CODEC.xmap(ItemRecipeResult::new, ItemRecipeResult::getItemStack));

	public static final MapCodec<ItemRecipeResult> CODEC = Codec.mapEither(ITEM_STACK_COMPAT_CODEC, INGREDIENT_COMPAT_CODEC).xmap(Either::unwrap, Either::left);

	public static final StreamCodec<RegistryFriendlyByteBuf, ItemRecipeResult> STREAM_CODEC = StreamCodec.composite(
			ItemStack.OPTIONAL_STREAM_CODEC,
			ItemRecipeResult::getItemStack,
			ItemRecipeResult::new
	);

	private final ItemStack itemStack;

	@Nullable
	private ItemStack[] cachedItemStacks;

	// Constructors
	public ItemRecipeResult(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemRecipeResult(ItemLike item, int count) {
		this.itemStack = new ItemStack(item, count);
	}

	//
	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public ItemStack[] getItemStacks() {
		if (this.cachedItemStacks == null) {
			this.cachedItemStacks = new ItemStack[]{this.itemStack};
		}
		return this.cachedItemStacks;
	}

	@Override
	public RecipeResultType<?> getType() {
		return Registration.RESULT_ITEM.get();
	}

	@Override
	public RecipeResult copyWithCount(int count) {
		return new ItemRecipeResult(this.itemStack.copyWithCount(count));
	}
}
