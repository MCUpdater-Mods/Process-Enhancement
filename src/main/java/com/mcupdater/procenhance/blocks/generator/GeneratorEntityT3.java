package com.mcupdater.procenhance.blocks.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.ADVGENERATOR_BLOCKENTITY;

public class GeneratorEntityT3 extends GeneratorEntity {
    public GeneratorEntityT3(BlockPos blockPos, BlockState blockState) {
        super(ADVGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_GENERATOR_PER_TICK.get() * 4);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_generator");
    }
}
