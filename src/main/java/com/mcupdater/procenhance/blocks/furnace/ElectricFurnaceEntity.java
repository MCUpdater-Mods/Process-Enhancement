package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import java.util.Arrays;

public abstract class ElectricFurnaceEntity extends AbstractMachineBlockEntity {

    private AbstractCookingRecipe currentRecipe = null;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return ElectricFurnaceEntity.this.workProgress;
                case 1:
                    return ElectricFurnaceEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    ElectricFurnaceEntity.this.workProgress = newValue;
                    break;
                case 1:
                    ElectricFurnaceEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ElectricFurnaceEntity(BlockEntityType<?> pType, BlockPos blockPos, BlockState blockState, int pMultiplier) {
        super(pType, blockPos, blockState, Config.FURNACE_ENERGY_PER_TICK.get() * 1000 * pMultiplier, Integer.MAX_VALUE, Config.FURNACE_ENERGY_PER_TICK.get(), pMultiplier);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 2, new int[]{0, 1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::isItemValid);
        this.configMap.put("items", itemResourceHandler);
    }

    private boolean isItemValid(int slot, ItemStack itemStack) {
        return this.level.getRecipeManager().
                getAllRecipesFor(RecipeType.SMELTING).stream()
                .anyMatch(recipe ->
                        Arrays.stream(recipe.getIngredients().get(0).getItems()).anyMatch(inputItem -> inputItem.sameItem(itemStack)));
    }

    @Override
    protected boolean performWork() {
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        ItemStack inputStack = itemStorage.getItem(0);
        if (!inputStack.isEmpty()) {
            Recipe<?> recipe = this.level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, itemStorage, this.level).orElse(null);
            if (this.currentRecipe == null || !this.currentRecipe.equals(recipe)) {
                if (recipe != null) {
                    this.currentRecipe = (AbstractCookingRecipe) recipe;
                    this.workTotal = this.currentRecipe.getCookingTime();
                }
                this.workProgress = 0;
            }
            int i = itemStorage.getMaxStackSize();
        } else {
            this.currentRecipe = null;
        }
        ItemStack outputSlot = itemStorage.getItem(1);
        if (this.currentRecipe != null && energyStorage.getStoredEnergy() >= Config.FURNACE_ENERGY_PER_TICK.get() && (outputSlot.isEmpty() || (outputSlot.sameItem(currentRecipe.getResultItem()) && outputSlot.getCount() < outputSlot.getMaxStackSize()))) {
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
                this.storedXP += this.currentRecipe.getExperience();
            }
            return true;
        }
        return false;
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    // MenuProvider methods
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new ElectricFurnaceMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }
}
