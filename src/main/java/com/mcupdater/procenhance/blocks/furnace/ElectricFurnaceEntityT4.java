package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.FURNACET4_BLOCKENTITY;

public class ElectricFurnaceEntityT4 extends ElectricFurnaceEntity {
    public ElectricFurnaceEntityT4(BlockPos blockPos, BlockState blockState) {
        super(FURNACET4_BLOCKENTITY.get(), blockPos, blockState, 8);
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.industrial_furnace");
    }
}