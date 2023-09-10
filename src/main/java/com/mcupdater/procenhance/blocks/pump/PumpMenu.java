package com.mcupdater.procenhance.blocks.pump;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Map;

public class PumpMenu extends PowerTrackingMenu implements IConfigurableMenu {
    private final Map<Direction, Component> adjacentNames;
    private final Player player;
    private final IItemHandler playerInventory;

    public PumpMenu(int windowId, Level level, BlockPos worldPosition, Inventory playerInventory, Player player, Map<Direction, Component> adjacentNames) {
        super(Registration.PUMP_MENU.get(), windowId);
        this.tileEntity = level.getBlockEntity(worldPosition) instanceof PumpEntity ? (PumpEntity) level.getBlockEntity(worldPosition) : null;
        this.adjacentNames = adjacentNames;
        this.player = player;
        this.playerInventory = new InvWrapper(playerInventory);

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
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        //TODO
        return null;
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
