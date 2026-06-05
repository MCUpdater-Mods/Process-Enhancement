package com.mcupdater.procenhance.blocks.copper_wire;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.grid.Endpoint;
import com.mcupdater.procenhance.grid.GridManager;
import com.mcupdater.procenhance.grid.INodeHolder;
import com.mcupdater.procenhance.grid.Node;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CopperWireEntity extends BlockEntity implements INodeHolder {
    //private final EnergyResourceHandler energyStorage;
    private UUID nodeId;
    int revalidateTick;

    public CopperWireEntity(BlockPos pPos, BlockState pState) {
        super(Registration.COPPERWIRE_ENTITY.get(), pPos, pState);

        //this.energyStorage = new EnergyResourceHandler(this.level, 5000, 5000, false);
    }

    @Override
    public Node getNode() {
        return GridManager.getNode(this.nodeId);
    }

    @Override
    public void validateOnNextTick() {
        this.revalidateTick = 1;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (this.nodeId != null) {
            tag.put("nodeId", NbtUtils.createUUID(this.nodeId));
        }
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("nodeId")) {
            this.nodeId = NbtUtils.loadUUID(tag.get("nodeId"));
        }
        super.loadAdditional(tag, registries);
    }


    public void tick(Level pLevel, BlockPos pPos) {
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
                                endpoints.add(new Endpoint(pLevel.dimension().location().toString(), this.worldPosition.relative(side), side, type));
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
                    if (pLevel.getBlockEntity(this.worldPosition.relative(side)) instanceof INodeHolder holder) {
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
                                localNode.addEndpoint(new Endpoint(pLevel.dimension().location().toString(), this.worldPosition.relative(side), side, type));
                            }
                        }
                    }
                }
                localNode.updateGridEndpoints();
                localNode.validate();
                revalidateTick += pLevel.getRandom().nextInt(100,300);
            }
            revalidateTick--;
        }

//        if (this.energyStorage.tickHandler(pLevel, pPos))
//            this.setChanged();
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
/*
    public EnergyResourceHandler getEnergyResourceHandler() {
        return this.energyStorage;
    }
*/
}
