package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class StairmakerRecipe implements Recipe<SingleRecipeInput> {
	protected final Ingredient input;
	protected final ItemStack result;

	public StairmakerRecipe(Ingredient input, ItemStack result) {
		this.input = input;
		this.result = result;
	}

	@Override
	public boolean matches(SingleRecipeInput input, Level level) {
		return this.input.test(input.item());
	}

	@Override
	public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
		return this.getResultItem(registries).copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registries) {
		return this.result;
	}

	public BlockState getResultBlockState(HolderLookup.Provider registries) {
		if (this.result.getItem() instanceof BlockItem blockItem) {
			return blockItem.getBlock().defaultBlockState();
		} else {
			return null;
		}
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Registration.STAIRMAKER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return Registration.STAIRMAKER_RECIPE.get();
	}

	public static class Serializer implements RecipeSerializer<StairmakerRecipe> {

			MapCodec<StairmakerRecipe> CODEC = RecordCodecBuilder.mapCodec(
					inst -> inst.group(
							Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(recipe -> recipe.input),
							ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
					)
							.apply(inst, StairmakerRecipe::new)
			);

			StreamCodec<RegistryFriendlyByteBuf, StairmakerRecipe> STREAM_CODEC = StreamCodec.of(
					StairmakerRecipe.Serializer::toNetwork,
					StairmakerRecipe.Serializer::fromNetwork
			);

			public static StairmakerRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
				Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
				ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
				return new StairmakerRecipe(ingredient, result);
			}

			public static void toNetwork(RegistryFriendlyByteBuf buf, StairmakerRecipe recipe) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input);
				ItemStack.STREAM_CODEC.encode(buf, recipe.result);
			}

			@Override
		public MapCodec<StairmakerRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, StairmakerRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
