package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_BIOGENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.BIOGENERATORT2_ENTITY;

public class BiogeneratorEntityT2 extends BiogeneratorEntity {
    public BiogeneratorEntityT2(BlockPos blockPos, BlockState blockState) {
        super(BIOGENERATORT2_ENTITY.get(), blockPos, blockState);
        setup(BASIC_BIOGENERATOR_PER_TICK.get() * 2);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.intermediate_biogenerator");
    }
}
