package com.mcupdater.procenhance.blocks.lava_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_LAVA_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.LAVAGENERATORT3_ENTITY;

public class LavaGeneratorEntityT3 extends LavaGeneratorEntity {
    public LavaGeneratorEntityT3(BlockPos blockPos, BlockState blockState) {
        super(LAVAGENERATORT3_ENTITY.get(), blockPos, blockState);
        setup(BASIC_LAVA_GENERATOR_PER_TICK.get() * 4);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_lava_generator");
    }
}
