package com.mcupdater.procenhance.blocks.miner;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public abstract class MinerBlock extends AbstractMachineBlock {
    public MinerBlock(Properties properties) {
        super(properties);
    }

    public static Properties defaultProperties() {
        return Properties.of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL)
                .strength(20.0f, 200.0f)
                .requiresCorrectToolForDrops();
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pState.getValue(ACTIVE)) {
            double x = (double) pPos.getX() + 0.5D;
            double y = (double) pPos.getY();
            double z = (double) pPos.getZ() + 0.5D;
            pLevel.playLocalSound(x, y, z, Registration.MACHINE_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (pStack.isEnchanted() && blockEntity instanceof MinerEntity miner) {
            ItemEnchantments enchantments = pStack.getTagEnchantments();
            miner.setEnchantments(enchantments);
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof MinerEntity minerEntity) {
                Containers.dropContents(level, blockPos, minerEntity.getItemResourceHandler());
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(oldState, level, blockPos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof MinerEntity miner) {
                miner.tick(lvl, pos, state);
            }
        };
    }
}
