package com.mcupdater.procenhance.blocks.lava_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_LAVA_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.INDLAVAGENERATOR_BLOCKENTITY;

public class LavaGeneratorEntityT4 extends LavaGeneratorEntity {
    public LavaGeneratorEntityT4(BlockPos blockPos, BlockState blockState) {
        super(INDLAVAGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_LAVA_GENERATOR_PER_TICK.get() * 8);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.industrial_lava_generator");
    }
}
