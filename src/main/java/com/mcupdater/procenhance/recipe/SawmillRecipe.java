package com.mcupdater.procenhance.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SawmillRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final int processTime;
    private final NonNullList<Ingredient> ingredients;
    private float experience;

    public SawmillRecipe(ResourceLocation id, ItemStack output, int processTime, float experience, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.result = output;
        this.processTime = processTime;
        this.experience = experience;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ingredients.get(0).test(container.getItem(2)); // match to phantom slot
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack assemble(Container container) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
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

    public float getExperience() {
        return experience;
    }

    public static class Type implements RecipeType<SawmillRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "sawmill";
    }

    public static class Serializer implements RecipeSerializer<SawmillRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProcessEnhancement.MODID,"sawmill");

        @Override
        public SawmillRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"result"));
            int processTime = GsonHelper.getAsInt(json,"processTime");
            float experience = GsonHelper.getAsFloat(json,"experience");
            JsonArray ingredientArray = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientArray.size(),Ingredient.EMPTY);
            for (int index = 0; index < ingredients.size(); index++) {
                ingredients.set(index,Ingredient.fromJson(ingredientArray.get(index)));
            }
            return new SawmillRecipe(id, output, processTime, experience, ingredients);
        }

        @Nullable
        @Override
        public SawmillRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for(int index = 0; index < ingredients.size(); index++) {
                ingredients.set(index, Ingredient.fromNetwork(buf));
            }
            int processTime = buf.readInt();
            float experience = buf.readFloat();
            ItemStack output = buf.readItem();
            return new SawmillRecipe(id, output, processTime, experience, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SawmillRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeInt(recipe.getProcessTime());
            buf.writeFloat(recipe.getExperience());
            buf.writeItemStack(recipe.getResultItem(), false);
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
