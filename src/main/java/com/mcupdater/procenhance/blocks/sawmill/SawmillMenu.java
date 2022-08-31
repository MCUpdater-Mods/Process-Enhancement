package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.mculib.inventory.MachineOutputSlot;
import com.mcupdater.mculib.inventory.PhantomSlot;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.network.ChannelRegistration;
import com.mcupdater.procenhance.network.RecipeChangePacket;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SawmillMenu extends AbstractMachineMenu<SawmillEntity> {
    private List<SawmillRecipe> recipes = new ArrayList<>();
    private Slot phantomSlot;

    Runnable slotUpdateListener = () -> {};
    DataSlot selectedRecipeIndexData = new DataSlot() {
        @Override
        public int get() {
            int slotNum = SawmillMenu.this.machineEntity.getCurrentRecipe() != null ? recipes.indexOf(recipes.stream().filter(recipe -> recipe.getId().equals(SawmillMenu.this.machineEntity.getCurrentRecipe().getId())).findFirst().orElse(null)) : -1;
            return slotNum;
        }

        @Override
        public void set(int pValue) {
            ResourceLocation recipeId;
            if (pValue >= 0) {
                recipeId = recipes.get(pValue).getId();
            } else {
                recipeId = new ResourceLocation(ProcessEnhancement.MODID, "invalid_recipe");
            }
            ChannelRegistration.RECIPE_CHANGE.sendToServer(new RecipeChangePacket(SawmillMenu.this.machineEntity.getBlockPos(), recipeId));
        }
    };

    public SawmillMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> directionComponentMap) {
        super((SawmillEntity) level.getBlockEntity(blockPos), Registration.SAWMILL_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
    }

    @Override
    protected void addMachineSlots() {
        this.addSlot(new MachineInputSlot(this.machineEntity, new InvWrapper(this.machineEntity.getInventory()), 0, 12, 48));
        this.addSlot(new MachineOutputSlot(this.machineEntity, new InvWrapper(this.machineEntity.getInventory()), 1, 127, 33));
        this.phantomSlot = this.addSlot(new PhantomSlot(this.machineEntity.getInventory(), 2, 12, 19) {
            @Override
            public void setChanged() {
                super.setChanged();
                SawmillMenu.this.slotsChanged(this.container);
                SawmillMenu.this.slotUpdateListener.run();
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            if (index == 2) {
                return ItemStack.EMPTY;
            }
            if (index != 0 && index != 1) {
                if (this.machineEntity.canPlaceItem(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(stackInSlot, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(stackInSlot, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stackInSlot, 3, 39, true)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(this.machineEntity.getLevel()), this.machineEntity.getBlockPos()), playerIn, Registration.SAWMILL_BLOCK.get());
    }

    @Override
    public void slotsChanged(Container pContainer) {
        ItemStack itemStack = this.phantomSlot.getItem(); // Get phantom slot
        this.setupRecipeList(itemStack);
    }

    private void setupRecipeList(ItemStack itemStack) {
        this.recipes.clear();
        if (!itemStack.isEmpty()) {
            this.recipes = this.machineEntity.getLevel().getRecipeManager().getRecipesFor(SawmillRecipe.Type.INSTANCE, this.machineEntity.getInventory(), this.machineEntity.getLevel());
        }
    }

    public List<SawmillRecipe> getRecipes() {
        return this.recipes;
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndexData.get();
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.phantomSlot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndexData.set(pId);
        }
        return true;
    }

    private boolean isValidRecipeIndex(int pRecipeIndex) {
        return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
    }

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }
}
