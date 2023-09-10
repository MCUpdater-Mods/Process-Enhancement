package com.mcupdater.procenhance.blocks.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.FURNACET3_BLOCKENTITY;

public class ElectricFurnaceEntityT3 extends ElectricFurnaceEntity {
    public ElectricFurnaceEntityT3(BlockPos blockPos, BlockState blockState) {
        super(FURNACET3_BLOCKENTITY.get(), blockPos, blockState, 4);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_furnace");
    }
}
