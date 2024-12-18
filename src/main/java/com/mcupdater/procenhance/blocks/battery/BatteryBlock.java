package com.mcupdater.procenhance.blocks.battery;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.component.DataComponents.CUSTOM_NAME;

public abstract class BatteryBlock extends AbstractMachineBlock {
    public static final IntegerProperty CHARGE_LEVEL = IntegerProperty.create("charge",0,4);

    public static BlockBehaviour.Properties defaultProperties() {
        return Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL)
                .strength(10.0f)
                .requiresCorrectToolForDrops();
    }

    public BatteryBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(ACTIVE, false)
                        .setValue(CHARGE_LEVEL,0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CHARGE_LEVEL);
    }

    @Override
    public void onRemove(BlockState pOldState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pOldState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

            if (blockEntity instanceof BatteryEntity batteryEntity) {
                Containers.dropContents(pLevel, pPos, batteryEntity.getInventory());
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
            super.onRemove(pOldState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel,pPos,pState,pPlacer,pStack);
        @Nullable IEnergyStorage energyStorage = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyStorage != null) {
            if (pLevel.getBlockEntity(pPos) instanceof BatteryEntity batteryEntity) {
                batteryEntity.getEnergyStorage().setEnergy(energyStorage.getEnergyStored());
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof BatteryEntity capacitor) {
                capacitor.tick(lvl, pos, state);
            }
        };
    }

}
