package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_BIOGENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.INDBIOGENERATOR_BLOCKENTITY;

public class BiogeneratorEntityT4 extends BiogeneratorEntity {
    public BiogeneratorEntityT4(BlockPos blockPos, BlockState blockState) {
        super(INDBIOGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_BIOGENERATOR_PER_TICK.get() * 8);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.industrial_biogenerator");
    }
}
