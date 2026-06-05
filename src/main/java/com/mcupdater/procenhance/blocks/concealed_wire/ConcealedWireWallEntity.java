package com.mcupdater.procenhance.blocks.concealed_wire;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ConcealedWireWallEntity extends ConcealedWireEntity {

	public ConcealedWireWallEntity(BlockPos pos, BlockState state) {
		super(Registration.CONCEALEDWIRE_WALL_ENTITY.get(), pos, state);
		this.mimic = this.getDefaultBlockState();
	}

	private BlockState getDefaultBlockState() {
		return Registration.CONCEALEDWIRE_WALL_BLOCK.get().defaultBlockState();
	}
}
