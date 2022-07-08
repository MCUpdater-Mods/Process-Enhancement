package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.block.MachineBlockEntity;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.mcupdater.procenhance.setup.Registration.SAWMILL_BLOCKENTITY;

public class SawmillEntity extends MachineBlockEntity implements WorldlyContainer, MenuProvider {

    protected NonNullList<ItemStack> itemStorage = NonNullList.withSize(2, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private SawmillRecipe currentRecipe = null;


    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return SawmillEntity.this.workProgress;
                case 1:
                    return SawmillEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    SawmillEntity.this.workProgress = newValue;
                    break;
                case 1:
                    SawmillEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SawmillEntity(BlockPos blockPos, BlockState blockState) {
        super(SAWMILL_BLOCKENTITY.get(), blockPos, blockState, 20000, Integer.MAX_VALUE, ReceiveMode.ACCEPTS, SendMode.SHARE, Config.SAWMILL_ENERGY_PER_TICK.get());
    }

    @Override
    protected boolean performWork() {
        ItemStack inputStack = this.itemStorage.get(0);
        if (!inputStack.isEmpty()) {
            Recipe<?> recipe = this.level.getRecipeManager().getRecipeFor(SawmillRecipe.Type.INSTANCE, this, this.level).orElse(null);
            if (this.currentRecipe == null || !this.currentRecipe.equals(recipe)) {
                if (recipe != null) {
                    this.currentRecipe = (SawmillRecipe) recipe;
                    this.workTotal = this.currentRecipe.getProcessTime();
                }
                this.workProgress = 0;
            }
        } else {
            this.currentRecipe = null;
        }
        ItemStack outputSlot = this.itemStorage.get(1);
        if (this.currentRecipe != null && (outputSlot.isEmpty() || (outputSlot.sameItem(currentRecipe.getResultItem()) && outputSlot.getCount() < outputSlot.getMaxStackSize() - (currentRecipe.getResultItem().getCount() - 1)))) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                ItemStack result = this.currentRecipe.assemble(this);
                if (outputSlot.isEmpty()) {
                    this.itemStorage.set(1, result.copy());
                } else if (outputSlot.is(result.getItem())) {
                    outputSlot.grow(result.getCount());
                }
                this.workProgress = 0;
                this.itemStorage.get(0).shrink(1);
                this.storedXP += this.currentRecipe.getExperience();
            }
            return true;
        }
        return false;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.itemStorage = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.itemStorage);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        ContainerHelper.saveAllItems(compound,this.itemStorage);
        super.saveAdditional(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return itemHandler[side != null ? side.ordinal() : 0].cast();
        }
        return super.getCapability(cap, side);
    }

    // WorldlyContainer methods
    @Override
    public int getContainerSize() {
        return this.itemStorage.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemStack : this.itemStorage) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.itemStorage.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.itemStorage, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.itemStorage, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.itemStorage.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        switch (index) {
            case 0:
                return true; // Source slot
            case 1:
                return false; // Destination slot
            default:
                return false; // Invalid slot
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0,1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index == 0 && this.canPlaceItem(index, itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    public void clearContent() {
        this.itemStorage.clear();
    }

    // MenuProvider methods
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.processenhancement.sawmill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new SawmillMenu(windowId, this.level, this.worldPosition, inventory, player, this.data);
    }
}
