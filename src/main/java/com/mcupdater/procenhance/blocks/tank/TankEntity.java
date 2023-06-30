package com.mcupdater.procenhance.blocks.tank;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.capabilities.FluidResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public abstract class TankEntity extends AbstractConfigurableBlockEntity {

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

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        super.tick();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (this.name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        super.saveAdditional(compound);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new TankMenu(pContainerId, this.level, this.worldPosition, pPlayerInventory, pPlayer, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
