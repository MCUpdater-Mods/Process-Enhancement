package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.INTBATTERY_BLOCKENTITY;

public class BatteryEntityT2 extends BatteryEntity {
    public BatteryEntityT2(BlockPos pPos, BlockState pState) {
        super(INTBATTERY_BLOCKENTITY.get(), pPos, pState);
        setup(20000);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.intermediate_battery");
    }
}
