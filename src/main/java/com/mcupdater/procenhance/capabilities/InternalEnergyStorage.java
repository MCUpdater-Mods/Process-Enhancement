package com.mcupdater.procenhance.capabilities;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class InternalEnergyStorage implements IEnergyStorage, INBTSerializable<Tag> {
    private final int capacity;
    private final int maxTransfer;
    private final ItemStack stack;
    private int storedEnergy;

    public InternalEnergyStorage(ItemStack itemStack, int capacity, int maxTransfer) {
        this.stack = itemStack;
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
        this.storedEnergy = stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getInt("energy") : 0;
    }

    public void setStoredEnergy(int amount) {
        this.storedEnergy = Math.min(amount, this.capacity);
        stack.getOrCreateTag().putInt("energy", this.storedEnergy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(this.capacity - this.storedEnergy, Math.min(this.maxTransfer, maxReceive));
        if (!simulate) {
            this.storedEnergy += energyReceived;
            stack.getOrCreateTag().putInt("energy", this.storedEnergy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(this.storedEnergy, Math.min(this.maxTransfer, maxExtract));
        if (!simulate) {
            this.storedEnergy -= energyExtracted;
            stack.getOrCreateTag().putInt("energy", this.storedEnergy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return this.storedEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(this.getEnergyStored());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Invalid tag type for deserialization");
        this.storedEnergy = intNbt.getAsInt();
    }
}
