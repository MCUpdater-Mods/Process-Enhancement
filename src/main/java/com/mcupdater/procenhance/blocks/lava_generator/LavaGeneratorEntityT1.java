package com.mcupdater.procenhance.blocks.lava_generator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_LAVA_GENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.BASICLAVAGENERATOR_BLOCKENTITY;

public class LavaGeneratorEntityT1 extends LavaGeneratorEntity {

    public LavaGeneratorEntityT1(BlockPos blockPos, BlockState blockState) {
        super(BASICLAVAGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_LAVA_GENERATOR_PER_TICK.get());
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_lava_generator");
    }
}
