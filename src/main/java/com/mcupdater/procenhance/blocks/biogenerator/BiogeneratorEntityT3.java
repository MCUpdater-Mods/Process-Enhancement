package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_BIOGENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.ADVBIOGENERATOR_BLOCKENTITY;

public class BiogeneratorEntityT3 extends BiogeneratorEntity {
    public BiogeneratorEntityT3(BlockPos blockPos, BlockState blockState) {
        super(ADVBIOGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_BIOGENERATOR_PER_TICK.get() * 4);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.advanced_biogenerator");
    }
}
