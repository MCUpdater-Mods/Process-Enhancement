package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MinerRecipe extends ShapedRecipe {

    private final List<Item> VALID_ITEMS = new ArrayList<>(Arrays.asList(Items.DIAMOND_PICKAXE,Registration.MINERT1_BLOCKITEM.get(),Registration.MINERT2_BLOCKITEM.get(),Registration.MINERT3_BLOCKITEM.get(),Registration.MINERT4_BLOCKITEM.get()));
    private final ItemStack result;

    public MinerRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult, boolean pShowNotification) {
        super(pGroup, pCategory, pPattern, pResult, pShowNotification);
        this.result = pResult;
    }

    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        return this.pattern.matches(craftingInput);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput pInv, HolderLookup.@NotNull Provider pRegistries) {
        ItemStack outputStack = this.getResultItem(pRegistries).copy();
        getPickaxe(pInv).flatMap(pickaxe -> Optional.of(pickaxe.getTagEnchantments())).ifPresent(tag -> {
            for (Object2IntMap.Entry<Holder<Enchantment>> enchant : tag.entrySet()) {
                outputStack.enchant(enchant.getKey(),enchant.getIntValue());
            }
        });
        return outputStack;
    }

    private Optional<ItemStack> getPickaxe(CraftingInput pInv) {
        for (int slot = 0; slot < pInv.size(); slot++) {
            ItemStack slotStack = pInv.getItem(slot);
            if (VALID_ITEMS.contains(slotStack.getItem())) {
                return Optional.of(slotStack);
            }
        }
        return Optional.of(ItemStack.EMPTY);
    }

    public ShapedRecipePattern getPattern() {
        return this.pattern;
    }

    private ItemStack getResult() {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Registration.MINER_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<MinerRecipe> {
        public static final MapCodec<MinerRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                                Codec.STRING.optionalFieldOf("group","").forGetter(MinerRecipe::getGroup),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(MinerRecipe::category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(MinerRecipe::getPattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(MinerRecipe::getResult),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(MinerRecipe::showNotification)
                        )
                        .apply(inst, MinerRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> STREAM_CODEC = StreamCodec.of(
                MinerRecipe.Serializer::toNetwork,
                MinerRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<MinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static MinerRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            CraftingBookCategory craftingBookCategory = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedRecipePattern = ShapedRecipePattern.STREAM_CODEC.decode(pBuffer);
            ItemStack itemStack = ItemStack.STREAM_CODEC.decode(pBuffer);
            boolean notify = pBuffer.readBoolean();
            return new MinerRecipe(group, craftingBookCategory, shapedRecipePattern, itemStack, notify);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, MinerRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeEnum(pRecipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(pBuffer, pRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification());
        }
    }
}
