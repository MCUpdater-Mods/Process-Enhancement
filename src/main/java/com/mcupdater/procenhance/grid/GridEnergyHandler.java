package com.mcupdater.procenhance.grid;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GridEnergyHandler implements IEnergyStorage {

	private final Grid grid;
	private final BlockPos source;
	private final ResourceLocation levelId;

	public GridEnergyHandler(Grid grid, ResourceLocation levelId, BlockPos source) {
		this.grid = grid;
		this.levelId = levelId;
		this.source = source;
	}

	@Override
	public int receiveEnergy(int toReceive, boolean simulate) {
		int availablePower = toReceive;
		if (this.grid == null) {
			return 0;  // Grid invalid, reject energy
		}
		Set<Endpoint> sinks = this.grid.getEndpoints().stream().filter(endpoint -> endpoint.type() == Endpoint.Type.SINK && !(levelId.toString().equals(endpoint.levelId()) && source.equals(endpoint.blockPos()))).collect(Collectors.toSet());
		Set<Endpoint> storages = this.grid.getEndpoints().stream().filter(endpoint -> endpoint.type() == Endpoint.Type.STORAGE && !(levelId.toString().equals(endpoint.levelId()) && source.equals(endpoint.blockPos()))).collect(Collectors.toSet());
		for (Endpoint endpoint : sinks) {
			if (availablePower > 0) {
				Level level = ProcessEnhancement.serverInstance.getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(endpoint.levelId())));
				if (level.getBlockEntity(endpoint.blockPos()) instanceof INodeHolder) {
					ProcessEnhancement.LOGGER.warn("Invalid SINK endpoint detected @ {}", endpoint.blockPos());
				} else {
					//ProcessEnhancement.LOGGER.debug("SINK Endpoint is: {} @ {}", level.getBlockState(endpoint.blockPos()).getBlock(), endpoint.blockPos());
					IEnergyStorage handler = level.getCapability(Capabilities.EnergyStorage.BLOCK, endpoint.blockPos(), endpoint.side().getOpposite());
					if (handler != null) availablePower -= handler.receiveEnergy(availablePower, simulate);
				}
			}
		}
		for (Endpoint endpoint : storages) {
			if (availablePower > 0) {
				Level level = ProcessEnhancement.serverInstance.getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(endpoint.levelId())));
				if (level.getBlockEntity(endpoint.blockPos()) instanceof INodeHolder) {
					ProcessEnhancement.LOGGER.warn("Invalid STORAGE endpoint detected @ {}", endpoint.blockPos());
				} else {
					//ProcessEnhancement.LOGGER.debug("STORAGE Endpoint is: {} @ {}", level.getBlockState(endpoint.blockPos()).getBlock(), endpoint.blockPos());
					IEnergyStorage handler = Objects.requireNonNull(ProcessEnhancement.serverInstance.getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(endpoint.levelId())))).getCapability(Capabilities.EnergyStorage.BLOCK, endpoint.blockPos(), endpoint.side().getOpposite());
					if (handler != null) availablePower -= handler.receiveEnergy(availablePower, simulate);
				}
			}
		}
		return toReceive - availablePower;
	}

	@Override
	public int extractEnergy(int toExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
}
