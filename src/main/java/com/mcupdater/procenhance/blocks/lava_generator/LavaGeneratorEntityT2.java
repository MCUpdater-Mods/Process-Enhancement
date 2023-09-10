package com.mcupdater.procenhance.blocks.lava_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_LAVA_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.INTERLAVAGENERATOR_BLOCKENTITY;

public class LavaGeneratorEntityT2 extends LavaGeneratorEntity {
    public LavaGeneratorEntityT2(BlockPos blockPos, BlockState blockState) {
        super(INTERLAVAGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_LAVA_GENERATOR_PER_TICK.get() * 2);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.intermediate_lava_generator");
    }
}
