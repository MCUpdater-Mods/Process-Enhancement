package com.mcupdater.procenhance.blocks.biogenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Config.BASIC_BIOGENERATOR_PER_TICK;
import static com.mcupdater.procenhance.setup.Registration.BASICBIOGENERATOR_BLOCKENTITY;

public class BiogeneratorEntityT1 extends BiogeneratorEntity {

    public BiogeneratorEntityT1(BlockPos blockPos, BlockState blockState) {
        super(BASICBIOGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        setup(BASIC_BIOGENERATOR_PER_TICK.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.basic_biogenerator");
    }
}
