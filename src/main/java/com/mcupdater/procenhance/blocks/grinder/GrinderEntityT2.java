package com.mcupdater.procenhance.blocks.grinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.GRINDERT2_ENTITY;

public class GrinderEntityT2 extends GrinderEntity {
    public GrinderEntityT2(BlockPos blockPos, BlockState blockState) {
        super(GRINDERT2_ENTITY.get(), blockPos, blockState, 2);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.processenhancement.intermediate_grinder");
    }
}
