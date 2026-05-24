package com.mcupdater.procenhance.blocks.lantern;

import com.mcupdater.mculib.block.AbstractMachineMenu;
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

public class LanternMenu extends AbstractMachineMenu<LanternEntity> {

	public LanternMenu(int id, Level level, BlockPos blockPos, Inventory inventory, Player player, ContainerData data, Map<Direction, String> adjacentNames) {
		super((LanternEntity) level.getBlockEntity(blockPos), Registration.ELECTRIC_LANTERN_MENU.get(), id, level, blockPos, inventory, player, data, adjacentNames);
	}

	public static LanternMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
		BlockPos pos = extraData.readBlockPos();
		Level world = playerInv.player.level();
		LanternEntity te = (LanternEntity) world.getBlockEntity(pos);
		return new LanternMenu(containerId, world, pos, playerInv, playerInv.player, new SimpleContainerData(2), DataHelper.readDirectionMap(extraData));
	}

	@Override
	public boolean stillValid(Player player) {
		return ContainerLevelAccess.create(machineEntity.getLevel(), machineEntity.getBlockPos()).evaluate((level, blockPos) -> player.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true);
	}

	@Override
	protected void addMachineSlots() {}
}
