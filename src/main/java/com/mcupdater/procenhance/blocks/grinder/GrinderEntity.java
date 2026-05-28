package com.mcupdater.procenhance.blocks.grinder;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineContainer;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.RecipeHelper;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class GrinderEntity extends AbstractMachineBlockEntity {
    private RecipeHolder<GrinderRecipe> currentRecipe = null;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return GrinderEntity.this.workProgress;
                case 1:
                    return GrinderEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    GrinderEntity.this.workProgress = newValue;
                    break;
                case 1:
                    GrinderEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private Set<Item> prizePool = new HashSet<>();
    private int maxOutput = 0;

    public GrinderEntity(BlockEntityType<?> pType, BlockPos blockPos, BlockState blockState, int multiplier) {
        super(pType, blockPos, blockState, Config.GRINDER_ENERGY_PER_TICK.get() * 1000 * multiplier, Integer.MAX_VALUE, Config.GRINDER_ENERGY_PER_TICK.get(), multiplier);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 2, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction((slot, itemStack) -> this.level.getRecipeManager().getAllRecipesFor(Registration.GRINDER_RECIPE.get()).stream().anyMatch(recipe -> Arrays.stream(recipe.value().getIngredients().get(0).getItems()).anyMatch(inputStack -> ItemStack.isSameItem(inputStack,itemStack))));
        this.configMap.put("items", itemResourceHandler);
    }

    @Override
    protected boolean performWork() {
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        ItemStack inputStack = itemStorage.getItem(0);
        if (!inputStack.isEmpty()) {
            RecipeHolder<GrinderRecipe> recipe = RecipeHelper.getGrinderRecipe(this.level, new SingleRecipeInput(this.getInventory().getItem(0)));
            if (this.currentRecipe == null || !this.currentRecipe.equals(recipe)) {
                if (recipe != null) {
                    this.currentRecipe = recipe;
                    this.prizePool.clear();
                    this.maxOutput = 0;
                    for (Tuple<RecipeResult, Integer> entry : currentRecipe.value().getOutputs()) {
                        if (entry.getA().getItemStack().getItem() != Items.BARRIER) {
                            this.prizePool.add(entry.getA().getItemStack().getItem());
                            this.maxOutput = Math.max(this.maxOutput, entry.getA().getItemStack().getCount());
                        }
                    }
                    this.workTotal = this.currentRecipe.value().getProcessTime();
                }
                this.workProgress = 0;
            }
        } else {
            this.currentRecipe = null;
        }
        ItemStack outputSlot = itemStorage.getItem(1);
        if (this.currentRecipe != null && (outputSlot.isEmpty() || (this.prizePool.size() == 1 && this.prizePool.contains(outputSlot.getItem()) && outputSlot.getCount() <= (outputSlot.getMaxStackSize() - this.maxOutput)))) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                List<ItemStack> prizeList = new ArrayList<>();
                for (Tuple<RecipeResult,Integer> tuple : this.currentRecipe.value().getOutputs()) {
                    ItemStack potentialPrize = tuple.getA().getItemStack();
                    if (potentialPrize.getItem() != Blocks.BARRIER.asItem()) {
                        for (int i = 0; i < tuple.getB(); i++) {
                            prizeList.add(potentialPrize);
                        }
                    }
                }
                Collections.shuffle(prizeList);
                ItemStack prize = prizeList.get(this.level.getRandom().nextInt(prizeList.size()));
                if (outputSlot.isEmpty()) {
                    itemStorage.setItem(1, prize.copy());
                } else if (outputSlot.is(prize.getItem())){
                    outputSlot.grow(prize.getCount());
                }
                this.workProgress = 0;
                itemStorage.getItem(0).shrink(1);
                this.storedXP += this.currentRecipe.value().getExperience();
            }
            return true;
        }
        return false;
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new GrinderMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }
}
