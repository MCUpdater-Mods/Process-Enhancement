package com.mcupdater.procenhance.blocks.deconstructor;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.AbstractResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import static com.mcupdater.procenhance.setup.Registration.DECONSTRUCTOR_ENTITY;

public class DeconstructorEntity extends AbstractMachineBlockEntity {

    private final ItemResourceHandler itemResourceHandler;
    private Recipe currentRecipe = null;
    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return DeconstructorEntity.this.workProgress;
                case 1:
                    return DeconstructorEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    DeconstructorEntity.this.workProgress = newValue;
                    break;
                case 1:
                    DeconstructorEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public DeconstructorEntity(BlockPos blockPos, BlockState blockState) {
        super (DECONSTRUCTOR_ENTITY.get(), blockPos, blockState, Config.DECONSTRUCTOR_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.DECONSTRUCTOR_ENERGY_PER_TICK.get(), 1);
        itemResourceHandler = new ItemResourceHandler(this.level, 10, IntStream.range(0,10).toArray(), new int[]{0}, IntStream.range(1,10).toArray(), this::stillValid);
        itemResourceHandler.setInsertFunction((slot, itemStack) -> slot == 0 && getRecipe(itemStack) != null);
        this.configMap.put("items", itemResourceHandler);
    }

    private Recipe getRecipe(ItemStack itemStack) {
        Recipe lookup = null;
        lookup = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream().filter(testRecipe -> testRecipe.getResultItem().getItem().equals(itemStack.getItem()) && testRecipe.getResultItem().getCount() <= itemStack.getCount()).findFirst().orElse(null);
        if (lookup == null) {
            lookup = level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING).stream().filter(testRecipe -> testRecipe.getResultItem().getItem().equals(itemStack.getItem())).findFirst().orElse(null);
        }
        return lookup;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.deconstructor");
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        if (this.level != null) {
            ItemStack inputSlot = itemResourceHandler.getItem(0);
            if (currentRecipe != null && (inputSlot.isEmpty() || !(currentRecipe.getResultItem().getItem().equals(inputSlot.getItem()) && currentRecipe.getResultItem().getCount() <= inputSlot.getCount()))) {
                currentRecipe = null;
                workProgress = 0;
                if (!inputSlot.isEmpty()) {
                    NonNullList<ItemStack> outputSlots = NonNullList.withSize(9,ItemStack.EMPTY);
                    for (int slot = 1; slot < 10; slot++) {
                        outputSlots.set(slot-1, itemResourceHandler.getItem(slot));
                    }
                    if (isOutputEmpty(outputSlots)) {
                        itemResourceHandler.setItem(1, inputSlot.copy());
                        itemResourceHandler.setItem(0, ItemStack.EMPTY);
                    }
                }
            } else {
                if (currentRecipe == null && !itemResourceHandler.getItem(0).isEmpty()) {
                    currentRecipe = getRecipe(inputSlot);
                    workProgress = 0;
                    workTotal = 200;
                }
            }
        }
        super.tick(pLevel, pPos, pBlockState);
    }

    @Override
    protected boolean performWork() {
        ItemStack inputSlot = itemResourceHandler.getItem(0);
        NonNullList<ItemStack> outputSlots = NonNullList.withSize(9,ItemStack.EMPTY);
        for (int slot = 1; slot < 10; slot++) {
            outputSlots.set(slot-1, itemResourceHandler.getItem(slot));
        }
        if (currentRecipe != null && isOutputEmpty(outputSlots)){
            workProgress++;
            if (workProgress >= workTotal) {
                if (currentRecipe instanceof CraftingRecipe craftingRecipe) {
                    int slot = 1;
                    for (Ingredient ingredient : craftingRecipe.getIngredients()) {
                        if (!ingredient.isEmpty() && ingredient.getItems().length > 0 && !ingredient.getItems()[0].hasContainerItem()) {
                            itemResourceHandler.setItem(slot, Arrays.stream(ingredient.getItems()).findFirst().orElse(ItemStack.EMPTY).copy());
                        }
                        slot++;
                    }
                    inputSlot.shrink(currentRecipe.getResultItem().getCount());
                }
                if (currentRecipe instanceof UpgradeRecipe upgradeRecipe) {
                    itemResourceHandler.setItem(1, upgradeRecipe.base.getItems()[0].copy());
                    itemResourceHandler.setItem(2, upgradeRecipe.addition.getItems()[0].copy());
                    inputSlot.shrink(currentRecipe.getResultItem().getCount());
                }
                workProgress = 0;
            }
            return true;
        }
        return false;
    }

    private boolean isOutputEmpty(NonNullList<ItemStack> outputSlots) {
        return outputSlots.stream().filter(stack -> !stack.equals(ItemStack.EMPTY)).count() == 0;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new DeconstructorMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Map<String, AbstractResourceHandler> getConfigMap() {
        return this.configMap;
    }
}
