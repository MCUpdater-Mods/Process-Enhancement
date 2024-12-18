package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.blocks.generator.GeneratorEntity;
import com.mcupdater.procenhance.blocks.generator.GeneratorMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ElectricFurnaceMenu extends AbstractMachineMenu<ElectricFurnaceEntity> {

    public ElectricFurnaceMenu(int windowId, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> directionComponentMap) {
        super((ElectricFurnaceEntity) level.getBlockEntity(blockPos), Registration.FURNACE_MENU.get(), windowId, level, blockPos, inventory, player, data, directionComponentMap);
    }

    public static ElectricFurnaceMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        Level world = playerInv.player.level();
        ElectricFurnaceEntity te = (ElectricFurnaceEntity) world.getBlockEntity(pos);
        return new ElectricFurnaceMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

}
