package com.mcupdater.procenhance.blocks.crude_generator;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Map;

public class CrudeGeneratorMenu extends PowerTrackingMenu implements IConfigurableMenu {
    private final CrudeGeneratorEntity localBlockEntity;
    private final Player player;
    private final IItemHandler playerInventory;
    private final Map<Direction, Component> adjacentNames;

    public CrudeGeneratorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, Map<Direction, Component> adjacentNames) {
        super(Registration.CRUDEGENERATOR_MENU.get(), windowId);
        this.adjacentNames = adjacentNames;
        this.localBlockEntity = level.getBlockEntity(blockPos) instanceof CrudeGeneratorEntity ? (CrudeGeneratorEntity) level.getBlockEntity(blockPos) : null;
        this.tileEntity = this.localBlockEntity;
        this.player = player;
        this.playerInventory = new InvWrapper(inventory);

        layoutPlayerInventorySlots(8, 84);
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
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(localBlockEntity.getLevel(), localBlockEntity.getBlockPos()), player, Registration.CRUDEGENERATOR_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            // Player inventory slots
            if (index >= 0 && index < 27) { // Move to hotbar
                if (!this.moveItemStackTo(stackInSlot, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 27 && index < 36 && !this.moveItemStackTo(stackInSlot, 0, 27, false)) { // Move to inventory
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

    @Override
    public BlockEntity getBlockEntity() {
        return this.localBlockEntity;
    }

    @Override
    public Component getSideName(Direction direction) {
        return this.adjacentNames.get(direction);
    }

}