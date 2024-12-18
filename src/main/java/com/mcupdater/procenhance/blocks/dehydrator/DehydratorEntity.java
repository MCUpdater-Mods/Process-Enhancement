package com.mcupdater.procenhance.blocks.dehydrator;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.FluidResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.recipe.DehydratorRecipe;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Arrays;

import static com.mcupdater.procenhance.setup.Registration.DEHYDRATOR_ENTITY;

public class DehydratorEntity extends AbstractMachineBlockEntity {

	private final ItemResourceHandler itemResourceHandler;
	private RecipeHolder<DehydratorRecipe> currentRecipe = null;

	public ContainerData data = new ContainerData() {
		@Override
		public int get(int pIndex) {
			switch (pIndex) {
				case 0:
					return DehydratorEntity.this.workProgress;
				case 1:
					return DehydratorEntity.this.workTotal;
				default:
					return 0;
			}
		}

		@Override
		public void set(int pIndex, int pValue) {
			switch (pIndex) {
				case 0:
					DehydratorEntity.this.workProgress = pValue;
					break;
				case 1:
					DehydratorEntity.this.workTotal = pValue;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public DehydratorEntity(BlockPos blockPos, BlockState blockState) {
		super(DEHYDRATOR_ENTITY.get(), blockPos, blockState, Config.DEHYDRATOR_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.DEHYDRATOR_ENERGY_PER_TICK.get(), 1);
		itemResourceHandler = new ItemResourceHandler(this.level, 2, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
		itemResourceHandler.setInsertFunction(this::canPlaceItem);
		FluidResourceHandler fluidResourceHandler = new FluidResourceHandler(this.level, player -> true);
		FluidTank outputTank = new FluidTank(10000);
		fluidResourceHandler.addTank(outputTank,false, true);
		this.configMap.put("items", itemResourceHandler);
		this.configMap.put("fluids", fluidResourceHandler);
	}

	private boolean canPlaceItem(int slot, ItemStack pStack) {
		return this.level.getRecipeManager().
				getAllRecipesFor(Registration.DEHYDRATOR_RECIPE.get()).stream().
				anyMatch(recipeHolder -> Arrays.stream(recipeHolder.value().getItemIngredients().get(0).getItems()).
						anyMatch(inputStack -> ItemStack.isSameItem(inputStack,pStack)));
	}

	private Boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	protected boolean performWork() {
		ItemResourceHandler itemStorage= (ItemResourceHandler) this.configMap.get("items");
		EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
		FluidResourceHandler fluidStorage = (FluidResourceHandler) this.configMap.get("fluids");
		MachineContainer machineContainer = new MachineContainer(this);
		ItemStack inputStack = itemStorage.getItem(0);
		FluidStack outputFluid = fluidStorage.getInternalHandler().getFluidInTank(0);
		if (!inputStack.isEmpty()) {
			RecipeHolder<DehydratorRecipe> recipeHolder = this.level.getRecipeManager().getRecipeFor(Registration.DEHYDRATOR_RECIPE.get(), machineContainer, this.level).orElse(null);
			if (this.currentRecipe == null || !this.currentRecipe.equals(recipeHolder)) {
				if (recipeHolder != null) {
					this.currentRecipe = recipeHolder;
					this.workTotal = this.currentRecipe.value().getProcessTime();
				}
				this.workProgress = 0;
			}
		} else {
			this.currentRecipe = null;
		}
		ItemStack outputSlot = itemStorage.getItem(1);
		if (this.currentRecipe != null && energyStorage.getStoredEnergy() >= Config.DEHYDRATOR_ENERGY_PER_TICK.get() && (outputFluid.isEmpty() || FluidStack.isSameFluid(outputFluid,currentRecipe.value().getFluidOutput())) && (outputSlot.isEmpty() || (ItemStack.isSameItem(outputSlot,currentRecipe.value().getResultItem(level.registryAccess())) && outputSlot.getCount() < outputSlot.getMaxStackSize()))) {
			this.workProgress++;
			if (this.workProgress >= this.workTotal) {
				ItemStack result = this.currentRecipe.value().assemble(machineContainer, level.registryAccess());
				if (outputSlot.isEmpty()) {
					itemStorage.setItem(1,result.copy());
				} else if (outputSlot.is(result.getItem())) {
					outputSlot.grow(result.getCount());
				}
				this.workProgress = 0;
				itemStorage.getItem(0).shrink(1);
				fluidStorage.getInternalHandler().forceFill(0, this.currentRecipe.value().getFluidOutput().copy(), IFluidHandler.FluidAction.EXECUTE);
			}
			return true;
		}
		return false;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.dehydrator");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
		return new DehydratorMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}

	public Container getInventory() {
		return this.itemResourceHandler;
	}
}
