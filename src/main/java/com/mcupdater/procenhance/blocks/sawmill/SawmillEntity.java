package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;

import static com.mcupdater.procenhance.setup.Registration.SAWMILL_ENTITY;

public class SawmillEntity extends AbstractMachineBlockEntity {

    private RecipeHolder<SawmillRecipe> currentRecipe = null;
    private ResourceLocation recipeId = null;


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
        super(SAWMILL_ENTITY.get(), blockPos, blockState, Config.SAWMILL_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.SAWMILL_ENERGY_PER_TICK.get(), 1);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 3, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        this.configMap.put("items", itemResourceHandler);
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
        if (energyStorage.getStoredEnergy() >= Config.SAWMILL_ENERGY_PER_TICK.get() && (outputSlot.isEmpty() || (ItemStack.isSameItem(outputSlot,currentRecipe.value().getResultItem(this.level.registryAccess())) && outputSlot.getCount() <= outputSlot.getMaxStackSize() - (currentRecipe.value().getResultItem(this.level.registryAccess()).getCount()))) && !inputSlot.isEmpty()) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                ItemStack result = this.currentRecipe.value().assemble(new MachineContainer(this), this.level.registryAccess());
                if (outputSlot.isEmpty()) {
                    itemStorage.setItem(1, result.copy());
                } else if (outputSlot.is(result.getItem())) {
                    outputSlot.grow(result.getCount());
                }
                this.workProgress = 0;
                itemStorage.getItem(0).shrink(1);
                this.storedXP += this.currentRecipe.value().getExperience();
            }
            return true;
        }
        return false;
    }

    @Override
    public void setCurrentRecipe(ResourceLocation recipeId) {
        RecipeHolder<SawmillRecipe> sawmillRecipe = level.getRecipeManager().getAllRecipesFor(Registration.SAWMILL_RECIPE.get()).stream().filter(recipe -> recipe.id().equals(recipeId)).findFirst().orElse(null);
        this.currentRecipe = sawmillRecipe != null ? sawmillRecipe : null;
        if (this.currentRecipe != null) {
            this.recipeId = sawmillRecipe.id();
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
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithoutMetadata(lookupProvider);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        this.setCurrentRecipe(this.recipeId);
    }

    public RecipeHolder<SawmillRecipe> getCurrentRecipe() {
        return this.currentRecipe;
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        return this.currentRecipe != null && Arrays.stream(this.currentRecipe.value().getIngredients().get(0).getItems()).anyMatch(validStack -> ItemStack.isSameItem(validStack,stack)); // Source slot
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.loadAdditional(compound, pRegistries);
        if (compound.contains("currentRecipe")) this.setCurrentRecipe(ResourceLocation.parse(compound.getString("currentRecipe")));
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        if (this.currentRecipe != null) compound.putString("currentRecipe", this.currentRecipe.id().toString());
        super.saveAdditional(compound, pRegistries);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.processenhancement.sawmill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new SawmillMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }
}
