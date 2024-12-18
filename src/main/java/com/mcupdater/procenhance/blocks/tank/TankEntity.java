package com.mcupdater.procenhance.blocks.tank;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IMachineGuiProvider;
import com.mcupdater.mculib.capabilities.FluidResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public abstract class TankEntity extends AbstractConfigurableBlockEntity implements IMachineGuiProvider {

    public TankEntity(BlockEntityType<?> pType, BlockPos blockPos, BlockState blockState, int multiplier) {
        super(pType, blockPos, blockState);
        FluidResourceHandler fluidResourceHandler = new FluidResourceHandler(this.level, player -> true);
        fluidResourceHandler.addTank(new FluidTank(10000 * multiplier, fluidStack -> true), true, true);
        this.configMap.put("fluids", fluidResourceHandler);
    }

    private Boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        super.tick();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new TankMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
