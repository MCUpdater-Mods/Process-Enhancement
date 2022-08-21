package com.mcupdater.procenhance.blocks.stonecutter;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.network.ChannelRegistration;
import com.mcupdater.procenhance.network.RecipeChangeStonecutterPacket;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static com.mcupdater.procenhance.setup.Registration.STONECUTTER_ENTITY;

public class ElectricStonecutterEntity extends AbstractMachineBlockEntity {

    protected NonNullList<ItemStack> itemStorage = NonNullList.withSize(3, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private StonecutterRecipe currentRecipe = null;
    private ResourceLocation recipeId = null;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            switch (pIndex) {
                case 0:
                    return ElectricStonecutterEntity.this.workProgress;
                case 1:
                    return ElectricStonecutterEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    ElectricStonecutterEntity.this.workProgress = pValue;
                    break;
                case 1:
                    ElectricStonecutterEntity.this.workTotal = pValue;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ElectricStonecutterEntity(BlockPos pPos, BlockState pState) {
        super(STONECUTTER_ENTITY.get(), pPos, pState, 20000, Integer.MAX_VALUE, ReceiveMode.ACCEPTS, SendMode.SHARE, Config.STONECUTTER_ENERGY_PER_TICK.get());
        this.workTotal = 16; // Base recipe type does not have a processing time.  All recipes will be 16 ticks.
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        if (this.level != null) {
            if (this.currentRecipe == null && this.recipeId != null) {
                this.setCurrentRecipe(this.level.getRecipeManager().getAllRecipesFor(RecipeType.STONECUTTING).stream().filter(recipe -> recipe.getId().equals(this.recipeId)).findFirst().get());
            }
        }
        super.tick(pLevel, pPos, pBlockState);
    }

    @Override
    protected boolean performWork() {
        if (this.currentRecipe == null) {
            return false;
        }
        ItemStack inputSlot = this.itemStorage.get(0);
        ItemStack outputSlot = this.itemStorage.get(1);
        if (this.energyStorage.getStoredEnergy() >= Config.STONECUTTER_ENERGY_PER_TICK.get() && (outputSlot.isEmpty() || (outputSlot.sameItem(currentRecipe.getResultItem()) && outputSlot.getCount() <= (outputSlot.getMaxStackSize() - currentRecipe.getResultItem().getCount()))) && !inputSlot.isEmpty()) {
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
        if (compound.contains("recipeId")) {
            recipeId = new ResourceLocation(compound.getString("recipeId"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (this.currentRecipe != null) {
            compound.putString("recipeId", this.currentRecipe.getId().toString());
        }
        ContainerHelper.saveAllItems(compound,this.itemStorage);
        super.saveAdditional(compound);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return itemHandler[side != null ? side.ordinal() : 0].cast();
        }
        return super.getCapability(cap, side);
    }

    public void setCurrentRecipe(StonecutterRecipe newRecipe) {
        this.currentRecipe = newRecipe;
        if (newRecipe != null) {
            this.recipeId = newRecipe.getId();
        } else {
            this.recipeId = null;
        }
        if (!level.isClientSide()) {
            ChannelRegistration.RECIPE_CHANGE.send(PacketDistributor.ALL.noArg(), new RecipeChangeStonecutterPacket(this.worldPosition, this.currentRecipe.getId()));
            //ChannelRegistration.RECIPE_CHANGE.sendToServer(new RecipeChangeStonecutterPacket(this.worldPosition,this.currentRecipe.getId()));
        }
    }

    public StonecutterRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }

    // WorldlyContainer methods

    @Override
    public int getContainerSize() {
        return this.itemStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return this.itemStorage.get(0).isEmpty() && this.itemStorage.get(1).isEmpty();
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
        return ContainerHelper.takeItem(itemStorage, pSlot);
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
            return pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        switch (pIndex) {
            case 0:
                return this.currentRecipe != null && pStack.sameItem(this.currentRecipe.getIngredients().get(0).getItems()[0]); // Source slot
            case 1:
                return false; // Destination slot
            default:
                return false; // Invalid slot
        }
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[]{0,1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pIndex == 1;
    }

    @Override
    public void clearContent() {
        this.itemStorage.clear();
    }


    // MenuProvider methods

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.stonecutter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        ChannelRegistration.RECIPE_CHANGE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 16.0d, this.level.dimension())), new RecipeChangeStonecutterPacket(this.worldPosition, this.currentRecipe.getId()));
        return new ElectricStonecutterMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
