package com.mcupdater.procenhance.blocks.lantern;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.COPPER_ELECTRIC_LANTERN_ENTITY;
import static com.mcupdater.procenhance.setup.Registration.ELECTRIC_LANTERN_ENTITY;

public class CopperLanternEntity extends LanternEntity {

	public CopperLanternEntity(BlockPos blockPos, BlockState blockState) {
		super(COPPER_ELECTRIC_LANTERN_ENTITY.get(), blockPos, blockState);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.electric_lantern");
	}
}
