package com.mcupdater.procenhance.blocks.disenchanter;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.AbstractResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static com.mcupdater.procenhance.setup.Registration.DISENCHANTER_ENTITY;

public class DisenchanterEntity extends AbstractMachineBlockEntity {

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return DisenchanterEntity.this.workProgress;
                case 1:
                    return DisenchanterEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    DisenchanterEntity.this.workProgress = newValue;
                    break;
                case 1:
                    DisenchanterEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public DisenchanterEntity(BlockPos blockPos, BlockState blockState) {
        super(DISENCHANTER_ENTITY.get(), blockPos, blockState, 10000, Integer.MAX_VALUE, 0, 1);
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 5, new int[]{0,1,2,3,4}, new int[]{0,1,2}, new int[]{3,4}, this::stillValid);
        itemResourceHandler.setInsertFunction((slot, itemStack) -> {
            switch(slot) {
                case 0:
                    return itemStack.getItem().equals(Items.LAPIS_LAZULI);
                case 1:
                    return itemStack.getItem().equals(Items.BOOK);
                case 2:
                    return itemStack.isEnchanted() || (itemStack.hasTag() && itemStack.getTag().contains("StoredEnchantments") && itemStack.getTag().getList("StoredEnchantments", Tag.TAG_COMPOUND).size() > 1);
                default:
                    return false;
            }
        });
        this.configMap.put("items", itemResourceHandler);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.disenchanter");
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    protected boolean performWork() {
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        ItemStack lapisSlot = itemStorage.getItem(0);
        ItemStack bookSlot = itemStorage.getItem(1);
        ItemStack inputSlot = itemStorage.getItem(2);
        ItemStack outputSlot = itemStorage.getItem(3);
        ItemStack enchantedSlot = itemStorage.getItem(4);
        if (!lapisSlot.isEmpty() &&
                !bookSlot.isEmpty() &&
                !inputSlot.isEmpty() &&
                outputSlot.isEmpty() &&
                enchantedSlot.isEmpty() &&
                lapisSlot.getCount() >= (inputSlot.getItem().equals(Items.ENCHANTED_BOOK) ? 1 : inputSlot.getEnchantmentTags().size())) { // Verify all slot are in a proper state
            this.workTotal = 200 * (inputSlot.getItem().equals(Items.ENCHANTED_BOOK) ? 1 : inputSlot.getEnchantmentTags().size()); // 200 ticks per enchantment

        } else { // Reset work counters
            this.workTotal = 0;
            this.workProgress = 0;
        }
        if (this.workTotal != 0) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) { // Finish cycle
                ListTag enchantments = inputSlot.getEnchantmentTags();
                ItemStack newStack = inputSlot.copy();
                if (newStack.getItem().equals(Items.ENCHANTED_BOOK)) {
                    enchantments = new ListTag();
                    Tag enchantment = newStack.hasTag() ? newStack.getTag().getList("StoredEnchantments", Tag.TAG_COMPOUND).remove(0) : null;
                    enchantments.addTag(0, enchantment);
                } else {
                    newStack.removeTagKey("Enchantments");
                }
                itemStorage.setItem(3, newStack);
                ItemStack newBook = new ItemStack(Items.ENCHANTED_BOOK,1);
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("StoredEnchantments", enchantments);
                newBook.setTag(compoundTag);
                itemStorage.setItem(4, newBook);
                itemStorage.getItem(2).shrink(1);
                itemStorage.getItem(1).shrink(1);
                itemStorage.getItem(0).shrink(enchantments.size());
            }
        }
        return false;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new DisenchanterMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Map<String, AbstractResourceHandler> getConfigMap() {
        return this.configMap;
    }
}
