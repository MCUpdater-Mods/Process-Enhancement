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

import static com.mcupdater.procenhance.setup.Registration.ELECTRIC_LANTERN_ENTITY;
import static com.mcupdater.procenhance.setup.Registration.NETHER_ELECTRIC_LANTERN_ENTITY;

public class NetherLanternEntity extends AbstractMachineBlockEntity {

	private int tick = 0;

	public ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return NetherLanternEntity.this.workProgress;
				case 1:
					return NetherLanternEntity.this.workTotal;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int newValue) {
			switch (index) {
				case 0:
					NetherLanternEntity.this.workProgress = newValue;
				case 1:
					NetherLanternEntity.this.workTotal = newValue;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public NetherLanternEntity(BlockPos blockPos, BlockState blockState) {
		super(NETHER_ELECTRIC_LANTERN_ENTITY.get(), blockPos, blockState, 1000, 5, 1, 1);
	}

	@Override
	public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
		if (!this.level.isClientSide()) {
			EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
			if (energyStorage.getStoredEnergy() >= 1) {
				if (this.performWork()) {
					energyStorage.getInternalHandler().extractEnergy(1, false);
				}
			} else {
				pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, false);
				pLevel.setBlock(pPos, pBlockState, 3);
			}

			super.tick();
		}
	}

	@Override
	protected boolean performWork() {
		if (tick >= 20) {
			tick = 0;
			BlockState blockState = getBlockState().setValue(AbstractMachineBlock.ACTIVE, true);
			this.level.setBlock(this.worldPosition, blockState, 3);
			return true;
		} else {
			tick++;
		}
		return false;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.processenhancement.electric_lantern");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new LanternMenu(containerId, this.level, this.worldPosition, playerInventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
	}
}
