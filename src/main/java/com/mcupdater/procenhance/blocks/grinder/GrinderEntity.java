package com.mcupdater.procenhance.blocks.grinder;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.mcupdater.procenhance.setup.Registration.GRINDER_BLOCKENTITY;

public class GrinderEntity extends AbstractMachineBlockEntity {
    private GrinderRecipe currentRecipe = null;

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

    public GrinderEntity(BlockPos blockPos, BlockState blockState) {
        super(GRINDER_BLOCKENTITY.get(), blockPos, blockState, 20000, Integer.MAX_VALUE, Config.GRINDER_ENERGY_PER_TICK.get(), 1);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 2, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction((slot, itemStack) -> this.level.getRecipeManager().getAllRecipesFor(GrinderRecipe.Type.INSTANCE).stream().anyMatch(recipe -> Arrays.stream(recipe.getIngredients().get(0).getItems()).anyMatch(inputStack -> inputStack.sameItem(itemStack))));
        this.configMap.put("items", itemResourceHandler);
    }

    @Override
    protected boolean performWork() {
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        ItemStack inputStack = itemStorage.getItem(0);
        if (!inputStack.isEmpty()) {
            Recipe<?> recipe = this.level.getRecipeManager().getRecipeFor(GrinderRecipe.Type.INSTANCE, itemStorage, this.level).orElse(null);
            if (this.currentRecipe == null || !this.currentRecipe.equals(recipe)) {
                if (recipe != null) {
                    this.currentRecipe = (GrinderRecipe) recipe;
                    this.prizePool.clear();
                    this.maxOutput = 0;
                    for (Tuple<ItemStack, Integer> entry : currentRecipe.getOutputs()) {
                        this.prizePool.add(entry.getA().getItem());
                        this.maxOutput = Math.max(this.maxOutput, entry.getA().getCount());
                    }
                    this.workTotal = this.currentRecipe.getProcessTime();
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
                for (Tuple<ItemStack,Integer> tuple : this.currentRecipe.getOutputs()) {
                    ItemStack potentialPrize = tuple.getA();
                    for (int i = 0; i < tuple.getB(); i++) {
                        prizeList.add(potentialPrize);
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
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.grinder");
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
