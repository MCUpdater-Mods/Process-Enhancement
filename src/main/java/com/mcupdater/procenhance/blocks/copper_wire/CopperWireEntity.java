package com.mcupdater.procenhance.blocks.copper_wire;

import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CopperWireEntity extends BlockEntity {
    private final EnergyResourceHandler energyStorage;

    public CopperWireEntity(BlockPos pPos, BlockState pState) {
        super(Registration.COPPERWIRE_BLOCKENTITY.get(), pPos, pState);
        this.energyStorage = new EnergyResourceHandler(this.level, 5000, 5000, false);
    }

    public void tick(Level pLevel, BlockPos pPos) {
        if (this.energyStorage.tickHandler(pLevel, pPos))
            this.setChanged();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (energyStorage.getCapability(cap, side).isPresent())
            return energyStorage.getCapability(cap, side);

        return super.getCapability(cap, side);
    }
}
