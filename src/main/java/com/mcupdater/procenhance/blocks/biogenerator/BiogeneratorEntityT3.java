package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_BIOGENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.BIOGENERATORT3_ENTITY;

public class BiogeneratorEntityT3 extends BiogeneratorEntity {
    public BiogeneratorEntityT3(BlockPos blockPos, BlockState blockState) {
        super(BIOGENERATORT3_ENTITY.get(), blockPos, blockState);
        setup(BASIC_BIOGENERATOR_PER_TICK.get() * 4);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_biogenerator");
    }
}
