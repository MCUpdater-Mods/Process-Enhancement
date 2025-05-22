package com.mcupdater.procenhance.blocks.concrete_mixer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.mcupdater.procenhance.setup.Registration.CONCRETEMIXER_ENTITY;

public class MixerEntity extends AbstractMachineBlockEntity {

	private final ItemResourceHandler itemResourceHandler;

	private static BiMap<DyeColor, Block> DYE_MAP = HashBiMap.create(16);

	static {
		DYE_MAP.put(DyeColor.WHITE, Blocks.WHITE_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.ORANGE, Blocks.ORANGE_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.MAGENTA, Blocks.MAGENTA_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.YELLOW, Blocks.YELLOW_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.LIME, Blocks.LIME_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.PINK, Blocks.PINK_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.GRAY, Blocks.GRAY_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.CYAN, Blocks.CYAN_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.PURPLE, Blocks.PURPLE_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.BLUE, Blocks.BLUE_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.BROWN, Blocks.BROWN_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.GREEN, Blocks.GREEN_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.RED, Blocks.RED_CONCRETE_POWDER);
		DYE_MAP.put(DyeColor.BLACK, Blocks.BLACK_CONCRETE_POWDER);
	}

	public ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return MixerEntity.this.workProgress;
				case 1:
					return MixerEntity.this.workTotal;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int newValue) {
			switch (index) {
				case 0:
					MixerEntity.this.workProgress = newValue;
					break;
				case 1:
					MixerEntity.this.workTotal = newValue;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public MixerEntity(BlockPos blockPos, BlockState blockState) {
		super(CONCRETEMIXER_ENTITY.get(), blockPos, blockState, Config.CONCRETEMIXER_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.CONCRETEMIXER_ENERGY_PER_TICK.get(), 1);
		itemResourceHandler = new ItemResourceHandler(this.level, 4, new int[]{0,1,2,3}, new int[]{0,1,2}, new int[]{3}, this::stillValid);
		itemResourceHandler.setInsertFunction(this::canPlaceItem);
		this.configMap.put("items", itemResourceHandler);
		this.workTotal=10;
	}

	private boolean canPlaceItem(int slot, ItemStack itemStack) {
		if (!this.level.isClientSide()) {
			if (slot == 0) {
				return itemStack.is(Items.GRAVEL);
			}
			if (slot == 1) {
				return itemStack.is(Items.SAND);
			}
			if (slot == 2) {
				return itemStack.getItem() instanceof DyeItem;
			}
		}
		return false;
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
		ItemResourceHandler itemStorage = this.getItemHandler();
		ItemStack gravelStack = itemStorage.getItem(0);
		ItemStack sandStack = itemStorage.getItem(1);
		ItemStack dyeStack = itemStorage.getItem(2);
		ItemStack resultStack = itemStorage.getItem(3);
		Block target;
		if (gravelStack.getCount() >= 4 && sandStack.getCount() >= 4 && dyeStack.getCount() >= 1 && dyeStack.getItem() instanceof DyeItem dyeItem && (resultStack.isEmpty() || (resultStack.is(DYE_MAP.get(dyeItem.getDyeColor()).asItem()) && resultStack.getCount() <= resultStack.getMaxStackSize() - 10))) {
			target = DYE_MAP.get(dyeItem.getDyeColor());
			workProgress++;
		} else {
			workProgress = 0;
			return false;
		}
		if (workProgress >= workTotal && (resultStack.isEmpty() || (resultStack.is(target.asItem()) && resultStack.getCount() <= resultStack.getMaxStackSize() - 10))) {
			itemStorage.removeItem(0,4); // Remove gravel
			itemStorage.removeItem(1,4); // Remove sand
			itemStorage.removeItem(2,1); // Remove dye
			if (resultStack.isEmpty()) {
				resultStack = new ItemStack(target.asItem(), 10);
			} else {
				resultStack.grow(10);
			}
			itemStorage.setItem(3, resultStack);
			workProgress = 0;
		}
		return true;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.concrete_mixer");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
		return new MixerMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}

	public Container getInventory() {
		return this.itemResourceHandler;
	}
}
