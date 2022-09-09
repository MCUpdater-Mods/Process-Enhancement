package com.mcupdater.procenhance.blocks.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.INTERGENERATOR_BLOCKENTITY;

public class GeneratorEntityT2 extends GeneratorEntity {
    public GeneratorEntityT2(BlockPos blockPos, BlockState blockState) {
        super(INTERGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_GENERATOR_PER_TICK.get() * 2);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.intermediate_generator");
    }
}
