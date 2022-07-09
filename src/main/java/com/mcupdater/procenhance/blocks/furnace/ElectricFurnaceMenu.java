package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ElectricFurnaceMenu extends AbstractMachineMenu<ElectricFurnaceEntity> {

    public ElectricFurnaceMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data) {
        super((ElectricFurnaceEntity) level.getBlockEntity(blockPos), Registration.FURNACE_MENU.get(), windowId, level, blockPos, inventory, player, data);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(this.machineEntity.getLevel()), this.machineEntity.getBlockPos()), playerIn, Registration.FURNACE_BLOCK.get());
    }

}
