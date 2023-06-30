package com.mcupdater.procenhance.blocks.miner;

import net.minecraft.core.BlockPos;

import java.util.Comparator;

public class BlockDistanceComparator implements Comparator<BlockPos> {

    BlockPos origin;
    public BlockDistanceComparator(BlockPos origin) {
        this.origin = origin;
    }

    @Override
    public int compare(BlockPos o1, BlockPos o2) {
        return Integer.compare(origin.distManhattan(o1), origin.distManhattan(o2));
    }
}
