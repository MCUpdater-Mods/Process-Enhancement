package com.mcupdater.procenhance.blocks.grinder;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Objects;

public class GrinderMenu extends AbstractMachineMenu<GrinderEntity> {

    public GrinderMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, Component> directionComponentMap) {
        super((GrinderEntity) level.getBlockEntity(blockPos), Registration.GRINDER_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(this.machineEntity.getLevel()), this.machineEntity.getBlockPos()), playerIn, Registration.GRINDER_BLOCK.get());
    }

}
