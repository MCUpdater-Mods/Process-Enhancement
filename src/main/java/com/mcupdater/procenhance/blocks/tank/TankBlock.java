package com.mcupdater.procenhance.blocks.tank;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

public abstract class TankBlock extends AbstractMachineBlock {
    public TankBlock(Properties properties) {
        super(properties);
    }

    public static Properties defaultProperties() {
        return Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL)
                .strength(10.0f, 200.0f)
                .requiresCorrectToolForDrops();
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        IFluidHandlerItem fluidStorage = pStack.getCapability(Capabilities.FluidHandler.ITEM);
        if (fluidStorage != null) {
            if (pLevel.getBlockEntity(pPos) instanceof TankEntity tankEntity) {
                for (int tank = 0; tank < fluidStorage.getTanks(); tank++) {
                    tankEntity.getFluidHandler().getInternalHandler().fill(fluidStorage.getFluidInTank(tank), IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean flag) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof TankEntity tankEntity) {
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, flag);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof TankEntity tank) {
                tank.tick(lvl, pos, state);
            }
        };
    }
}
