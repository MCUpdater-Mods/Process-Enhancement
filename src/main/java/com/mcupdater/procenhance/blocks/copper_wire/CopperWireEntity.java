package com.mcupdater.procenhance.blocks.copper_wire;

import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CopperWireEntity extends BlockEntity {
    private final EnergyResourceHandler energyStorage;

    public CopperWireEntity(BlockPos pPos, BlockState pState) {
        super(Registration.COPPERWIRE_ENTITY.get(), pPos, pState);
        this.energyStorage = new EnergyResourceHandler(this.level, 5000, 5000, false);
    }

    public void tick(Level pLevel, BlockPos pPos) {
        if (this.energyStorage.tickHandler(pLevel, pPos))
            this.setChanged();
    }

    public EnergyResourceHandler getEnergyResourceHandler() {
        return this.energyStorage;
    }

}
