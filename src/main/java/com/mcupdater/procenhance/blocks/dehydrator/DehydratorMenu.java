package com.mcupdater.procenhance.blocks.dehydrator;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.procenhance.recipe.DehydratorRecipe;
import com.mcupdater.procenhance.recipe.HydratorRecipe;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Map;

public class DehydratorMenu extends AbstractMachineMenu<DehydratorEntity> {
	private final Container transientSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			DehydratorMenu.this.slotsChanged(this);
		}
	};

	public DehydratorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> directionComponentMap) {
		super((DehydratorEntity) level.getBlockEntity(blockPos), Registration.DEHYDRATOR_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
		this.addSlot(new Slot(this.transientSlots, 0, 25, 16) {
			@Override
			public boolean mayPlace(ItemStack pStack) {
				if (pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
					IFluidHandler tankFluidHandler = DehydratorMenu.this.tileEntity.getFluidHandler();
					IFluidHandlerItem itemFluidHandler = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
					return tankFluidHandler.isFluidValid(0, itemFluidHandler.getFluidInTank(0)) || itemFluidHandler.getFluidInTank(0).isEmpty();
				}
				return false;
			}
		});
		this.addSlot(new Slot(this.transientSlots, 1, 25,51){
			@Override
			public boolean mayPlace(ItemStack pStack) {
				return false;
			}
		});
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
	}

	@Override
	public void removed(Player pPlayer) {
		super.removed(pPlayer);
		ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()).execute((level, blockPos) -> {
			this.clearContainer(pPlayer, this.transientSlots);
		});
	}

	@Override
	public void slotsChanged(Container pContainer) {
		super.slotsChanged(pContainer);
		if (pContainer == this.transientSlots) {
			if (!this.transientSlots.getItem(0).isEmpty() && this.transientSlots.getItem(1).isEmpty() && this.transientSlots.getItem(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
				IFluidHandlerItem itemFluidHandler = this.transientSlots.getItem(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
				IFluidHandler tankFluidHandler = DehydratorMenu.this.tileEntity.getFluidHandler();
				if (itemFluidHandler.getFluidInTank(0).isEmpty()) {
					// Fill item
					FluidStack testFluidStack = tankFluidHandler.drain(tankFluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
					int fillAmount = itemFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.SIMULATE);
					if (fillAmount > 0) {
						itemFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.EXECUTE);
						tankFluidHandler.drain(fillAmount, IFluidHandler.FluidAction.EXECUTE);
						this.transientSlots.setItem(1, itemFluidHandler.getContainer());
						this.transientSlots.setItem(0, ItemStack.EMPTY);
					}
				} else {
					if (itemFluidHandler.getFluidInTank(0).getFluid().isSame(tankFluidHandler.getFluidInTank(0).getFluid()) || tankFluidHandler.getFluidInTank(0).isEmpty()) {
						// Drain item
						FluidStack testFluidStack = itemFluidHandler.drain(itemFluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
						int fillAmount = tankFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.SIMULATE);
						if (fillAmount > 0) {
							tankFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.EXECUTE);
							itemFluidHandler.drain(fillAmount, IFluidHandler.FluidAction.EXECUTE);
							this.transientSlots.setItem(1, itemFluidHandler.getContainer().copy());
							this.transientSlots.setItem(0, ItemStack.EMPTY);
						}
					} else {
						// Reject item
						this.transientSlots.setItem(1, this.transientSlots.getItem(0).copy());
						this.transientSlots.setItem(0, ItemStack.EMPTY);
					}
				}
			}
		}
	}
}
