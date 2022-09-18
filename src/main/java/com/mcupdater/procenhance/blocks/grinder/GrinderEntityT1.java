package com.mcupdater.procenhance.blocks.grinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.GRINDERT1_BLOCKENTITY;

public class GrinderEntityT1 extends GrinderEntity {
    public GrinderEntityT1(BlockPos blockPos, BlockState blockState) {
        super(GRINDERT1_BLOCKENTITY.get(), blockPos, blockState, 1);
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_grinder");
    }
}
