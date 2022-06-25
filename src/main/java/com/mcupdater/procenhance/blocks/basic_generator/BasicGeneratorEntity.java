package com.mcupdater.procenhance.blocks.basic_generator;

import com.mcupdater.mculib.capabilities.TileEntityPowered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static com.mcupdater.procenhance.setup.Registration.BASICGENERATOR_BLOCKENTITY;

public class BasicGeneratorEntity extends TileEntityPowered implements WorldlyContainer, MenuProvider {
    protected NonNullList<ItemStack> itemStorage = NonNullList.withSize(2, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());

    int burnCurrent;
    int burnTotal;
    public ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return BasicGeneratorEntity.this.burnCurrent;
                case 1:
                    return BasicGeneratorEntity.this.burnTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    BasicGeneratorEntity.this.burnCurrent = newValue;
                    break;
                case 1:
                    BasicGeneratorEntity.this.burnTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    public BasicGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        super(BASICGENERATOR_BLOCKENTITY.get(), blockPos, blockState, 200000, Integer.MAX_VALUE );
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (this.burnCurrent > 0) {
                int added = this.energyStorage.receiveEnergy(20, false);
                if (added > 0) {
                    --this.burnCurrent;
                }
            }
            if (this.burnCurrent == 0 && !this.itemStorage.get(0).isEmpty()) {
                int newBurnTime = ForgeHooks.getBurnTime(this.itemStorage.get(0), RecipeType.SMELTING);
                if (newBurnTime > 0) {
                    if (this.itemStorage.get(0).hasContainerItem()) {
                        if ((this.itemStorage.get(0).getContainerItem().sameItem(this.itemStorage.get(1)) && this.itemStorage.get(1).getCount() < this.itemStorage.get(1).getMaxStackSize()) || this.itemStorage.get(1).isEmpty()) {
                            if (this.itemStorage.get(1).isEmpty()) {
                                this.itemStorage.set(1, itemStorage.get(0).getContainerItem());
                            } else {
                                this.itemStorage.get(1).grow(1);
                            }
                        } else {
                            return;
                        }
                    }
                    this.itemStorage.get(0).shrink(1);
                    this.burnCurrent += newBurnTime;
                    this.burnTotal = this.burnCurrent;
                }
            }
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.itemStorage = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.burnTotal = compound.getInt("burnTotal");
        this.burnCurrent = compound.getInt("burnCurrent");
        ContainerHelper.loadAllItems(compound, this.itemStorage);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putInt("burnTotal", this.burnTotal);
        compound.putInt("burnCurrent", this.burnCurrent);
        ContainerHelper.saveAllItems(compound, this.itemStorage);
        super.saveAdditional(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
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
        for(ItemStack itemstack : this.itemStorage) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;    }

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
                return (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0);
            case 1:
                return true;
            default:
                return false; // Invalid slot
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0,1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @javax.annotation.Nullable Direction direction) {
        return index == 0 && this.canPlaceItem(index, itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    public void clearContent() {

    }

    // MenuProvider methods
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.processenhancement.basic_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new BasicGeneratorMenu(windowId, this.level, this.worldPosition, inventory, player, this.data);
    }
}
