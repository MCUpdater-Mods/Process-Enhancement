package com.mcupdater.procenhance.blocks.autoharvester;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

import java.util.Map;

public class HarvesterMenu extends AbstractMachineMenu<HarvesterEntity> {

    public HarvesterMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
        super((HarvesterEntity) level.getBlockEntity(blockPos), Registration.HARVESTER_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
    }

    public static HarvesterMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        Level world = playerInv.player.level();
        HarvesterEntity te = (HarvesterEntity) world.getBlockEntity(pos);
        return new HarvesterMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
    }

    @Override
    protected void addMachineSlots() {
        ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getInventory();
        //this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 0, 62, 20));
        //this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 1, 98, 20));
        //this.addSlotRange(resourceHandler.getInternalHandler(),2, 8, 54, 8, 18);
        addSlotBox(resourceHandler.getInternalHandler(), 0, 62, 26, 3, 18, 2, 18);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }
}
