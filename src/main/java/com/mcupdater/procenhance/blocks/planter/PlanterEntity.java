package com.mcupdater.procenhance.blocks.planter;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.helpers.RenderHelper;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

import static com.mcupdater.procenhance.setup.Registration.PLANTER_ENTITY;

public class PlanterEntity extends AbstractMachineBlockEntity {

	private final ItemResourceHandler itemResourceHandler;
	private int tick;
	private Queue<Tuple<BlockPos,Integer>> workArea = new LinkedList<>();

	public ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			return 0;
		}

		@Override
		public void set(int index, int value) {

		}

		@Override
		public int getCount() {
			return 0;
		}
	};

	public PlanterEntity(BlockPos blockPos, BlockState blockState) {
		super(PLANTER_ENTITY.get(), blockPos, blockState, Config.PLANTER_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.PLANTER_ENERGY_PER_TICK.get(), 1);
		int[] slots = IntStream.rangeClosed(0,8).toArray();
		itemResourceHandler = new ItemResourceHandler(this.level, 18, slots, slots, IntStream.empty().toArray(), this::stillValid);
		itemResourceHandler.setInsertFunction(this::validateStack);
		this.configMap.put("items", itemResourceHandler);
	}

	private void initializeWorkArea() {
		Direction workFacing = level.getBlockState(this.worldPosition).getValue(PlanterBlock.FACING).getOpposite();
		BlockPos initial = this.worldPosition.relative(workFacing).relative(workFacing.getCounterClockWise(),4);
		for (int i = 0; i < 9; i++) {
			Integer row = i;
			BlockPos currentRowStart = initial;
			IntStream.rangeClosed(0,8).forEach(value -> workArea.add(new Tuple<>(currentRowStart.relative(workFacing,value), row)));
			initial = currentRowStart.relative(workFacing.getClockWise());
		}
	}

	private boolean validateStack(int slot, ItemStack itemStack) {
		if (IntStream.rangeClosed(9,17).anyMatch(value -> value == slot)) {// Phantom Slots
			return itemStack.is(Tags.Items.SEEDS);
		}
		return ItemStack.isSameItem(itemStack, itemResourceHandler.getItem(slot + 9));
	}

	private Boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	protected boolean performWork() {
		if (!level.isClientSide()) {
			if (this.workArea.isEmpty()) {
				initializeWorkArea();
			}
			if (tick == 0) {
				Tuple<BlockPos,Integer> current = this.workArea.remove(); // Get the first entry
				this.workArea.add(current); // ...and put it back at the end of the queue
				RenderHelper.sendParticles((ServerLevel) level, ParticleTypes.WHITE_SMOKE, current.getA().getX()+0.5D, current.getA().getY()+0.5D, current.getA().getZ()+0.5D,1,0,0,0,0); // Visualize where it is working
				ItemStack filterSlot = itemResourceHandler.getInternalHandler().getStackInSlot(current.getB()+9);
				if (filterSlot.isEmpty()) {
					tick -= 10;
					return false;
				}
				if (filterSlot.getItem() instanceof BlockItem blockItem && (blockItem.getBlock() instanceof CropBlock || blockItem.getBlock() instanceof StemBlock)) {
					if (level.getBlockState(current.getA().below()).is(BlockTags.DIRT)) {
						level.setBlock(current.getA().below(), Blocks.FARMLAND.defaultBlockState(), 3);
					}
				}
				if (!itemResourceHandler.getInternalHandler().getStackInSlot(current.getB()).isEmpty()) {
					if (!level.getBlockState(current.getA().below()).is(BlockTags.AIR) && (level.getBlockState(current.getA()).is(BlockTags.AIR) || level.getBlockState(current.getA()).getBlock().equals(Blocks.WATER))) {
						try {
							BlockHitResult hitResult = new BlockHitResult(current.getA().above().getCenter(), Direction.DOWN, current.getA(), false);
							InteractionResult interactionResult = itemResourceHandler.getInternalHandler().getStackInSlot(current.getB()).useOn(new UseOnContext(this.level, null, InteractionHand.MAIN_HAND, itemResourceHandler.getInternalHandler().getStackInSlot(current.getB()), hitResult));
							if (interactionResult.indicateItemUse()) {
								tick += 20;
								return true;
							}
						} catch (Exception e) {
							ProcessEnhancement.LOGGER.error("Exception caught!",e);
							tick -= 10;
							return false;
						}
					}
				}
				tick -= 10;
				return false;
			} else if (tick > 0) {
				// Sleeping while on
				tick--;
				return true;
			} else {
				// Sleeping while off
				tick++;
				return false;
			}
		}
		return false;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.planter");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
		return new PlanterMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}

	public Container getInventory() {
		return this.itemResourceHandler;
	}
}
