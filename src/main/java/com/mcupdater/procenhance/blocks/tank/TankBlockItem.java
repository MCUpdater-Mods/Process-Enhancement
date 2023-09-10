package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TankBlockItem extends BlockItem {
    protected final int capacity;

    public TankBlockItem(@NotNull TankBlock tankBlock, Item.Properties properties, int capacity) {
        super(tankBlock, properties);
        this.capacity = capacity;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, capacity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        pStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).ifPresent(fluidStorage -> pTooltip.add(Component.literal("Contains: ").append(fluidStorage.getFluidInTank(0).isEmpty() ? Component.literal("Empty") : Component.translatable(fluidStorage.getFluidInTank(0).getFluid().getFluidType().getDescriptionId()))));
    }
}
