package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.battery.BatteryBlockItem;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class BatteryUpgradeRecipe extends ShapedRecipe {

    private ItemStack result;

    public BatteryUpgradeRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult, boolean pShowNotification) {
        super(pGroup, pCategory, pPattern, pResult, pShowNotification);
        this.result = pResult;
    }

    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        craftingInput.items().stream().forEach(stack -> ProcessEnhancement.LOGGER.debug("Input: " + stack.toString()));
        this.pattern.ingredients().stream().forEach(stack -> ProcessEnhancement.LOGGER.debug("Pattern: " + stack.toString()));
        return this.pattern.matches(craftingInput);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput pInv, HolderLookup.@NotNull Provider pRegistries) {
        ItemStack outputStack = super.assemble(pInv, pRegistries);
        getMachine(pInv).map(ItemStack::getComponents).ifPresent(outputStack::applyComponents);
        return outputStack;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Registration.BATTERYUPGRADE_SERIALIZER.get();
    }

    private Optional<ItemStack> getMachine(CraftingInput pInv) {
        for (int slot = 0; slot < pInv.size(); slot++) {
            ItemStack slotStack = pInv.getItem(slot);
            if (slotStack.getItem() instanceof BatteryBlockItem) {
                return Optional.of(slotStack);
            }
        }
        return Optional.empty();
    }

    public ShapedRecipePattern getPattern() {
        return this.pattern;
    }

    private ItemStack getResult() {
        return this.result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<BatteryUpgradeRecipe> {
        public static final MapCodec<BatteryUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                                Codec.STRING.optionalFieldOf("group","").forGetter(BatteryUpgradeRecipe::getGroup),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(BatteryUpgradeRecipe::category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(BatteryUpgradeRecipe::getPattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(BatteryUpgradeRecipe::getResult),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(BatteryUpgradeRecipe::showNotification)
                        )
                        .apply(inst, BatteryUpgradeRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, BatteryUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
                BatteryUpgradeRecipe.Serializer::toNetwork,
                BatteryUpgradeRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<BatteryUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BatteryUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static BatteryUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            CraftingBookCategory craftingBookCategory = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedRecipePattern = ShapedRecipePattern.STREAM_CODEC.decode(pBuffer);
            ItemStack itemStack = ItemStack.STREAM_CODEC.decode(pBuffer);
            boolean notify = pBuffer.readBoolean();
            return new BatteryUpgradeRecipe(group, craftingBookCategory, shapedRecipePattern, itemStack, notify);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, BatteryUpgradeRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeEnum(pRecipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(pBuffer, pRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification());
        }
    }

}
