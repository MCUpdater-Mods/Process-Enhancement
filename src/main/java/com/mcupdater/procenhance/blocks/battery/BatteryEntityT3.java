package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.BATTERYT3_ENTITY;

public class BatteryEntityT3 extends BatteryEntity {
    public BatteryEntityT3(BlockPos pPos, BlockState pState) {
        super(BATTERYT3_ENTITY.get(), pPos, pState);
        setup(40000);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.advanced_battery");
    }
}
