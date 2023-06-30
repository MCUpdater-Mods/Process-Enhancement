package com.mcupdater.procenhance.blocks.miner;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Map;

public class MinerMenu extends PowerTrackingMenu implements IConfigurableMenu {
    private final Map<Direction, Component> adjacentNames;
    private final Player player;
    private final IItemHandler playerInventory;
    private final MinerEntity localBlockEntity;

    public MinerMenu(int windowId, Level level, BlockPos worldPosition, Inventory playerInventory, Player player, Map<Direction, Component> adjacentNames) {
        super(Registration.MINER_MENU.get(), windowId);
        this.localBlockEntity = level.getBlockEntity(worldPosition) instanceof MinerEntity ? (MinerEntity) level.getBlockEntity(worldPosition) : null;
        this.tileEntity = localBlockEntity;
        this.adjacentNames = adjacentNames;
        this.player = player;
        this.playerInventory = new InvWrapper(playerInventory);

        addSlotBox(new InvWrapper(this.tileEntity.getInventory()), 0, 62,26, 3, 18, 2, 18);
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
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            if (index >= 0 && index <= 5) {
                if (!this.moveItemStackTo(stackInSlot, 6,42, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots
                if (index >= 6 && index < 33) { // Move to hotbar
                    if (!this.moveItemStackTo(stackInSlot, 33, 42, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 33 && index < 42 && !this.moveItemStackTo(stackInSlot, 6, 33, false)) { // Move to inventory
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

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.tileEntity != null ? ContainerLevelAccess.create(this.tileEntity.getLevel(), this.tileEntity.getBlockPos()).evaluate((level, blockPos) -> pPlayer.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true) : false;
    }

    @Override
    public AbstractConfigurableBlockEntity getBlockEntity() {
        return this.tileEntity;
    }

    @Override
    public Component getSideName(Direction direction) {
        return this.adjacentNames.get(direction);
    }
}
