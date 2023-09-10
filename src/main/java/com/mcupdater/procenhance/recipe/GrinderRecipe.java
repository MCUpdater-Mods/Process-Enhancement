package com.mcupdater.procenhance.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GrinderRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final NonNullList<Tuple<ItemStack,Integer>> outputs;
    private final int processTime;
    private final float experience;

    public GrinderRecipe(ResourceLocation id, NonNullList<Tuple<ItemStack,Integer>> outputs, int processTime, float experience, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.outputs = outputs;
        this.processTime = processTime;
        this.experience = experience;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return ingredients.get(0).test(container.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack assemble(Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
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

    public float getExperience() {
        return experience;
    }

    public NonNullList<Tuple<ItemStack, Integer>> getOutputs() {
        return this.outputs;
    }

    public static class Type implements RecipeType<GrinderRecipe> {
        private Type() {}

        public static final Type INSTANCE = new Type();

        public static final String ID = "grinder";
    }

    public static class Serializer implements RecipeSerializer<GrinderRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProcessEnhancement.MODID,"grinder");

        @Override
        public GrinderRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray outputsArray = GsonHelper.getAsJsonArray(json, "outputs");
            NonNullList<Tuple<ItemStack,Integer>> outputs = NonNullList.withSize(outputsArray.size(),new Tuple<>(ItemStack.EMPTY,0));
            for (int index1 = 0; index1 < outputs.size(); index1++) {
                JsonObject entry = (JsonObject) outputsArray.get(index1);
                outputs.set(index1,new Tuple<>(ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(entry,"stack")),GsonHelper.getAsInt(entry, "weight")));
            }
            JsonArray ingredientArray = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientArray.size(),Ingredient.EMPTY);
            for (int index2 = 0; index2 < ingredients.size(); index2++) {
                ingredients.set(index2,Ingredient.fromJson(ingredientArray.get(index2)));
            }
            int processTime = GsonHelper.getAsInt(json,"processTime");
            float experience = GsonHelper.getAsFloat(json,"experience");
            return new GrinderRecipe(id, outputs, processTime, experience, ingredients);
        }

        @Nullable
        @Override
        public GrinderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int index1 = 0; index1 < ingredients.size(); index1++) {
                ingredients.set(index1, Ingredient.fromNetwork(buf));
            }
            NonNullList<Tuple<ItemStack,Integer>> outputs = NonNullList.withSize(buf.readInt(), new Tuple<>(ItemStack.EMPTY,0));
            for (int index2 = 0; index2 < outputs.size(); index2++) {
                outputs.set(index2, new Tuple<>(buf.readItem(),buf.readInt()));
            }
            int processTime = buf.readInt();
            float experience = buf.readFloat();
            return new GrinderRecipe(id, outputs, processTime, experience, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GrinderRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeInt(recipe.getOutputs().size());
            for(Tuple<ItemStack,Integer> entry : recipe.getOutputs()) {
                buf.writeItemStack(entry.getA(), false);
                buf.writeInt(entry.getB());
            }
            buf.writeInt(recipe.getProcessTime());
            buf.writeFloat(recipe.getExperience());
        }

        /*
        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }
        */

        @SuppressWarnings("unchecked")
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }



}
