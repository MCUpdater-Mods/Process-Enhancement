package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.BATTERYT1_ENTITY;

public class BatteryEntityT1 extends BatteryEntity {

    public BatteryEntityT1(BlockPos pPos, BlockState pState) {
        super(BATTERYT1_ENTITY.get(), pPos, pState);
        setup(10000);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.basic_battery");
    }
}
