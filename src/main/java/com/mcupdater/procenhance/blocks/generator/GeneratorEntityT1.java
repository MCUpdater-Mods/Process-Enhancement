package com.mcupdater.procenhance.blocks.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.BASICGENERATOR_BLOCKENTITY;

public class GeneratorEntityT1 extends GeneratorEntity {

    public GeneratorEntityT1(BlockPos blockPos, BlockState blockState) {
        super(BASICGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_GENERATOR_PER_TICK.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.basic_generator");
    }
}
