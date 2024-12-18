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
import org.jetbrains.annotations.NotNull;

public class SawmillRecipe implements Recipe<MachineContainer> {
    private final ItemStack result;
    private final int processTime;
    private final NonNullList<Ingredient> ingredients;
    private final float experience;

    public SawmillRecipe(ItemStack result, int processTime, float experience, NonNullList<Ingredient> ingredients) {
        this.result = result;
        this.processTime = processTime;
        this.experience = experience;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(@NotNull MachineContainer container, @NotNull Level level) {
        return ingredients.getFirst().test(container.getItem(2)); // match to phantom slot
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MachineContainer container, HolderLookup.@NotNull Provider pRegistries) {
        return this.getResultItem(pRegistries).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
        return this.result;
    }

    public ItemStack result() {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Registration.SAWMILL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Registration.SAWMILL_RECIPE.get();
    }

    public int processTime() {
        return this.processTime;
    }

    public float getExperience() {
        return experience;
    }

    public static class Serializer implements RecipeSerializer<SawmillRecipe> {
        public static final MapCodec<SawmillRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                                // ItemStack result, int processTime, float experience, NonNullList<Ingredient> ingredients
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(SawmillRecipe::result),
                                Codec.INT.fieldOf("processTime").forGetter(SawmillRecipe::processTime),
                                Codec.FLOAT.fieldOf("experience").forGetter(SawmillRecipe::getExperience),
                                NonNullList.codecOf(Ingredient.CODEC).fieldOf("ingredients").forGetter(SawmillRecipe::getIngredients)
                        )
                        .apply(inst, SawmillRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, SawmillRecipe> STREAM_CODEC = StreamCodec.of(
                SawmillRecipe.Serializer::toNetwork,
                SawmillRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<SawmillRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SawmillRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static SawmillRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            int processTime = buf.readInt();
            float experience = buf.readFloat();
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new SawmillRecipe(output, processTime, experience, ingredients);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, SawmillRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf,ingredient);
            }
            buf.writeInt(recipe.processTime());
            buf.writeFloat(recipe.getExperience());
            ItemStack.STREAM_CODEC.encode(buf,recipe.result);
        }
    }
}
