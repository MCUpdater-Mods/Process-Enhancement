package com.mcupdater.procenhance.blocks.grinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.GRINDERT2_BLOCKENTITY;

public class GrinderEntityT2 extends GrinderEntity {
    public GrinderEntityT2(BlockPos blockPos, BlockState blockState) {
        super(GRINDERT2_BLOCKENTITY.get(), blockPos, blockState, 2);
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.intermediate_grinder");
    }
}
