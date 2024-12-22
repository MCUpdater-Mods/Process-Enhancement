package com.mcupdater.procenhance.blocks.hydrator;

import com.mcupdater.mculib.block.AbstractMachineMenu;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class HydratorMenu extends AbstractMachineMenu<HydratorEntity> {
	private final Container transientSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			HydratorMenu.this.slotsChanged(this);
		}
	};

	public HydratorMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> directionComponentMap) {
		super((HydratorEntity) level.getBlockEntity(blockPos), Registration.HYDRATOR_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
		this.addSlot(new Slot(this.transientSlots, 0, 25, 16) {
			@Override
			public boolean mayPlace(ItemStack pStack) {
				@Nullable IFluidHandlerItem itemFluidHandler = pStack.getCapability(Capabilities.FluidHandler.ITEM);
				if (itemFluidHandler != null){
					IFluidHandler tankFluidHandler = HydratorMenu.this.tileEntity.getFluidHandler().getInternalHandler();
					return tankFluidHandler.isFluidValid(0, itemFluidHandler.getFluidInTank(0)) && level.getRecipeManager().getAllRecipesFor(Registration.HYDRATOR_RECIPE.get()).stream().anyMatch(recipeHolder -> recipeHolder.value().getFluidIngredient().getFluid().isSame(itemFluidHandler.getFluidInTank(0).getFluid()));
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

	public static HydratorMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		HydratorEntity te = (HydratorEntity) world.getBlockEntity(pos);
		return new HydratorMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
	}

	@Override
	public void removed(@NotNull Player player) {
		super.removed(player);
		ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos()).execute((level, blockPos) -> {
			this.clearContainer(player, this.transientSlots);
		});
	}

	@Override
	public void slotsChanged(@NotNull Container container) {
		super.slotsChanged(container);
		if (container == this.transientSlots) {
			IFluidHandlerItem itemFluidHandler = this.transientSlots.getItem(0).getCapability(Capabilities.FluidHandler.ITEM);
			if (!this.transientSlots.getItem(0).isEmpty() && this.transientSlots.getItem(1).isEmpty() && itemFluidHandler != null) {
				IFluidHandler tankFluidHandler = HydratorMenu.this.tileEntity.getFluidHandler().getInternalHandler();
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
