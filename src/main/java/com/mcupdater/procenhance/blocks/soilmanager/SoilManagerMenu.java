package com.mcupdater.procenhance.blocks.soilmanager;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class SoilManagerMenu extends AbstractMachineMenu<SoilManagerEntity> {
	private final Container transientSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			SoilManagerMenu.this.slotsChanged(this);
		}
	};
	private final Slot fluidInput =new Slot(this.transientSlots, 0, 25, 16) {
		@Override
		public boolean mayPlace(ItemStack stack) {
			@Nullable IFluidHandlerItem itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
			if (itemFluidHandler != null) {
				IFluidHandler tankFluidHandler = SoilManagerMenu.this.tileEntity.getFluidHandler().getInternalHandler();
				return tankFluidHandler.isFluidValid(0, itemFluidHandler.getFluidInTank(0));
			}
			return false;
		}
	};
	private final Slot fluidOutput = new Slot(this.transientSlots, 1, 25,51) {
		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}
	};

	public SoilManagerMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
		super((SoilManagerEntity) level.getBlockEntity(blockPos), Registration.SOILMANAGER_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
		// Transient slots must be added after class instantiation
		this.addSlot(fluidInput);
		this.addSlot(fluidOutput);
	}

	public static SoilManagerMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		SoilManagerEntity te = (SoilManagerEntity) world.getBlockEntity(pos);
		return new SoilManagerMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
	}

	@Override
	protected void addMachineSlots() {
		ItemResourceHandler resourceHandler = this.machineEntity.getItemHandler();
		addSlot(new SlotItemHandler(resourceHandler.getInternalHandler(), 0, 81, 56)); // Bonemeal input
	}

	@Override
	public boolean stillValid(@NotNull Player playerIn) {
		return ContainerLevelAccess.create(Objects.requireNonNull(machineEntity.getLevel()), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr(blockPos.getCenter()) <= 64.0D, true);
	}

	@Override
	public void removed(@NotNull Player player) {
		super.removed(player);
		ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos()).execute((level, blockPos) -> {
			this.clearContainer(player, this.transientSlots);
		});
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);
		if (container == this.transientSlots) {
			IFluidHandlerItem itemFluidHandler = this.transientSlots.getItem(0).getCapability(Capabilities.FluidHandler.ITEM);
			if (!this.transientSlots.getItem(0).isEmpty() && this.transientSlots.getItem(1).isEmpty() && itemFluidHandler != null) {
				IFluidHandler tankFluidHandler = SoilManagerMenu.this.tileEntity.getFluidHandler().getInternalHandler();
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

	public int getFertilizer() {
		return (this.data.get(0) * 36) / SoilManagerEntity.MAX_FERTILIZER;
	}
}
