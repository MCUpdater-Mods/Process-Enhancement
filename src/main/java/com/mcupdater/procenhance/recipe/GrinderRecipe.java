package com.mcupdater.procenhance.recipe;

import com.mcupdater.mculib.inventory.MachineContainer;
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

public class GrinderRecipe implements Recipe<MachineContainer> {
    private final NonNullList<Ingredient> ingredients;
    private final NonNullList<Tuple<ItemStack,Integer>> outputs;
    private final int processTime;
    private final float experience;

    public GrinderRecipe(NonNullList<Tuple<ItemStack,Integer>> outputs, int processTime, float experience, NonNullList<Ingredient> ingredients) {
        this.outputs = outputs;
        this.processTime = processTime;
        this.experience = experience;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(@NotNull MachineContainer container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return ingredients.getFirst().test(container.getItem(0));
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MachineContainer container, HolderLookup.@NotNull Provider pRegistries) {
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

    public NonNullList<Tuple<ItemStack, Integer>> getOutputs() {
        return this.outputs;
    }

    public static class Serializer implements RecipeSerializer<GrinderRecipe> {

        public static final Codec<Tuple<ItemStack,Integer>> TUPLE_CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ItemStack.CODEC.fieldOf("stack").forGetter(Tuple::getA),
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
            NonNullList<Tuple<ItemStack,Integer>> outputs = NonNullList.withSize(buf.readInt(), new Tuple<>(ItemStack.EMPTY,0));
            outputs.replaceAll(ignored -> new Tuple<>(ItemStack.STREAM_CODEC.decode(buf), buf.readInt()));
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
            for(Tuple<ItemStack,Integer> entry : recipe.getOutputs()) {
                ItemStack.STREAM_CODEC.encode(buf,entry.getA());
                buf.writeInt(entry.getB());
            }
            buf.writeInt(recipe.getProcessTime());
            buf.writeFloat(recipe.getExperience());
        }
    }

    /*
    public static class TupleCodec implements Codec<Tuple<ItemStack,Integer>> {

        @Override
        public <T> DataResult<Pair<Tuple<ItemStack, Integer>, T>> decode(DynamicOps<T> ops, T input) {
            return ItemStack.CODEC.decode(ops, input).flatMap(p1 ->
                    Codec.INT.decode(ops, p1.getSecond()).map(p2 ->
                            Pair.of(new Tuple<>(p1.getFirst(), p2.getFirst()), p2.getSecond())));
        }

        @Override
        public <T> DataResult<T> encode(Tuple<ItemStack, Integer> input, DynamicOps<T> ops, T prefix) {
            return Codec.INT.encode(input.getB(), ops, prefix).flatMap(f -> ItemStack.CODEC.encode(input.getA(), ops, f));
        }
    }
     */
}
