package com.mcupdater.procenhance.blocks.lava_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
import com.mcupdater.mculib.inventory.BucketSlot;
import com.mcupdater.mculib.inventory.FuelSlot;
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

public class LavaGeneratorMenu extends PowerTrackingMenu implements IConfigurableMenu {
    private final LavaGeneratorEntity localBlockEntity;
    private final Player player;
    private final IItemHandler playerInventory;
    private final ContainerData data;
    private final Map<Direction, Component> adjacentNames;

    public LavaGeneratorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> adjacentNames) {
        super(Registration.LAVAGENERATOR_MENU.get(), windowId);
        this.adjacentNames = adjacentNames;
        this.localBlockEntity = level.getBlockEntity(blockPos) instanceof LavaGeneratorEntity ? (LavaGeneratorEntity) level.getBlockEntity(blockPos) : null;
        this.tileEntity = this.localBlockEntity;
        this.player = player;
        this.playerInventory = new InvWrapper(inventory);
        this.data = data;

        if (this.localBlockEntity != null) {
            addSlot(new BucketSlot(new InvWrapper(this.localBlockEntity.getInventory()), 0, 81, 56));
            addSlot(new BucketSlot(new InvWrapper(this.localBlockEntity.getInventory()), 1, 105, 56));
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
            if (index == 0 || index == 1) { // Fuel slot (0) or bucket output slot (1)
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

    public boolean isFueled() {
        return this.data.get(0) > 0;
    }

    public int getBurnProgress() {
        int maxBurn = this.data.get(1);
        if (maxBurn == 0) {
            maxBurn = 200;
        }
        return this.data.get(0) * 13 / maxBurn;
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