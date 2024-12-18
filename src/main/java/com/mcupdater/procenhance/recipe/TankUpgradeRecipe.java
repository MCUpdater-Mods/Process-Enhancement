package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.blocks.tank.TankBlockItem;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TankUpgradeRecipe extends ShapedRecipe {

    final ItemStack result;

    public TankUpgradeRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult, boolean pShowNotification) {
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
        @Nullable IFluidHandlerItem oldFluidCap = getMachine(pInv).getCapability(Capabilities.FluidHandler.ITEM, null);
        if (oldFluidCap != null) {
            Objects.requireNonNull(outputStack.getCapability(Capabilities.FluidHandler.ITEM,null)).fill(oldFluidCap.getFluidInTank(0), IFluidHandler.FluidAction.EXECUTE);
        }
        return outputStack;
    }

    private ItemStack getMachine(CraftingInput pInv) {
        for (int slot = 0; slot < pInv.size(); slot++) {
            ItemStack slotStack = pInv.getItem(slot);
            if (slotStack.getItem() instanceof TankBlockItem) {
                return slotStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Registration.TANK_UPGRADE_SERIALIZER.get();
    }

    public ShapedRecipePattern getPattern() {
        return this.pattern;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public static class Serializer implements RecipeSerializer<TankUpgradeRecipe> {
        public static final MapCodec<TankUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                                Codec.STRING.optionalFieldOf("group","").forGetter(TankUpgradeRecipe::getGroup),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(TankUpgradeRecipe::category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(TankUpgradeRecipe::getPattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(TankUpgradeRecipe::getResult),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(TankUpgradeRecipe::showNotification)
                        )
                        .apply(inst, TankUpgradeRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, TankUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
                TankUpgradeRecipe.Serializer::toNetwork,
                TankUpgradeRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<TankUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, TankUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static TankUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            CraftingBookCategory craftingBookCategory = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedRecipePattern = ShapedRecipePattern.STREAM_CODEC.decode(pBuffer);
            ItemStack itemStack = ItemStack.STREAM_CODEC.decode(pBuffer);
            boolean notify = pBuffer.readBoolean();
            return new TankUpgradeRecipe(group, craftingBookCategory, shapedRecipePattern, itemStack, notify);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, TankUpgradeRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeEnum(pRecipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(pBuffer, pRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification());
        }
    }
}
