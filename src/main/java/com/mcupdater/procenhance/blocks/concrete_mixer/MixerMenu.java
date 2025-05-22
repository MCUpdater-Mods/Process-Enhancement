package com.mcupdater.procenhance.blocks.concrete_mixer;

import com.mcupdater.mculib.block.AbstractMachineMenu;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.MachineInputSlot;
import com.mcupdater.mculib.inventory.MachineOutputSlot;
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

public class MixerMenu extends AbstractMachineMenu<MixerEntity> {

	public MixerMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
		super((MixerEntity) level.getBlockEntity(blockPos), Registration.CONCRETEMIXER_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
	}

	public static MixerMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		MixerEntity te = (MixerEntity) world.getBlockEntity(pos);
		return new MixerMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
	}

	@Override
	protected void addMachineSlots() {
		ItemResourceHandler resourceHandler = (ItemResourceHandler) this.machineEntity.getInventory();
		this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 0, 41, 26));
		this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 1, 41, 45));
		this.addSlot(new MachineInputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 2, 62, 37));
		this.addSlot(new MachineOutputSlot(this.machineEntity, resourceHandler.getInternalHandler(), 3, 98, 37));
	}

	@Override
	public boolean stillValid(Player player) {
		return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> player.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
	}
}
