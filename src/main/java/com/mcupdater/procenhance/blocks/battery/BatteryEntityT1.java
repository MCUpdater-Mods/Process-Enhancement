package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.BASICBATTERY_BLOCKENTITY;

public class BatteryEntityT1 extends BatteryEntity {

    public BatteryEntityT1(BlockPos pPos, BlockState pState) {
        super(BASICBATTERY_BLOCKENTITY.get(), pPos, pState);
        setup(10000);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_battery");
    }
}
