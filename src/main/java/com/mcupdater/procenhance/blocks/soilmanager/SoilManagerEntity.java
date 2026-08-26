package com.mcupdater.procenhance.blocks.soilmanager;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.FluidResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.helpers.RenderHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.SOILMANAGER_ENTITY;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class SoilManagerEntity extends AbstractMachineBlockEntity {
	public static final int MAX_FERTILIZER = 100;
	private final ItemResourceHandler itemResourceHandler;
	private final FluidResourceHandler fluidResourceHandler;
	private int tick;
	private Queue<BlockPos> workArea = new LinkedList<>();

	public ContainerData dataFertilizer = new SimpleContainerData(1);

	public SoilManagerEntity(BlockPos blockPos, BlockState blockState) {
		super(SOILMANAGER_ENTITY.get(), blockPos, blockState, Config.SOILMANAGER_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.SOILMANAGER_ENERGY_PER_TICK.get(), 1);
		itemResourceHandler = new ItemResourceHandler(this.level, 1, new int[]{0}, new int[]{0}, IntStream.empty().toArray(), this::stillValid);
		itemResourceHandler.setInsertFunction(this::validateStack);
		fluidResourceHandler = new FluidResourceHandler(this.level, player -> true);
		FluidTank inputTank = new FluidTank(10000);
		fluidResourceHandler.addTank(inputTank,true, false);
		fluidResourceHandler.setInsertFunction(this::validateFluid);
		this.configMap.put("items", itemResourceHandler);
		this.configMap.put("fluids", fluidResourceHandler);
	}

	private boolean validateFluid(int tank, FluidStack fluidStack) {
		return fluidStack.is(Tags.Fluids.WATER);
	}

	private boolean validateStack(int slot, ItemStack itemStack) {
		return itemStack.is(Items.BONE_MEAL);
	}

	private Boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr(this.worldPosition.getCenter()) <= 64.0D;
		}
	}

	private void initializeWorkArea() {
		Direction workFacing = level.getBlockState(this.worldPosition).getValue(SoilManagerBlock.FACING).getOpposite();
		BlockPos initial = this.worldPosition.relative(workFacing).relative(workFacing.getCounterClockWise(),4);
		for (int row = 0; row < 9; row++) {
			BlockPos currentRowStart = initial;
			IntStream.rangeClosed(0,8).forEach(value -> workArea.add(currentRowStart.relative(workFacing,value)));
			initial = currentRowStart.relative(workFacing.getClockWise());
		}
	}

	@Override
	protected boolean performWork() {
		if (!level.isClientSide()) {
			boolean doneWork = false;
			if (this.workArea.isEmpty()) {
				initializeWorkArea();
			}
			if (dataFertilizer.get(0) == 0 && !this.itemResourceHandler.getInternalHandler().getStackInSlot(0).isEmpty()) {
				this.itemResourceHandler.getInternalHandler().getStackInSlot(0).shrink(1);
				dataFertilizer.set(0, MAX_FERTILIZER);
			}
			if (tick == 0) {
				BlockPos current = this.workArea.remove();
				this.workArea.add(current);
				if (level.getBlockState(current.below()).getBlock() instanceof FarmBlock) {
					if (level.getBlockState(current.below()).getValue(FarmBlock.MOISTURE) < 7 && this.fluidResourceHandler.getInternalHandler().getFluidInTank(0).getAmount() >= 1) {
						BlockState farmlandState = level.getBlockState(current.below());
						level.setBlock(current.below(), farmlandState.setValue(FarmBlock.MOISTURE,7), 2);
						this.fluidResourceHandler.getInternalHandler().drain(1, IFluidHandler.FluidAction.EXECUTE);
						RenderHelper.sendParticles((ServerLevel) this.level, ParticleTypes.SPLASH, current.getX()+0.5D, current.getY(), current.getZ()+0.5D, 3, 0,0,0,0);
					}
				}
				if (level.getBlockState(current).isRandomlyTicking()) {
					doneWork = true;
					level.getBlockState(current).randomTick((ServerLevel) this.level, current, this.level.getRandom());
					if (dataFertilizer.get(0) > 0) {
						level.getBlockState(current).randomTick((ServerLevel) this.level, current, this.level.getRandom());
						dataFertilizer.set(0, dataFertilizer.get(0)-1);
						RenderHelper.sendParticles((ServerLevel) this.level, ParticleTypes.HAPPY_VILLAGER, current.getX()+0.5D, current.getY()+0.5D, current.getZ()+0.5D, 5, 0,0,0,0);
					}
				}
				tick += 5;
				return doneWork;
			} else if (tick > 0) {
				tick--;
				return false;
			} else {
				tick++;
				return false;
			}
		}
		return false;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.soilmanager");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
		return new SoilManagerMenu(windowId, this.level, this.worldPosition, inventory, player, this.dataFertilizer, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}

}
