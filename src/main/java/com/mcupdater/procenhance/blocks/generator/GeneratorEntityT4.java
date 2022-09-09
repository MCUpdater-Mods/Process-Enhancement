package com.mcupdater.procenhance.blocks.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.INDGENERATOR_BLOCKENTITY;

public class GeneratorEntityT4 extends GeneratorEntity {
    public GeneratorEntityT4(BlockPos blockPos, BlockState blockState) {
        super(INDGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_GENERATOR_PER_TICK.get() * 8);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.industrial_generator");
    }
}
