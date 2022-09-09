package com.mcupdater.procenhance.blocks.battery;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public abstract class BatteryEntity extends AbstractConfigurableBlockEntity {
    public ContainerData data = new SimpleContainerData(0);

    public BatteryEntity(BlockEntityType<?> pBlockEntityType, BlockPos pPos, BlockState pState) {
        super(pBlockEntityType, pPos, pState);
    }

    protected void setup(int transferRate) {
        EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, 50 * transferRate, transferRate, false);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 1, new int[]{0}, new int[]{0}, new int[]{0}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        itemResourceHandler.setExtractFunction(this::canTakeItem);
        this.configMap.put("power", energyResourceHandler);
        this.configMap.put("items", itemResourceHandler);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        if (!this.level.isClientSide) {
            // Charge item in slot
            if (!itemStorage.getItem(0).isEmpty()) {
                chargeItem(itemStorage.getItem(0));
            }
            // Update BlockState
            int powerLevel = (int) Math.round(((double)energyStorage.getStoredEnergy() / (double)energyStorage.getCapacity()) * 4.0d);
            int currentState = pBlockState.getValue(BatteryBlock.CHARGE_LEVEL);
            if (powerLevel != currentState) {
                pBlockState = pBlockState.setValue(BatteryBlock.CHARGE_LEVEL, powerLevel);
                pLevel.setBlock(pPos, pBlockState, 3);
            }
        }
        super.tick();
    }

    private void chargeItem(ItemStack itemStack) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        if (itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
            IEnergyStorage itemEnergyHandler = itemStack.getCapability(CapabilityEnergy.ENERGY).resolve().get();
            if (itemEnergyHandler.canReceive()) {
                int energyTransferred = itemEnergyHandler.receiveEnergy(Math.min(2000,energyStorage.getInternalHandler().getEnergyStored()),false);
                energyStorage.getInternalHandler().extractEnergy(energyTransferred,false);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BatteryMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public boolean canPlaceItem(int slot, ItemStack pStack) {
        return pStack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    public boolean canTakeItem(int slot, ItemStack pStack) {
        if (!pStack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
            return true;
        } else {
            IEnergyStorage itemEnergyHandler = pStack.getCapability(CapabilityEnergy.ENERGY).resolve().get();
            return itemEnergyHandler.getEnergyStored() == itemEnergyHandler.getMaxEnergyStored() || !itemEnergyHandler.canReceive();
        }
    }

    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }

}
