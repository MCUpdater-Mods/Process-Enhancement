package com.mcupdater.procenhance.blocks.pump;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.FluidResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PumpEntity extends AbstractMachineBlockEntity {

    private final int range;
    private final FluidResourceHandler fluidResourceHandler;
    private List<BlockPos> fluidBlocks = new ArrayList<>();
    private int tick = 0;

    public PumpEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int multiplier, int range) {
        super(blockEntityType, blockPos, blockState, Config.PUMP_ENERGY_PER_TICK.get() * 1000 * multiplier, Integer.MAX_VALUE, Config.PUMP_ENERGY_PER_TICK.get(), multiplier);
        this.range = range;
        fluidResourceHandler = new FluidResourceHandler(this.level, player -> true);
        fluidResourceHandler.addTank(new FluidTank(5000 * multiplier, fluidStack -> true), false, true);
        this.configMap.put("fluids", fluidResourceHandler);
    }

    public void initialize() {
        int initx = this.worldPosition.getX();
        int inity = this.worldPosition.getY();
        int initz = this.worldPosition.getZ();
        for (int y = inity; (y > (inity - (range * 2))); y--) {
            for (int x = (initx - range); (x <= (initx + range)); x++) {
                for (int z = (initz - range); (z <= (initz + range)); z++) {
                    BlockPos blockPos = new BlockPos(x,y,z);
                    BlockState state = level.getBlockState(blockPos);
                    //ProcessEnhancement.LOGGER.debug("Pos: {}  State: {}  FluidBlock? {}  FluidState: {}  Source: {}", blockPos, state, state.getBlock() instanceof IFluidBlock, state.getFluidState(), state.getFluidState().isSource());
                    if (((state.getBlock() instanceof IFluidBlock) || (state.getBlock() instanceof BucketPickup)) && state.getFluidState().isSource()) {
                        fluidBlocks.add(blockPos);
                    }
                }
            }
        }
        ProcessEnhancement.LOGGER.debug("Blocks found: {}", fluidBlocks.size());
/*
        level.getBlockStates(new AABB((double)(initx - range), (double)inity, (double)(initz - range), (double)(initx + range), (double)(inity - (range*2)), (double)(initz + range))).forEach(blockState -> {
            if (blockState.getBlock() instanceof IFluidBlock && blockState.getFluidState().isSource()) {
                fluidBlocks.add(blockState);
            }
        });
 */
    }

    @Override
    protected boolean performWork() {
        if (this.fluidResourceHandler.getInternalHandler().getFluidInTank(0).getAmount() < this.fluidResourceHandler.getInternalHandler().getTankCapacity(0)) {
            if (tick == 0) {
                ProcessEnhancement.LOGGER.debug("Fluid Blocks: {}", this.fluidBlocks.size());
                if (this.fluidBlocks.isEmpty()) {
                    initialize();
                }
                Fluid filter;
                if (!this.fluidBlocks.isEmpty()) {
                    if (!this.fluidResourceHandler.getInternalHandler().getFluidInTank(0).isEmpty()) {
                        filter = this.fluidResourceHandler.getInternalHandler().getFluidInTank(0).getFluid();
                    } else {
                        filter = null;
                    }
                    List<BlockPos> tempFluids = this.fluidBlocks.stream().filter(blockPos -> {
                        BlockState blockState = level.getBlockState(blockPos);
                        return (blockState.getFluidState().isSource() && (filter != null ? blockState.getFluidState().is(filter) : true));
                    }).toList();
                    ProcessEnhancement.LOGGER.debug("Temp list size: {}", tempFluids.size());
                    //Collections.shuffle(tempFluids);
                    if (!tempFluids.isEmpty()) {
                        BlockPos blockPos = tempFluids.get(0);
                        BlockState state = level.getBlockState(blockPos);
                        if (state.getBlock() instanceof IFluidBlock fluidBlock) {
                            FluidStack testFluid = fluidBlock.drain(level, blockPos, IFluidHandler.FluidAction.SIMULATE);
                            if (fluidResourceHandler.getInternalHandler().fill(testFluid, IFluidHandler.FluidAction.SIMULATE) == testFluid.getAmount()) {
                                fluidResourceHandler.getInternalHandler().fill(((IFluidBlock) state.getBlock()).drain(level, blockPos, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                                this.fluidBlocks.remove(blockPos);
                                tick += 200;
                                return true;
                            }
                        } else if (state.getBlock() instanceof BucketPickup bucketBlock) {
                            Fluid fluid = state.getFluidState().getType();
                            if (state.getFluidState().isSource() && (fluidResourceHandler.getInternalHandler().getFluidInTank(0).isEmpty() || fluid.isSame(this.fluidResourceHandler.getInternalHandler().getFluidInTank(0).getFluid()))) {
                                FluidStack fluidStack = new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME);
                                int val = this.fluidResourceHandler.getInternalHandler().forceFill(0, fluidStack, IFluidHandler.FluidAction.SIMULATE);
                                if (val == fluidStack.getAmount()) {
                                    if (!bucketBlock.pickupBlock(level, blockPos, state).isEmpty()) {
                                        this.fluidResourceHandler.getInternalHandler().forceFill(0, fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                        this.fluidBlocks.remove(blockPos);
                                        tick += 200;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    tick += 200;
                } else {
                    tick -= 4000;
                    return false;
                }
            } else if (tick > 0) {
                tick--;
                return true;
            } else {
                tick++;
                return false;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (this.name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        super.saveAdditional(compound);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new PumpMenu(windowId, this.level, this.worldPosition, inventory, player, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
