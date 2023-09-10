package com.mcupdater.procenhance.blocks.deconstructor;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.mculib.inventory.MachineOutputSlot;
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

public class DeconstructorMenu extends AbstractMachineMenu<DeconstructorEntity> {
    public DeconstructorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> adjacentNames) {
        super((DeconstructorEntity) level.getBlockEntity(blockPos), Registration.DECONSTRUCTOR_MENU.get(), windowId, level, blockPos, inventory, player, data, adjacentNames);
    }

    @Override
    protected void addMachineSlots() {
        ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getConfigMap().get("items");
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(),0,44,37));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),1,80,19));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),2,98,19));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),3,116,19));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),4,80,37));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),5,98,37));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),6,116,37));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),7,80,55));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),8,98,55));
        this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(),9,116,55));
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
            if (index > 9) {
                if (resourceHandler.canPlaceItem(0, stackInSlot) || resourceHandler.canPlaceItem(1, stackInSlot) || resourceHandler.canPlaceItem(2, stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 10 && index < 37) {
                    if (!this.moveItemStackTo(stackInSlot, 37, 46, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 37 && index < 46 && !this.moveItemStackTo(stackInSlot, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stackInSlot, 10, 46, true)) {
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
