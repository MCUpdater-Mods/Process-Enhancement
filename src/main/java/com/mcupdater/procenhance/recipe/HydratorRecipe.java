package com.mcupdater.procenhance.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class HydratorRecipe implements Recipe<MachineContainer> {
	private final ResourceLocation id;
	private final ItemStack result;
	private final int processTime;
	private final NonNullList<Ingredient> itemIngredients;
	private final FluidStack fluidIngredient;

	public HydratorRecipe(ResourceLocation id, ItemStack output, int processTime, NonNullList<Ingredient> itemIngredients, FluidStack fluidIngredient) {
		this.id = id;
		this.result = output;
		this.processTime = processTime;
		this.itemIngredients = itemIngredients;
		this.fluidIngredient = fluidIngredient;
	}

	@Override
	public boolean matches(MachineContainer pContainer, Level pLevel) {
		if (pLevel.isClientSide()) {
			return false;
		}

		return itemIngredients.get(0).test(pContainer.getItem(0)) && fluidIngredient.containsFluid(pContainer.getFluidHandler().getFluidInTank(0));
	}

	public NonNullList<Ingredient> getItemIngredients() {
		return this.itemIngredients;
	}

	public FluidStack getFluidIngredient() {
		return this.fluidIngredient;
	}

	@Override
	public ItemStack assemble(MachineContainer pContainer) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return result.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	public int getProcessTime() {
		return this.processTime;
	}

	public static class Type implements RecipeType<HydratorRecipe> {
		private Type() {}
		public static final Type INSTANCE = new Type();
		public static final String ID = "hydrator";
	}

	public static class Serializer implements RecipeSerializer<HydratorRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(ProcessEnhancement.MODID, "hydrator");

		@Override
		public HydratorRecipe fromJson(ResourceLocation id, JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			int processTime = GsonHelper.getAsInt(json, "processTime");
			JsonArray itemIngredientArray = GsonHelper.getAsJsonArray(json, "itemIngredients");
			NonNullList<Ingredient> itemIngredients = NonNullList.withSize(itemIngredientArray.size(),Ingredient.EMPTY);
			for (int index = 0; index < itemIngredients.size(); index++) {
				itemIngredients.set(index,Ingredient.fromJson(itemIngredientArray.get(index)));
			}
			FluidStack fluidIngredient =  DataHelper.getJsonFluidStack(GsonHelper.getAsJsonObject(json, "fluidIngredient"));
			return new HydratorRecipe(id, output, processTime, itemIngredients, fluidIngredient);
		}

		@Override
		public @Nullable HydratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> itemIngredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			for(int index = 0; index < itemIngredients.size(); index++) {
				itemIngredients.set(index, Ingredient.fromNetwork(buf));
			}
			FluidStack fluidIngredient = FluidStack.readFromPacket(buf);
			int processTime = buf.readInt();
			ItemStack output = buf.readItem();
			return new HydratorRecipe(id, output, processTime, itemIngredients, fluidIngredient);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, HydratorRecipe recipe) {
			buf.writeInt(recipe.getItemIngredients().size());
			for(Ingredient ingredient : recipe.getItemIngredients()) {
				ingredient.toNetwork(buf);
			}
			recipe.fluidIngredient.writeToPacket(buf);
			buf.writeInt(recipe.getProcessTime());
			buf.writeItemStack(recipe.getResultItem(), false);
		}

		@SuppressWarnings("unchecked")
		private static <G> Class<G> castClass(Class<?> cls) {
			return (Class<G>)cls;
		}
	}
}
