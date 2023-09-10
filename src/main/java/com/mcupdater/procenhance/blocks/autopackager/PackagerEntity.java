package com.mcupdater.procenhance.blocks.autopackager;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.items.autopackager.AbstractPatternItem;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.AUTOPACKAGER_ENTITY;

public class PackagerEntity extends AbstractMachineBlockEntity {

    private final ItemResourceHandler itemResourceHandler;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 0;
        }
    };

    public PackagerEntity(BlockPos blockPos, BlockState blockState) {
        super(AUTOPACKAGER_ENTITY.get(), blockPos, blockState, Config.AUTOPACKAGER_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.AUTOPACKAGER_ENERGY_PER_TICK.get(), 1);
        itemResourceHandler = new ItemResourceHandler(this.level, 10, new int[]{0,1},new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        this.configMap.put("items", itemResourceHandler);
        this.workTotal=10;
    }

    private boolean canPlaceItem(int slot, ItemStack pStack) {
        if (!this.level.isClientSide()) {
            if (slot == 0) {
                for (int patSlot = 2; patSlot < itemResourceHandler.getContainerSize(); patSlot++) {
                    ItemStack patternStack = itemResourceHandler.getItem(patSlot).copy();
                    if (patternStack.getItem() instanceof AbstractPatternItem pattern) {
                        if (pattern.matchingRecipe(pStack, this.level) != null) {
                            return true;
                        }
                    }
                }
            }
            if (slot >= 2) {
                if (pStack.getItem() instanceof AbstractPatternItem) {
                    return true;
                }
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
        if (!level.isClientSide()) {
            if (!itemResourceHandler.getItem(0).isEmpty() && itemResourceHandler.getItem(1).isEmpty()) {
                this.workProgress++;
                if (this.workProgress >= this.workTotal) {
                    ItemStack inputStack = itemResourceHandler.getItem(0);
                    for (int patSlot = 2; patSlot < itemResourceHandler.getContainerSize(); patSlot++) {
                        ItemStack patternStack = itemResourceHandler.getItem(patSlot);
                        if (patternStack.getItem() instanceof AbstractPatternItem pattern) {
                            ItemStack result = pattern.doCraft(inputStack, this.level);
                            if (result != ItemStack.EMPTY) {
                                itemResourceHandler.setItem(1, result);
                                this.workProgress = 0;
                                return true;
                            }
                        }
                    }
                    itemResourceHandler.setItem(1, itemResourceHandler.getItem(0).split(itemResourceHandler.getItem(0).getCount()));
                } else {
                    return true;
                }
            } else {
                workProgress = 0;
                return false;
            }
        }
        return false;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.autopackager");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new PackagerMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return this.itemResourceHandler;
    }
}
