package com.mcupdater.procenhance.blocks.stonecutter;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static com.mcupdater.procenhance.setup.Registration.STONECUTTER_ENTITY;

public class ElectricStonecutterEntity extends AbstractMachineBlockEntity {

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
        super(STONECUTTER_ENTITY.get(), pPos, pState, 20000, Integer.MAX_VALUE, Config.STONECUTTER_ENERGY_PER_TICK.get(), 1);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 3, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        this.configMap.put("items", itemResourceHandler);
        this.workTotal = 16; // Base recipe type does not have a processing time.  All recipes will be 16 ticks.
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        if (this.level != null) {
            if (this.getInventory().getItem(2).isEmpty() && this.currentRecipe != null) {
                this.setCurrentRecipe(null);
            }
            if (this.currentRecipe == null && this.recipeId != null) {
                this.setCurrentRecipe(this.recipeId);
            }
        }
        super.tick(pLevel, pPos, pBlockState);
    }

    @Override
    protected boolean performWork() {
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        if (this.currentRecipe == null) {
            return false;
        }
        ItemStack inputSlot = itemStorage.getItem(0);
        ItemStack outputSlot = itemStorage.getItem(1);
        if (energyStorage.getStoredEnergy() >= Config.STONECUTTER_ENERGY_PER_TICK.get() && (outputSlot.isEmpty() || (outputSlot.sameItem(currentRecipe.getResultItem()) && outputSlot.getCount() <= (outputSlot.getMaxStackSize() - currentRecipe.getResultItem().getCount()))) && !inputSlot.isEmpty()) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                ItemStack result = this.currentRecipe.assemble(itemStorage);
                if (outputSlot.isEmpty()) {
                    itemStorage.setItem(1, result.copy());
                } else if (outputSlot.is(result.getItem())) {
                    outputSlot.grow(result.getCount());
                }
                this.workProgress = 0;
                itemStorage.getItem(0).shrink(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setCurrentRecipe(ResourceLocation recipeId) {
        StonecutterRecipe stonecutterRecipe = level.getRecipeManager().getAllRecipesFor(RecipeType.STONECUTTING).stream().filter(recipe -> recipe.getId().equals(recipeId)).findFirst().orElse(null);
        this.currentRecipe = stonecutterRecipe;
        if (stonecutterRecipe != null) {
            this.recipeId = stonecutterRecipe.getId();
        } else {
            this.recipeId = null;
        }
        super.setCurrentRecipe(recipeId);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        this.setCurrentRecipe(this.recipeId);
    }

    public StonecutterRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }

    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(int slot, ItemStack pStack) {
        return this.currentRecipe != null && Arrays.stream(this.currentRecipe.getIngredients().get(0).getItems()).anyMatch(validStack -> validStack.sameItem(pStack)); // Source slot
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.stonecutter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ElectricStonecutterMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }
}
