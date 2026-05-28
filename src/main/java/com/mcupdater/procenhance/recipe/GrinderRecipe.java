package com.mcupdater.procenhance.recipe;

import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GrinderRecipe implements Recipe<SingleRecipeInput> {
    private final NonNullList<Ingredient> ingredients;
    private final NonNullList<Tuple<RecipeResult,Integer>> outputs;
    private final int processTime;
    private final float experience;

    public GrinderRecipe(NonNullList<Tuple<RecipeResult,Integer>> outputs, int processTime, float experience, NonNullList<Ingredient> ingredients) {
        this.outputs = outputs;
        this.processTime = processTime;
        this.experience = experience;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return ingredients.getFirst().test(container.item());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput container, HolderLookup.@NotNull Provider pRegistries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Registration.GRINDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Registration.GRINDER_RECIPE.get();
    }

    public int getProcessTime() {
        return this.processTime;
    }

    public float getExperience() {
        return experience;
    }

    public NonNullList<Tuple<RecipeResult, Integer>> getOutputs() {
        return this.outputs;
    }

    public static class Serializer implements RecipeSerializer<GrinderRecipe> {

        public static final Codec<Tuple<RecipeResult,Integer>> TUPLE_CODEC = RecordCodecBuilder.create(inst -> inst.group(
                RecipeResult.CODEC.fieldOf("result").forGetter(Tuple::getA),
                Codec.INT.fieldOf("weight").forGetter(Tuple::getB)
        ).apply(inst, Tuple::new));

        public static final MapCodec<GrinderRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                        // NonNullList<Tuple<ItemStack,Integer>> outputs, int processTime, float experience, NonNullList<Ingredient> ingredients
                        NonNullList.codecOf(TUPLE_CODEC).fieldOf("outputs").forGetter(GrinderRecipe::getOutputs),
                        Codec.INT.fieldOf("processTime").forGetter(GrinderRecipe::getProcessTime),
                        Codec.FLOAT.fieldOf("experience").forGetter(GrinderRecipe::getExperience),
                        NonNullList.codecOf(Ingredient.CODEC).fieldOf("ingredients").forGetter(GrinderRecipe::getIngredients)
                )
                        .apply(inst,GrinderRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, GrinderRecipe> STREAM_CODEC = StreamCodec.of(
                GrinderRecipe.Serializer::toNetwork,
                GrinderRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<GrinderRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, GrinderRecipe> streamCodec() {
            return STREAM_CODEC;
        }


        public static @NotNull GrinderRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            NonNullList<Tuple<RecipeResult,Integer>> outputs = NonNullList.withSize(buf.readInt(), new Tuple<>(RecipeResult.of(ItemStack.EMPTY),0));
            outputs.replaceAll(ignored -> new Tuple<>(RecipeResult.STREAM_CODEC.decode(buf), buf.readInt()));
            int processTime = buf.readInt();
            float experience = buf.readFloat();
            return new GrinderRecipe(outputs, processTime, experience, ingredients);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, GrinderRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            buf.writeInt(recipe.getOutputs().size());
            for(Tuple<RecipeResult,Integer> entry : recipe.getOutputs()) {
                RecipeResult.STREAM_CODEC.encode(buf,entry.getA());
                buf.writeInt(entry.getB());
            }
            buf.writeInt(recipe.getProcessTime());
            buf.writeFloat(recipe.getExperience());
        }
    }
}
