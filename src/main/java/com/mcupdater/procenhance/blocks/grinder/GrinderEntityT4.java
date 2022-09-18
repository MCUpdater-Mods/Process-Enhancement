package com.mcupdater.procenhance.blocks.grinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.GRINDERT4_BLOCKENTITY;

public class GrinderEntityT4 extends GrinderEntity {
    public GrinderEntityT4(BlockPos blockPos, BlockState blockState) {
        super(GRINDERT4_BLOCKENTITY.get(), blockPos, blockState, 8);
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.industrial_grinder");
    }
}
