package com.mcupdater.procenhance.recipe;

import com.mcupdater.mculib.inventory.MachineContainer;
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

public class HydratorRecipe implements Recipe<MachineContainer> {
	private final ItemStack result;
	private final int processTime;
	private final NonNullList<Ingredient> itemIngredients;
	private final FluidStack fluidIngredient;

	public HydratorRecipe(ItemStack output, int processTime, NonNullList<Ingredient> itemIngredients, FluidStack fluidIngredient) {
		this.result = output;
		this.processTime = processTime;
		this.itemIngredients = itemIngredients;
		this.fluidIngredient = fluidIngredient;
	}

	@Override
	public boolean matches(@NotNull MachineContainer pContainer, Level pLevel) {
		if (pLevel.isClientSide()) {
			return false;
		}
		return itemIngredients.getFirst().test(pContainer.getItem(0)) && FluidStack.isSameFluid(pContainer.getFluidHandler().getInternalHandler().getFluidInTank(0),fluidIngredient);
	}

	public NonNullList<Ingredient> getItemIngredients() {
		return this.itemIngredients;
	}

	public FluidStack getFluidIngredient() {
		return this.fluidIngredient;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull MachineContainer pContainer, HolderLookup.@NotNull Provider pRegistries) {
		return this.getResultItem(pRegistries).copy();
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
		return this.result;
	}

	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return Registration.HYDRATOR_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return Registration.HYDRATOR_RECIPE.get();
	}

	public int getProcessTime() {
		return this.processTime;
	}

	public static class Serializer implements RecipeSerializer<HydratorRecipe> {
		public static final MapCodec<HydratorRecipe> CODEC = RecordCodecBuilder.mapCodec(
				inst -> inst.group(
								// (ItemStack output, int processTime, NonNullList<Ingredient> itemIngredients, FluidStack fluidIngredient)
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(HydratorRecipe::getResult),
								Codec.INT.fieldOf("processTime").forGetter(HydratorRecipe::getProcessTime),
								NonNullList.codecOf(Ingredient.CODEC).fieldOf("itemIngredients").forGetter(HydratorRecipe::getItemIngredients),
								FluidStack.CODEC.fieldOf("fluidIngredient").forGetter(HydratorRecipe::getFluidIngredient)
						)
						.apply(inst, HydratorRecipe::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, HydratorRecipe> STREAM_CODEC = StreamCodec.of(
				HydratorRecipe.Serializer::toNetwork,
				HydratorRecipe.Serializer::fromNetwork
		);

		@Override
		public @NotNull MapCodec<HydratorRecipe> codec() {
			return CODEC;
		}

		@Override
		public @NotNull StreamCodec<RegistryFriendlyByteBuf, HydratorRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		public static HydratorRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
			NonNullList<Ingredient> itemIngredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			itemIngredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
			FluidStack fluidIngredient = FluidStack.STREAM_CODEC.decode(buf);
			int processTime = buf.readInt();
			ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
			return new HydratorRecipe(output, processTime, itemIngredients, fluidIngredient);
		}

		public static void toNetwork(RegistryFriendlyByteBuf buf, HydratorRecipe recipe) {
			buf.writeInt(recipe.getItemIngredients().size());
			for (Ingredient ingredient : recipe.getItemIngredients()) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
			}
			FluidStack.STREAM_CODEC.encode(buf, recipe.fluidIngredient);
			buf.writeInt(recipe.processTime);
			ItemStack.STREAM_CODEC.encode(buf, recipe.result);
		}
	}
}
