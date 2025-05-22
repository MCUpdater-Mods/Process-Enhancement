package com.mcupdater.procenhance.blocks.planter;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.PhantomSlot;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.neoforged.neoforge.common.SpecialPlantable;

import java.util.Map;
import java.util.stream.IntStream;

public class PlanterMenu extends AbstractMachineMenu<PlanterEntity> {

	public PlanterMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
		super((PlanterEntity) level.getBlockEntity(blockPos), Registration.PLANTER_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
	}

	public static PlanterMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		PlanterEntity te = (PlanterEntity) world.getBlockEntity(pos);
		return new PlanterMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
	}

	@Override
	protected void addMachineSlots() {
		ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getInventory();
		addSlotRange(resourceHandler.getInternalHandler(), 0, 8, 51, 9, 18);
		addPhantomSlotRange(this.machineEntity.getInventory(), 9, 8, 32, 9, 18);
	}

	private int addPhantomSlotRange(Container container, int index, int x, int y, int amount, int dx) {
		for(int i = 0; i < amount; ++i) {
			this.addSlot(new PhantomSlot(container, index, x, y, this::isPlantable));
			x += dx;
			++index;
		}

		return index;
	}

	private boolean isPlantable(int unused, ItemStack itemStack) {
		return itemStack.getItem() instanceof SpecialPlantable || (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BushBlock);
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		int invOffset = 18;
		int hotbarOffset = invOffset + 27;
		ItemStack itemstack = ItemStack.EMPTY;
		ItemResourceHandler resourceHandler = (ItemResourceHandler)this.machineEntity.getResourceHandler("items");
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack stackInSlot = slot.getItem();
			itemstack = stackInSlot.copy();
			if (index > invOffset) { // From seed slots
				if (!this.moveItemStackTo(stackInSlot, 0, 9, false)) {
					return ItemStack.EMPTY;
				}
			} else if (IntStream.rangeClosed(9,18).anyMatch(value -> value == index)) { // From filter phantom slots
				return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(stackInSlot, 2, 38, true)) { // From inventory
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
