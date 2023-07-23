package com.mcupdater.procenhance.blocks.solidifier;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.AbstractResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class AbstractSolidifierEntity extends AbstractMachineBlockEntity {

    protected final ItemResourceHandler itemResourceHandler;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return AbstractSolidifierEntity.this.workProgress;
                case 1:
                    return AbstractSolidifierEntity.this.workTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    AbstractSolidifierEntity.this.workProgress = newValue;
                    break;
                case 1:
                    AbstractSolidifierEntity.this.workTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public AbstractSolidifierEntity(BlockEntityType<?> pType, BlockPos blockPos, BlockState blockState) {
        super(pType, blockPos, blockState, Math.max(5000, Config.SOLIDIFIER_ENERGY_PER_TICK.get() * 1000), Integer.MAX_VALUE, Config.SOLIDIFIER_ENERGY_PER_TICK.get(), 1);
        itemResourceHandler = new ItemResourceHandler(this.level, 1, new int[]{0}, new int[]{}, new int[]{0}, this::stillValid);
        this.configMap.put("items", itemResourceHandler);
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new SolidifierMenu(windowId, this.level, this.worldPosition, inventory, player, data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Map<String, AbstractResourceHandler> getConfigMap() {
        return this.configMap;
    }
}
