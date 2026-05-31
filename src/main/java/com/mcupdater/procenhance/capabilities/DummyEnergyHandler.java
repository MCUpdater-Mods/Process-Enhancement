package com.mcupdater.procenhance.capabilities;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class DummyEnergyHandler implements IEnergyStorage {
	public static final DummyEnergyHandler INSTANCE = new DummyEnergyHandler();

	@Override
	public int receiveEnergy(int toReceive, boolean simulate) {
		return toReceive;
	}

	@Override
	public int extractEnergy(int toExtract, boolean simulate) {
		return toExtract;
	}

	@Override
	public int getEnergyStored() {
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		return 0;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
}
