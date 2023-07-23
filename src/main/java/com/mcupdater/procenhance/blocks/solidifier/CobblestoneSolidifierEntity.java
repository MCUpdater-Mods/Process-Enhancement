package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.mculib.helpers.InventoryHelper;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CobblestoneSolidifierEntity extends AbstractSolidifierEntity {

    public CobblestoneSolidifierEntity(BlockPos blockPos, BlockState blockState) {
        super(Registration.COBBLESTONESOLIDIFIER_ENTITY.get(), blockPos, blockState);
        this.workTotal=40;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.cobblestone_solidifier");
    }

    @Override
    protected boolean performWork() {
        IItemHandler itemHandler = new InvWrapper(itemResourceHandler);
        if (itemHandler.getStackInSlot(0).getCount() < itemHandler.getSlotLimit(0)) {
            this.workProgress++;
            if (this.workProgress >= this.workTotal) {
                this.workProgress = 0;
                ItemStack cobbleStack = new ItemStack(Items.COBBLESTONE, 1);
                itemHandler.insertItem(0, cobbleStack, false);
            }
            return true;
        } else {
            return false;
        }
    }
}
