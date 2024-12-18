package com.mcupdater.procenhance.capabilities;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ItemEnergyStorage extends EnergyStorage {
    protected final ItemStack stack;

    public ItemEnergyStorage(ItemStack itemStack, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.stack = itemStack;
        this.energy = itemStack.getOrDefault(Registration.STORED_ENERGY,0);
    }

    public void setStoredEnergy(int amount) {
        this.energy = Math.min(amount, this.capacity);
        stack.set(Registration.STORED_ENERGY, this.energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = super.receiveEnergy(maxReceive, simulate);
        if (!simulate) {
            stack.set(Registration.STORED_ENERGY, this.energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate) {
            stack.set(Registration.STORED_ENERGY, this.energy);
        }
        return energyExtracted;
    }
}
