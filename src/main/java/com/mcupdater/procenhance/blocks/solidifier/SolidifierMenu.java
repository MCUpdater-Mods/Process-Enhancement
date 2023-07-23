package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
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
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Map;

public class SolidifierMenu extends AbstractMachineMenu<AbstractSolidifierEntity> {

    public SolidifierMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> adjacentNames) {
        super((AbstractSolidifierEntity) level.getBlockEntity(blockPos), Registration.SOLIDIFIER_MENU.get(), windowId, level, blockPos, inventory, player, data, adjacentNames);
    }

    @Override
    protected void addMachineSlots() {
        ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getConfigMap().get("items");
        addSlot(new MachineOutputSlot(this.machineEntity, new InvWrapper(this.machineEntity.getInventory()), 0, 81, 56));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();
            if (index == 0) { // Output slot
                if (!this.moveItemStackTo(stackInSlot, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots
                if (!this.moveItemStackTo(stackInSlot, 28, 37, false)) { // Move to hotbar
                    return ItemStack.EMPTY;
                } else if (index >= 28 && index < 37 && !this.moveItemStackTo(stackInSlot, 1, 28, false)) { // Move to inventory
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }
        return itemStack;
    }
}
