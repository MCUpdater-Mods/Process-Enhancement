package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.mculib.inventory.MachineOutputSlot;
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

public class SawmillMenu extends PowerTrackingMenu {
    private final SawmillEntity localBlockEntity;
    private final Player player;
    private final IItemHandler playerInventory;
    private final ContainerData data;

    public SawmillMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data) {
        super(Registration.SAWMILL_MENU.get(), windowId);
        this.localBlockEntity = level.getBlockEntity(blockPos) instanceof SawmillEntity ? (SawmillEntity) level.getBlockEntity(blockPos) : null;
        this.tileEntity = this.localBlockEntity;
        this.player = player;
        this.playerInventory = new InvWrapper(inventory);
        this.data = data;

        if (this.localBlockEntity != null) {
            addSlot(new MachineInputSlot(this.localBlockEntity, new InvWrapper(this.localBlockEntity), 0, 62, 37));
            addSlot(new MachineOutputSlot(this.localBlockEntity, new InvWrapper(this.localBlockEntity), 1, 98, 37));
        }
        layoutPlayerInventorySlots(8,84);
        trackPower();
        addDataSlots(data);
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
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(localBlockEntity.getLevel(), localBlockEntity.getBlockPos()), player, Registration.SAWMILL_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            if (index == 0 || index == 1) { // Input slot (0) or Output slot (1)
                if (!this.moveItemStackTo(stackInSlot, 2,38, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots
                if (this.localBlockEntity.canPlaceItem(0, stackInSlot)) { // Insert fuel
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 2 && index < 29) { // Move to hotbar
                    if (!this.moveItemStackTo(stackInSlot, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 29 && index < 38 && !this.moveItemStackTo(stackInSlot, 2, 29, false)) { // Move to inventory
                    return ItemStack.EMPTY;
                }
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

    public SawmillEntity getBlockEntity() {
        return localBlockEntity;
    }

    public boolean isWorking() {
        return this.data.get(0) > 0;
    }

    public int getWorkProgress() {
        int maxWork = this.data.get(1);
        if (maxWork == 0) {
            maxWork = 200;
        }
        return this.data.get(0) * 18 / maxWork;
    }
}
