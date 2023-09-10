package com.mcupdater.procenhance.blocks.grinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.GRINDERT3_BLOCKENTITY;

public class GrinderEntityT3 extends GrinderEntity {
    public GrinderEntityT3(BlockPos blockPos, BlockState blockState) {
        super(GRINDERT3_BLOCKENTITY.get(), blockPos, blockState, 4);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_grinder");
    }
}
