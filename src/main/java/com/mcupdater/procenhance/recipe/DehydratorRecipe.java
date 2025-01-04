package com.mcupdater.procenhance.recipe;

import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class DehydratorRecipe implements Recipe<MachineContainer> {
	private final ItemStack result;
	private final int processTime;
	private final NonNullList<Ingredient> itemIngredients;
	private final FluidStack fluidOutput;

	public DehydratorRecipe(ItemStack output, int processTime, NonNullList<Ingredient> itemIngredients, FluidStack fluidOutput) {
		this.result = output;
		this.processTime = processTime;
		this.itemIngredients = itemIngredients;
		this.fluidOutput = fluidOutput;
	}

	@Override
	public boolean matches(@NotNull MachineContainer pContainer, Level pLevel) {
		if (pLevel.isClientSide()) {
			return false;
		}
		if (com.mcupdater.mculib.setup.Config.DEBUG.get()) ProcessEnhancement.LOGGER.debug("Dehydrator checking recipe");
		return itemIngredients.getFirst().test(pContainer.getItem(0));
	}

	public NonNullList<Ingredient> getItemIngredients() {
		return this.itemIngredients;
	}

	public FluidStack getFluidOutput() {
		return this.fluidOutput;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull MachineContainer pContainer, HolderLookup.@NotNull Provider pRegistries) {
		return this.getResultItem(pRegistries).copy();
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
		return this.result;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return Registration.DEHYDRATOR_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return Registration.DEHYDRATOR_RECIPE.get();
	}

	public int getProcessTime() {
		return this.processTime;
	}

	public static class Serializer implements RecipeSerializer<DehydratorRecipe> {
		public static final MapCodec<DehydratorRecipe> CODEC = RecordCodecBuilder.mapCodec(
				inst -> inst.group(
								// (ItemStack output, int processTime, NonNullList<Ingredient> itemIngredients, FluidStack fluidOutput)
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(DehydratorRecipe::getResult),
								Codec.INT.fieldOf("processTime").forGetter(DehydratorRecipe::getProcessTime),
								NonNullList.codecOf(Ingredient.CODEC).fieldOf("itemIngredients").forGetter(DehydratorRecipe::getItemIngredients),
								FluidStack.CODEC.fieldOf("fluidOutput").forGetter(DehydratorRecipe::getFluidOutput)
						)
						.apply(inst, DehydratorRecipe::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, DehydratorRecipe> STREAM_CODEC = StreamCodec.of(
				DehydratorRecipe.Serializer::toNetwork,
				DehydratorRecipe.Serializer::fromNetwork
		);

		@Override
		public @NotNull MapCodec<DehydratorRecipe> codec() {
			return CODEC;
		}

		@Override
		public @NotNull StreamCodec<RegistryFriendlyByteBuf, DehydratorRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		public static DehydratorRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
			NonNullList<Ingredient> itemIngredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			itemIngredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
			FluidStack fluidOutput = FluidStack.STREAM_CODEC.decode(buf);
			int processTime = buf.readInt();
			ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
			return new DehydratorRecipe(output, processTime, itemIngredients, fluidOutput);
		}

		public static void toNetwork(RegistryFriendlyByteBuf buf, DehydratorRecipe recipe) {
			buf.writeInt(recipe.getItemIngredients().size());
			for (Ingredient ingredient : recipe.getItemIngredients()) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
			}
			FluidStack.STREAM_CODEC.encode(buf, recipe.fluidOutput);
			buf.writeInt(recipe.processTime);
			ItemStack.STREAM_CODEC.encode(buf, recipe.result);
		}
	}
}
