package com.mcupdater.procenhance.blocks.biogenerator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Map;

public class BiogeneratorMenu extends PowerTrackingMenu implements IConfigurableMenu {
    private final BiogeneratorEntity localBlockEntity;
    private final Player player;
    private final IItemHandler playerInventory;
    private final ContainerData data;
    private final Map<Direction, Component> adjacentNames;

    public BiogeneratorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> adjacentNames) {
        super(Registration.BIOGENERATOR_MENU.get(), windowId);
        this.adjacentNames = adjacentNames;
        this.localBlockEntity = level.getBlockEntity(blockPos) instanceof BiogeneratorEntity ? (BiogeneratorEntity) level.getBlockEntity(blockPos) : null;
        this.tileEntity = this.localBlockEntity;
        this.player = player;
        this.playerInventory = new InvWrapper(inventory);
        this.data = data;

        if (this.localBlockEntity != null) {
            addSlot(new SlotItemHandler(new InvWrapper(this.localBlockEntity.getInventory()), 0, 8, 15));
            addSlot(new SlotItemHandler(new InvWrapper(this.localBlockEntity.getInventory()), 1, 8, 34));
            addSlot(new SlotItemHandler(new InvWrapper(this.localBlockEntity.getInventory()), 2, 8, 53));
        }
        layoutPlayerInventorySlots(8, 84);
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
        return ContainerLevelAccess.create(localBlockEntity.getLevel(), localBlockEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            if (index == 0 || index == 1 || index == 2) { // Fuel slot (0) or bucket output slot (1)
                if (!this.moveItemStackTo(stackInSlot, 3,39, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots
                if (this.localBlockEntity.canPlaceItem(0, stackInSlot)) { // Insert fuel
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (this.localBlockEntity.canPlaceItem(1, stackInSlot)) { // Insert fuel
                    if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (this.localBlockEntity.canPlaceItem(2, stackInSlot)) { // Insert fuel
                    if (!this.moveItemStackTo(stackInSlot, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (index >= 3 && index < 30) { // Move to hotbar
                    if (!this.moveItemStackTo(stackInSlot, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(stackInSlot, 3, 30, false)) { // Move to inventory
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

    public boolean isBioFueled() {
        return this.data.get(0) > 0;
    }

    public int getBioFill() {
        return (this.data.get(0) * 97) / BiogeneratorEntity.BIO_MAX;
    }

    public boolean isCopperFueled() {
        return this.data.get(1) > 0;
    }

    public int getCopperFill() {
        return (this.data.get(1) * 97) / BiogeneratorEntity.COPPER_MAX;
    }

    public boolean isGunpowderFueled() {
        return this.data.get(2) > 0;
    }

    public int getGunpowderFill() {
        return (this.data.get(2) * 97) / BiogeneratorEntity.GUNPOWDER_MAX;
    }

    @Override
    public AbstractConfigurableBlockEntity getBlockEntity() {
        return this.localBlockEntity;
    }

    @Override
    public Component getSideName(Direction direction) {
        return this.adjacentNames.get(direction);
    }
}