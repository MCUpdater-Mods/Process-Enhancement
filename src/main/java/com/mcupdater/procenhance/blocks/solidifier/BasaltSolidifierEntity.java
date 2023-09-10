package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BasaltSolidifierEntity extends AbstractSolidifierEntity {

    public BasaltSolidifierEntity(BlockPos blockPos, BlockState blockState) {
        super(Registration.BASALTSOLIDIFIER_ENTITY.get(), blockPos, blockState);
        this.workTotal=40;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.basalt_solidifier");
    }

    @Override
    protected boolean performWork() {
        IItemHandler itemHandler = new InvWrapper(itemResourceHandler);
        if (itemHandler.getStackInSlot(0).getCount() < itemHandler.getSlotLimit(0)) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                this.workProgress = 0;
                ItemStack cobbleStack = new ItemStack(Items.BASALT, 1);
                itemHandler.insertItem(0, cobbleStack, false);
            }
            return true;
        } else {
            return false;
        }
    }
}
