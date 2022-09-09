package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.ADVBATTERY_BLOCKENTITY;

public class BatteryEntityT3 extends BatteryEntity {
    public BatteryEntityT3(BlockPos pPos, BlockState pState) {
        super(ADVBATTERY_BLOCKENTITY.get(), pPos, pState);
        setup(40000);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.advanced_battery");
    }
}
