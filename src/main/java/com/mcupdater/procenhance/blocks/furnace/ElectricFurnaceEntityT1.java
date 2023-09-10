package com.mcupdater.procenhance.blocks.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.FURNACET1_BLOCKENTITY;

public class ElectricFurnaceEntityT1 extends ElectricFurnaceEntity {
    public ElectricFurnaceEntityT1(BlockPos blockPos, BlockState blockState) {
        super(FURNACET1_BLOCKENTITY.get(), blockPos, blockState, 1);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.processenhancement.basic_furnace");
    }
}
