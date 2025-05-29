package com.mcupdater.procenhance.blocks.solar_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.capabilities.PowerTrackingMenu;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.blocks.generator.GeneratorEntity;
import com.mcupdater.procenhance.blocks.generator.GeneratorMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.Map;

public class SolarMenu extends PowerTrackingMenu implements IConfigurableMenu {
	private final SolarEntity localBlockEntity;
	private final Player player;
	private final IItemHandler playerInventory;
	private final Map<Direction, String> adjacentNames;

	public SolarMenu(int containerId, Level level, BlockPos blockPos, Inventory inventory, Player player, Map<Direction, String> adjacentNames) {
		super(Registration.SOLARGENERATOR_MENU.get(), containerId);
		this.adjacentNames = adjacentNames;
		this.localBlockEntity = level.getBlockEntity(blockPos) instanceof SolarEntity ? (SolarEntity) level.getBlockEntity(blockPos) : null;
		this.tileEntity = this.localBlockEntity;
		this.player = player;
		this.playerInventory = new InvWrapper(inventory);

		layoutPlayerInventorySlots(8, 84);
		trackPower();
	}

	public static SolarMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		SolarEntity te = (SolarEntity) world.getBlockEntity(pos);
		return new SolarMenu(containerId, world, pos, playerInv, playerInv.player, DataHelper.readDirectionMap(extraData));
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
	public AbstractConfigurableBlockEntity getBlockEntity() {
		return this.localBlockEntity;
	}

	@Override
	public String getSideName(Direction direction) {
		return this.adjacentNames.get(direction);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stackInSlot = slot.getItem();
			itemstack = stackInSlot.copy();
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
}
