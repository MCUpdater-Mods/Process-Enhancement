package com.mcupdater.procenhance.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import static com.mcupdater.procenhance.setup.Registration.INDBATTERY_BLOCKENTITY;

public class BatteryEntityT4 extends BatteryEntity {
    public BatteryEntityT4(BlockPos pPos, BlockState pState) {
        super(INDBATTERY_BLOCKENTITY.get(), pPos, pState);
        setup(80000);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.industrial_battery");
    }
}
