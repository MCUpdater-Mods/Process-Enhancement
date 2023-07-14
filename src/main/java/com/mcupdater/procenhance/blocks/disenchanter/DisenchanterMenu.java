package com.mcupdater.procenhance.blocks.disenchanter;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class DisenchanterMenu extends AbstractMachineMenu<DisenchanterEntity> {

    public DisenchanterMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> directionComponentMap) {
        super((DisenchanterEntity) level.getBlockEntity(blockPos), Registration.DISENCHANTER_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
    }

    @Override
    protected void addMachineSlots() {
        ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getConfigMap().get("items");
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 0, 41, 37));
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 2, 62, 26));
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 1, 62, 45));
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 3, 98, 26));
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 4, 98, 45));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemResourceHandler resourceHandler = (ItemResourceHandler)this.machineEntity.getConfigMap().get("items");
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            if (index != 0 && index != 1 && index != 2) {
                if (resourceHandler.canPlaceItem(0, stackInSlot) || resourceHandler.canPlaceItem(1, stackInSlot) || resourceHandler.canPlaceItem(2, stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 5 && index < 32) {
                    if (!this.moveItemStackTo(stackInSlot, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 32 && index < 41 && !this.moveItemStackTo(stackInSlot, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stackInSlot, 5, 41, true)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }
}
