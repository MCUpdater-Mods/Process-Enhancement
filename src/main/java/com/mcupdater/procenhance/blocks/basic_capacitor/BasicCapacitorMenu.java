package com.mcupdater.procenhance.blocks.basic_capacitor;

import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BasicCapacitorMenu extends PowerTrackingMenu {
    private final BasicCapacitorEntity localBlockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public BasicCapacitorMenu(int pContainerId, Level level, BlockPos worldPosition, Inventory pPlayerInventory, Player pPlayer) {
        super(Registration.BASICCAPACITOR_MENU.get(), pContainerId);
        this.localBlockEntity = level.getBlockEntity(worldPosition) instanceof BasicCapacitorEntity ? (BasicCapacitorEntity) level.getBlockEntity(worldPosition) : null;
        this.tileEntity = this.localBlockEntity;
        this.player = pPlayer;
        this.playerInventory = new InvWrapper(pPlayerInventory);

        if (this.localBlockEntity != null) {
            addSlot(new SlotItemHandler(new InvWrapper(this.localBlockEntity), 0, 81, 56));
        }
        layoutPlayerInventorySlots(8,84);
        trackPower();
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(localBlockEntity.getLevel(), localBlockEntity.getBlockPos()), player, Registration.BASICCAPACITOR_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();
            if (pIndex == 0) { // Charging slot (0)
                if (!this.moveItemStackTo(stackInSlot, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots
                if (this.localBlockEntity.canPlaceItem(0, stackInSlot)) { // Insert to charging slot
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 1 && pIndex < 28) { // Move to hotbar
                    if (!this.moveItemStackTo(stackInSlot, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 28 && pIndex < 37 && !this.moveItemStackTo(stackInSlot, 1, 28, false)) { // Move to inventory
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

            slot.onTake(pPlayer, stackInSlot);
        }
        return itemStack;
    }
}
