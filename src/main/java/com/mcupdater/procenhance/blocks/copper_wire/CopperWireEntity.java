package com.mcupdater.procenhance.blocks.copper_wire;

import com.mcupdater.mculib.capabilities.PoweredBlockEntity;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CopperWireEntity extends PoweredBlockEntity {
    public CopperWireEntity(BlockPos pPos, BlockState pState) {
        super(Registration.COPPERWIRE_BLOCKENTITY.get(), pPos, pState, 5000, 5000, ReceiveMode.NOT_SHARED, SendMode.SEND_ALL);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        super.tick();
    }
}
