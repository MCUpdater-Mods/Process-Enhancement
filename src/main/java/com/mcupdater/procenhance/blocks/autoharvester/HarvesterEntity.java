package com.mcupdater.procenhance.blocks.autoharvester;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.helpers.RenderHelper;
import com.mcupdater.procenhance.blocks.miner.BlockDistanceComparator;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import static com.mcupdater.procenhance.setup.Registration.HARVESTER_ENTITY;

public class HarvesterEntity extends AbstractMachineBlockEntity {

    private final ItemResourceHandler itemResourceHandler;
    private List<BlockPos> harvestableBlocks = new ArrayList<>();
    private int tick;
    private Queue<ItemStack> internalBuffer = new LinkedList<>();

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 0;
        }
    };

    public HarvesterEntity(BlockPos blockPos, BlockState blockState) {
        super(HARVESTER_ENTITY.get(), blockPos, blockState, Config.AUTOHARVESTER_ENERGY_PER_TICK.get() * 1000, Integer.MAX_VALUE, Config.AUTOHARVESTER_ENERGY_PER_TICK.get(), 1);
        int[] slots = IntStream.rangeClosed(0,5).toArray();
        itemResourceHandler = new ItemResourceHandler(this.level, 6, slots, IntStream.empty().toArray(), slots, this::stillValid);
        this.configMap.put("items", itemResourceHandler);
    }

    private Boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    private void buildHarvestablesList() {
        Direction facing = this.getBlockState().getValue(HarvesterBlock.FACING);
        BlockPos startPos = this.worldPosition.relative(facing.getOpposite(),9).relative(facing.getClockWise(),4);
        BlockPos endPos = this.worldPosition.relative(facing.getOpposite(),1).relative(facing.getCounterClockWise(),4);
        int y = startPos.getY();
        for (int x = Math.min(startPos.getX(),endPos.getX()); x <= Math.max(startPos.getX(), endPos.getX()); x++) {
            for (int z = Math.min(startPos.getZ(), endPos.getZ()); z <= Math.max(startPos.getZ(), endPos.getZ()); z++) {
                BlockPos blockPos = new BlockPos(x,y,z);
                BlockState state = level.getBlockState(blockPos);
                if (state.getBlock() instanceof BushBlock || state.getBlock().equals(Blocks.MELON) || state.getBlock().equals(Blocks.PUMPKIN) || state.getBlock().equals(Blocks.KELP)) {
                    harvestableBlocks.add(blockPos);
                }
                if (state.getBlock() instanceof BambooStalkBlock || state.getBlock() instanceof SugarCaneBlock || state.getBlock() instanceof CactusBlock) {
                    harvestableBlocks.add(blockPos.above());
                }
            }
        }
        harvestableBlocks.sort(new BlockDistanceComparator(this.worldPosition));
    }

    @Override
    protected boolean performWork() {
        if (!level.isClientSide()) {
            // Clear the internal buffer
            if (!this.internalBuffer.isEmpty()) {
                //ProcessEnhancement.LOGGER.info("Buffer not empty - transferring contents");
                List<ItemStack> tempBuffer = this.internalBuffer.stream().toList();
                for (ItemStack stack : tempBuffer) {
                    boolean success = false;
                    for (int slot = 0; slot < this.itemResourceHandler.getInternalHandler().getSlots(); slot++) {
                        ItemStack result = this.itemResourceHandler.getInternalHandler().insertItem(slot, stack, false);
                        if (result == ItemStack.EMPTY) {
                            internalBuffer.remove(stack);
                            success = true;
                            break;
                        }
                    }
                    if (success = true) {
                        break;
                    }
                    internalBuffer.remove(stack);
                    internalBuffer.add(stack);
                }
            }

            // If collection buffer still has contents, turn off harvester
            if (!internalBuffer.isEmpty()){
                //ProcessEnhancement.LOGGER.info("Buffer not empty - turning off harvester");
                return false;
            }

            // When tick delay reaches 0, do work
            if (tick == 0) {
                // Check if queue is empty and rebuild if needed
                if (this.harvestableBlocks.isEmpty()) {
                    buildHarvestablesList();
                }
                // If queue is not empty, try harvesting
                if (!this.harvestableBlocks.isEmpty()) {
                    BlockPos toHarvest = this.harvestableBlocks.removeFirst();
                    BlockState state = level.getBlockState(toHarvest);
                    if (readyToFullHarvest(state)){
                        List<ItemStack> drops = state.getDrops(new LootParams.Builder((ServerLevel) this.level).withParameter(LootContextParams.ORIGIN,toHarvest.getBottomCenter()).withParameter(LootContextParams.TOOL,new ItemStack(Items.NETHERITE_HOE)));
                        level.setBlock(toHarvest, !state.is(Blocks.KELP) ? Blocks.AIR.defaultBlockState() : Blocks.WATER.defaultBlockState(), 3);
                        RenderHelper.sendParticles((ServerLevel) level, ParticleTypes.INSTANT_EFFECT, toHarvest.getX() + 0.5D, toHarvest.getY() + 0.1D, toHarvest.getZ() + 0.5D, 3,0,0, 0, 0);
                        level.playSound(null, toHarvest, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1, 1);
                        internalBuffer.addAll(drops);
                        //ProcessEnhancement.LOGGER.info("Harvested: {} @ {} Drops: {}", state.getBlock(), toHarvest, drops);
                        tick += 20;
                        return true;
                    } else if (state.getBlock() instanceof SweetBerryBushBlock berryBushBlock && state.getValue(SweetBerryBushBlock.AGE) == SweetBerryBushBlock.MAX_AGE) {
                        int count = 2 + level.random.nextInt(2);
                        internalBuffer.add(new ItemStack(Items.SWEET_BERRIES, count));
                        level.setBlock(toHarvest, state.setValue(SweetBerryBushBlock.AGE, 1), 2);
                        level.playSound(null, toHarvest, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                        tick += 20;
                        return true;
                    }
                }
            } else {
                tick--;
                return true;
            }
            /*
            if (!itemResourceHandler.getItem(0).isEmpty() && itemResourceHandler.getItem(1).isEmpty()) {
                this.workProgress++;
                if (this.workProgress >= this.workTotal) {
                    ItemStack inputStack = itemResourceHandler.getItem(0);
                    for (int patSlot = 2; patSlot < itemResourceHandler.getContainerSize(); patSlot++) {
                        ItemStack patternStack = itemResourceHandler.getItem(patSlot);
                        if (patternStack.getItem() instanceof AbstractPatternItem pattern) {
                            ItemStack result = pattern.doCraft(inputStack, this.level);
                            if (result != ItemStack.EMPTY) {
                                itemResourceHandler.setItem(1, result);
                                this.workProgress = 0;
                                return true;
                            }
                        }
                    }
                    itemResourceHandler.setItem(1, itemResourceHandler.getItem(0).split(itemResourceHandler.getItem(0).getCount()));
                } else {
                    return true;
                }
            } else {
                workProgress = 0;
                return false;
            }
             */
        }
        return false;
    }

    public boolean readyToFullHarvest(BlockState state) {
        return
                (state.getBlock() instanceof CropBlock && ((CropBlock) state.getBlock()).isMaxAge(state)) ||
                        state.getBlock().equals(Blocks.MELON) ||
                        state.getBlock().equals(Blocks.PUMPKIN) ||
                        state.getBlock() instanceof BambooStalkBlock ||
                        state.getBlock() instanceof SugarCaneBlock ||
                        state.getBlock() instanceof CactusBlock ||
                        (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == NetherWartBlock.MAX_AGE) ||
                        state.getBlock() instanceof KelpBlock
                ;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.autoharvester");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new HarvesterMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return this.itemResourceHandler;
    }
}
