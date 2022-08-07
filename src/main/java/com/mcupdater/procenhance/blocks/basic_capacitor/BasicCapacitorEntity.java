package com.mcupdater.procenhance.blocks.basic_capacitor;

import com.mcupdater.mculib.capabilities.PoweredBlockEntity;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.BASICCAPACITOR_BLOCKENTITY;

public class BasicCapacitorEntity extends PoweredBlockEntity implements WorldlyContainer, MenuProvider {
    protected NonNullList<ItemStack> itemStorage = NonNullList.withSize(1, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private Component name;
    public ContainerData data = new SimpleContainerData(0);

    public BasicCapacitorEntity(BlockPos pPos, BlockState pState) {
        super(BASICCAPACITOR_BLOCKENTITY.get(), pPos, pState, 250000, 2000, ReceiveMode.NOT_SHARED, SendMode.SEND_ALL);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        if (!this.level.isClientSide) {
            // Charge item in slot


            // Update BlockState
            int powerLevel = (int) Mth.ceil(((double)this.energyStorage.getEnergyStored() / (double)this.energyStorage.getMaxEnergyStored()) * 4.0d);
            int currentState = pBlockState.getValue(BasicCapacitorBlock.CHARGE_LEVEL);
            if (powerLevel != currentState) {
                pBlockState = pBlockState.setValue(BasicCapacitorBlock.CHARGE_LEVEL, powerLevel);
                pLevel.setBlock(pPos, pBlockState, 3);
            }
        }
        super.tick();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.itemStorage = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (compound.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
        ContainerHelper.loadAllItems(compound, this.itemStorage);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        ContainerHelper.saveAllItems(compound, this.itemStorage);
        if (this.name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        super.saveAdditional(compound);
        ProcessEnhancement.LOGGER.debug("saveAdditional - " + compound.getAsString());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return itemHandler[side != null ? side.ordinal() : 0].cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BasicCapacitorMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[]{0};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return pIndex == 0 && this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

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
    public ItemStack getItem(int pSlot) {
        return this.itemStorage.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.itemStorage, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.itemStorage, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.itemStorage.set(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.itemStorage.clear();
    }

    public void setCustomName(Component hoverName) {
        this.name = hoverName;
    }

    @Override
    public Component getDisplayName() {
        return this.name != null ? this.name : new TranslatableComponent("block.processenhancement.basic_capacitor");
    }
}
