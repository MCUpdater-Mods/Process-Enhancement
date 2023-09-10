package com.mcupdater.procenhance.blocks.biogenerator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.InputOutputSettings;
import com.mcupdater.mculib.inventory.SideSetting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.COPPER_DUST;
import static com.mcupdater.procenhance.setup.Registration.PLANT_DUST;

public abstract class BiogeneratorEntity extends AbstractConfigurableBlockEntity {
    public final static int BIO_MAX = 1000;
    public final static int COPPER_MAX = 5000;
    public final static int GUNPOWDER_MAX = 2000;

    int bioCurrent;
    int copperCurrent;
    int gunpowderCurrent;
    public ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return BiogeneratorEntity.this.bioCurrent;
                case 1:
                    return BiogeneratorEntity.this.copperCurrent;
                case 2:
                    return BiogeneratorEntity.this.gunpowderCurrent;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    BiogeneratorEntity.this.bioCurrent = newValue;
                    break;
                case 1:
                    BiogeneratorEntity.this.copperCurrent = newValue;
                    break;
                case 2:
                    BiogeneratorEntity.this.gunpowderCurrent = newValue;
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    protected int energyPerTick;

    public BiogeneratorEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    protected void setup(int energyPerTick) {
        this.energyPerTick = energyPerTick;
        EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, energyPerTick * 10000, Integer.MAX_VALUE, false);
        for (Direction side : Direction.values()) {
            InputOutputSettings ioSetting = energyResourceHandler.getIOSettings(side);
            ioSetting.setInputSetting(SideSetting.DISABLED);
            energyResourceHandler.updateIOSettings(side, ioSetting);
        }
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 3, new int[]{0,1,2}, new int[]{0,1,2}, new int[]{}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        this.configMap.put("power", energyResourceHandler);
        this.configMap.put("items", itemResourceHandler);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        if (!this.level.isClientSide) {
            if (this.bioCurrent > 0 && this.copperCurrent > 0 && this.gunpowderCurrent > 0) {
                int added = energyStorage.getInternalHandler().receiveEnergy(this.energyPerTick, false);
                if (added > 0) {
                    --this.bioCurrent;
                    --this.copperCurrent;
                    --this.gunpowderCurrent;
                    boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                    if (!currentState) {
                        pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, true);
                        pLevel.setBlock(pPos, pBlockState, 3);
                    }
                } else {
                    boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                    if (currentState) {
                        pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, false);
                        pLevel.setBlock(pPos, pBlockState, 3);
                    }
                }
            } else {
                boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                if (currentState) {
                    pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, false);
                    pLevel.setBlock(pPos, pBlockState, 3);
                }
            }
            if (this.bioCurrent == 0 && !itemStorage.getItem(0).isEmpty()) {
                bioCurrent += BIO_MAX;
                itemStorage.getItem(0).shrink(1);
            }
            if (this.copperCurrent == 0 && !itemStorage.getItem(1).isEmpty()) {
                copperCurrent += COPPER_MAX;
                itemStorage.getItem(1).shrink(1);
            }
            if (this.gunpowderCurrent == 0 && !itemStorage.getItem(2).isEmpty()) {
                gunpowderCurrent += GUNPOWDER_MAX;
                itemStorage.getItem(2).shrink(1);
            }
        }
        super.tick();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.bioCurrent = compound.getInt("bioCurrent");
        this.copperCurrent = compound.getInt("copperCurrent");
        this.gunpowderCurrent = compound.getInt("gunpowderCurrent");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putInt("bioCurrent", this.bioCurrent);
        compound.putInt("copperCurrent", this.copperCurrent);
        compound.putInt("gunpowderCurrent", this.gunpowderCurrent);
        super.saveAdditional(compound);
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        switch(slot) {
            case 0: // Plant Dust
                return stack.getItem().equals(PLANT_DUST.get());
            case 1: // Copper Dust
                return stack.getItem().equals(COPPER_DUST.get());
            case 2: // Gunpowder
                return stack.getItem().equals(Items.GUNPOWDER);
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new BiogeneratorMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }

}
