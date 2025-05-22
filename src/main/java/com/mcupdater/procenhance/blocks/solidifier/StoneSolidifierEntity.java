package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

public class StoneSolidifierEntity extends AbstractSolidifierEntity {

    public StoneSolidifierEntity(BlockPos blockPos, BlockState blockState) {
        super(Registration.STONESOLIDIFIER_ENTITY.get(), blockPos, blockState);
        this.workTotal=40;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.stone_solidifier");
    }

    @Override
    protected boolean performWork() {
        IItemHandler itemHandler = itemResourceHandler.getInternalHandler();
        if (itemHandler.getStackInSlot(0).isEmpty() || itemHandler.getStackInSlot(0).getCount() < itemHandler.getStackInSlot(0).getMaxStackSize()) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                this.workProgress = 0;
                ItemStack outputStack = new ItemStack(Items.STONE, 1);
                itemHandler.insertItem(0, outputStack, false);
            }
            return true;
        } else {
            return false;
        }
    }
}
