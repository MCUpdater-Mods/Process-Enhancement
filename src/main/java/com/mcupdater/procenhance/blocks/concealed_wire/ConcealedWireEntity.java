package com.mcupdater.procenhance.blocks.concealed_wire;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.grid.Endpoint;
import com.mcupdater.procenhance.grid.GridManager;
import com.mcupdater.procenhance.grid.INodeHolder;
import com.mcupdater.procenhance.grid.Node;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ConcealedWireEntity extends BlockEntity implements INodeHolder {
	public BlockState mimic;
	public BlockState next;
	protected UUID nodeId;
	private int revalidateTick;

	public ConcealedWireEntity(BlockPos pos, BlockState state) {
		super(Registration.CONCEALEDWIRE_ENTITY.get(), pos, state);
		this.mimic = this.getDefaultBlockState();
	}

	public ConcealedWireEntity(BlockEntityType<? extends ConcealedWireEntity> entityType, BlockPos pos, BlockState state) {
		super(entityType, pos, state);
	}

	public Node getNode() {
		return GridManager.getNode(this.nodeId);
	}

	@Override
	public void validateOnNextTick() {
		this.revalidateTick = 1;
	}

	public boolean updateBlock() {
		if (this.level != null) {
			BlockState state = this.level.getBlockState(this.worldPosition);
			this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
			this.setChanged();
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		if (this.mimic != null) tag.put("mimic", NbtUtils.writeBlockState(this.mimic));
		if (this.nodeId != null) tag.put("nodeId", NbtUtils.createUUID(this.nodeId));
		super.saveAdditional(tag, registries);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("nodeId")) this.nodeId = NbtUtils.loadUUID(tag.get("nodeId"));
		HolderGetter<Block> holderGetter = (this.level != null ? this.level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup());
		if (tag.contains("mimic")) {
			this.mimic = NbtUtils.readBlockState(holderGetter, tag.getCompound("mimic"));
		} else {
			this.mimic = this.getDefaultBlockState();
		}
	}

	private BlockState getDefaultBlockState() {
		return Registration.CONCEALEDWIRE_BLOCK.get().defaultBlockState();
	}

	@Override
	public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
		super.onDataPacket(net, pkt, lookupProvider);
		this.handleUpdateTag(pkt.getTag(), lookupProvider);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag, registries);
		return tag;
	}

	public void tick(Level level, BlockPos pos) {
		if (!this.level.isClientSide && GridManager.isLoaded()) {
			Node localNode = this.getNode();
			if (this.nodeId != null && localNode != null) { // If we have a nodeId and it is still valid, validate the grid
				if (!localNode.getRawGrid().isValid()) {
					if (localNode.getRawGrid().getReplacedBy() != null) {
						localNode.setGrid(localNode.getRawGrid().getReplacedBy());
					} else {
						localNode.validate();
					}
					for (Direction side : Direction.values()) {
						if (this.level.getBlockEntity(this.worldPosition.relative(side)) instanceof INodeHolder holder) {
							if (holder.getNode() != null && !holder.getNode().getRawGrid().getGridId().equals(this.nodeId)) {
								// Invalid Grid State!
								holder.getNode().validate();
							}
						}
					}
				}
			} else { // ...otherwise (re)build the node
				GridManager.getInstance((ServerLevel) level);
				Set<Node> neighbors = new HashSet<>();
				Set<Endpoint> endpoints = new HashSet<>();
				Arrays.stream(Direction.values()).forEach(side -> {
					if (this.level.getBlockEntity(this.worldPosition.relative(side)) instanceof INodeHolder nodeHolder) {
						if (nodeHolder.getNode() != null) {
							neighbors.add(nodeHolder.getNode());
						} else {
							ProcessEnhancement.LOGGER.error("INodeHolder missing Node at {}",this.worldPosition.relative(side));
						}
					} else {
						IEnergyStorage energyCap = this.level.getCapability(Capabilities.EnergyStorage.BLOCK, this.worldPosition.relative(side), side.getOpposite());
						if (energyCap != null) {
							Endpoint.Type type = (energyCap.canExtract() && energyCap.canReceive() ? Endpoint.Type.STORAGE : energyCap.canExtract() ? Endpoint.Type.SOURCE : energyCap.canReceive() ? Endpoint.Type.SINK : null);
							if (type != null) {
								endpoints.add(new Endpoint(level.dimension().location().toString(), this.worldPosition.relative(side), side, type));
							}
						}
					}
				});
				Node newNode = new Node(worldPosition, level, neighbors, endpoints);
				this.nodeId = newNode.getNodeId();
				this.revalidateTick = 300;
				ProcessEnhancement.LOGGER.info("Set node successfully");
			}
			if (revalidateTick <= 0 && localNode != null) {
				//ProcessEnhancement.LOGGER.debug("Random check at {} - NodeId {}", localNode.getPos(),this.nodeId);
				localNode.clearEndpoints();
				for (Direction side : Direction.values()) {
					if (level.getBlockEntity(this.worldPosition.relative(side)) instanceof INodeHolder holder) {
						if (holder.getNode() != null) {
							localNode.addNeighbor(holder.getNode());
						} else {
							ProcessEnhancement.LOGGER.error("INodeHolder missing Node at {}",this.worldPosition.relative(side));
						}
					} else {
						IEnergyStorage energyCap = this.level.getCapability(Capabilities.EnergyStorage.BLOCK, this.worldPosition.relative(side), side.getOpposite());
						if (energyCap != null) {
							Endpoint.Type type = (energyCap.canExtract() && energyCap.canReceive() ? Endpoint.Type.STORAGE : energyCap.canExtract() ? Endpoint.Type.SOURCE : energyCap.canReceive() ? Endpoint.Type.SINK : null);
							if (type != null) {
								localNode.addEndpoint(new Endpoint(level.dimension().location().toString(), this.worldPosition.relative(side), side, type));
							}
						}
					}
				}
				localNode.updateGridEndpoints();
				localNode.validate();
				revalidateTick += level.getRandom().nextInt(100,300);
			}
			revalidateTick--;
		}
	}

	@Override
	public void onRemove() {
		if (this.nodeId != null) {
			Node node = GridManager.getInstance().getNodeById(this.nodeId);
			if (node != null) {
				node.invalidate();
			} else {
				ProcessEnhancement.LOGGER.error("Unable to find node by ID!");
			}
		} else {
			ProcessEnhancement.LOGGER.error("Wire removed without nodeId!");
		}
	}
}
