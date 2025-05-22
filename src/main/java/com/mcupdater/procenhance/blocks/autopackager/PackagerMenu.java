package com.mcupdater.procenhance.blocks.autopackager;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class PackagerMenu extends AbstractMachineMenu<PackagerEntity> {

    public static SimpleContainer patternSupply = new SimpleContainer(new ItemStack(Registration.SLATE_3x3.get(),1));

    public PackagerMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
        super((PackagerEntity) level.getBlockEntity(blockPos), Registration.AUTOPACKAGER_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
        patternSupply.addListener((container) -> {
            if (container.getContainerSize() > 0 && container.getItem(0) == ItemStack.EMPTY) {
                container.setItem(0, new ItemStack(Registration.SLATE_3x3.get(), 1));
            }
        });
    }

    public static PackagerMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        Level world = playerInv.player.level();
        PackagerEntity te = (PackagerEntity) world.getBlockEntity(pos);
        return new PackagerMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
    }

    @Override
    protected void addMachineSlots() {
        ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getInventory();
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 0, 62, 20));
        this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 1, 98, 20));
        this.addSlot(new Slot(this.patternSupply,0, 8,20));
        this.addSlotRange(resourceHandler.getInternalHandler(),2, 8, 51, 8, 18);
    }

    @Override
    public boolean stillValid(Player player) {
        return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> player.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
    }
}
