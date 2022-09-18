package com.mcupdater.procenhance.blocks.battery;

import com.mcupdater.procenhance.capabilities.InternalEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatteryBlockItem extends BlockItem {
    private final int maxTransfer;
    public BatteryBlockItem(@NotNull BatteryBlock batteryBlock, Item.Properties properties, int maxTransfer) {
        super(batteryBlock, properties);
        this.maxTransfer = maxTransfer;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BatteryCapabilityProvider(stack, nbt, 50 * maxTransfer, maxTransfer);

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        pStack.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(energyStorage -> pTooltip.add(new TextComponent(String.format("%d / %d FE",energyStorage.getEnergyStored(),energyStorage.getMaxEnergyStored()))));
    }

    private class BatteryCapabilityProvider implements ICapabilityProvider {
        private int capacity;
        private int maxTransfer;
        private ItemStack itemStack;
        private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new InternalEnergyStorage(itemStack, capacity, maxTransfer));
        public BatteryCapabilityProvider(ItemStack stack, @Nullable CompoundTag nbt, int capacity, int maxTransfer) {
            this.itemStack = stack;
            this.capacity = capacity;
            this.maxTransfer = maxTransfer;
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
        }
    }
}
