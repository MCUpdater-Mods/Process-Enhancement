package com.mcupdater.procenhance.blocks.solar_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.block.IMachineGuiProvider;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.InputOutputSettings;
import com.mcupdater.mculib.inventory.SideSetting;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class SolarEntity extends AbstractConfigurableBlockEntity implements IMachineGuiProvider {
	protected int energyPerTick;

	public SolarEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	protected void setup(int energyPerTick) {
		this.energyPerTick = energyPerTick;
		EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, energyPerTick * 10000, Integer.MAX_VALUE, false);
		for (Direction side : Direction.values()) {
			InputOutputSettings ioSetting = energyResourceHandler.getIOSettings(side);
			ioSetting.setInputSetting(SideSetting.DISABLED);
			energyResourceHandler.updateIOSettings(side, ioSetting);
		}
		this.configMap.put("power", energyResourceHandler);
	}

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
		if (!this.level.isClientSide) {
			boolean currentState = blockState.getValue(AbstractMachineBlock.ACTIVE);
			if (level.isDay() && !level.isRaining() && level.canSeeSky(blockPos.above())) {
				if (!currentState) {
					blockState = blockState.setValue(AbstractMachineBlock.ACTIVE, true);
					level.setBlock(blockPos, blockState, 3);
				}
				int added = energyStorage.getInternalHandler().receiveEnergy(this.energyPerTick, false);
			} else {
				if (currentState) {
					blockState = blockState.setValue(AbstractMachineBlock.ACTIVE, false);
					level.setBlock(blockPos, blockState, 3);
				}
			}
		}
		super.tick();
	}

	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr(Vec3.atCenterOf(this.worldPosition)) <= 64.0D;
		}
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new SolarMenu(containerId, this.level, this.worldPosition, playerInventory, player, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}
}