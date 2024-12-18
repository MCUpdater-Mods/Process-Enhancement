package com.mcupdater.procenhance.blocks.stonecutter;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.mculib.inventory.MachineOutputSlot;
import com.mcupdater.mculib.inventory.PhantomSlot;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.sawmill.SawmillMenu;
import com.mcupdater.procenhance.network.RecipeChange;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElectricStonecutterMenu extends AbstractMachineMenu<ElectricStonecutterEntity> {
    private List<RecipeHolder<StonecutterRecipe>> recipes = new ArrayList<>();
    private Slot phantomSlot;

    Runnable slotUpdateListener = () -> {};
    DataSlot selectedRecipeIndexData = new DataSlot() {
        @Override
        public int get() {
            RecipeHolder<StonecutterRecipe> currentRecipe = ElectricStonecutterMenu.this.machineEntity.getCurrentRecipe();
            int slotNum = ElectricStonecutterMenu.this.machineEntity.getCurrentRecipe() != null ? recipes.indexOf(recipes.stream().filter(recipe -> recipe.id().equals(ElectricStonecutterMenu.this.machineEntity.getCurrentRecipe().id())).findFirst().orElse(null)) : -1;
            return slotNum;
        }

        @Override
        public void set(int pValue) {
            ResourceLocation recipeId;
            if (pValue >= 0) {
                recipeId = recipes.get(pValue).id();
            } else {
                recipeId = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID,"invalid_recipe");
            }
            PacketDistributor.sendToServer(new RecipeChange(ElectricStonecutterMenu.this.machineEntity.getBlockPos(), recipeId));
        }
    };

    public ElectricStonecutterMenu(int pContainerId, Level pLevel, BlockPos pPos, Inventory pPlayerInventory, Player pPlayer, ContainerData data, Map<Direction, String> directionComponentMap) {
        super((ElectricStonecutterEntity) pLevel.getBlockEntity(pPos), Registration.STONECUTTER_MENU.get(), pContainerId, pLevel, pPos, pPlayerInventory, pPlayer, data, directionComponentMap);
    }

    public static ElectricStonecutterMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        Level world = playerInv.player.level();
        ElectricStonecutterEntity te = (ElectricStonecutterEntity) world.getBlockEntity(pos);
        return new ElectricStonecutterMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
    }

    @Override
    protected void addMachineSlots() {
        this.addSlot(new MachineInputSlot(this.machineEntity, new InvWrapper(this.machineEntity.getInventory()), 0, 12, 48));
        this.addSlot(new MachineOutputSlot(this.machineEntity, new InvWrapper(this.machineEntity.getInventory()), 1, 127, 33));
        this.phantomSlot = this.addSlot(new PhantomSlot(this.machineEntity.getInventory(), 2, 12, 19) {
            @Override
            public void setChanged() {
                super.setChanged();
                ElectricStonecutterMenu.this.slotsChanged(this.container);
                ElectricStonecutterMenu.this.slotUpdateListener.run();
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
                if (this.machineEntity.canPlaceItem(0, stackInSlot)) {
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
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(this.machineEntity.getLevel()), this.machineEntity.getBlockPos()), pPlayer, Registration.STONECUTTER_BLOCK.get());
    }

    @Override
    public void slotsChanged(Container pContainer) {
        ItemStack itemStack = this.phantomSlot.getItem(); // Get phantom slot
        this.setupRecipeList(itemStack);
    }

    private void setupRecipeList(ItemStack itemStack) {
        this.recipes.clear();
        if (!itemStack.isEmpty()) {
            this.recipes = this.machineEntity.getLevel().getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, new SingleRecipeInput(itemStack), this.machineEntity.getLevel());
        }
    }

    public List<RecipeHolder<StonecutterRecipe>> getRecipes() {
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
        ProcessEnhancement.LOGGER.info("Clicked: {}", pId);
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndexData.set(pId);
            //this.machineEntity.setCurrentRecipe(this.recipes.get(this.data.get(2)));
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
