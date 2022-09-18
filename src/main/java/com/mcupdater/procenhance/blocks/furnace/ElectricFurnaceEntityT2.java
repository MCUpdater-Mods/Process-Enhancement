package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.FURNACET2_BLOCKENTITY;

public class ElectricFurnaceEntityT2 extends ElectricFurnaceEntity {
    public ElectricFurnaceEntityT2(BlockPos blockPos, BlockState blockState) {
        super(FURNACET2_BLOCKENTITY.get(), blockPos, blockState, 2);
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.intermediate_furnace");
    }
}
