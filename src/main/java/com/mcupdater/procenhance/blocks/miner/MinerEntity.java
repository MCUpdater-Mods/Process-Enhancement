package com.mcupdater.procenhance.blocks.miner;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.helpers.RenderHelper;
import com.mcupdater.procenhance.setup.Config;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class MinerEntity extends AbstractMachineBlockEntity {

    private final int range;
    private final ItemResourceHandler itemResourceHandler;
    private List<BlockPos> mineableBlocks = new ArrayList<>();
    private Queue<ItemStack> internalBuffer = new LinkedList<>();
    private int tick = 0;
    private ItemEnchantments enchantments = ItemEnchantments.EMPTY;
    public static BlockState LIGHT = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL,15);

    public MinerEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int multiplier, int range) {
        super(blockEntityType, blockPos, blockState, Config.MINER_ENERGY_PER_TICK.get() * 1000 * multiplier, Integer.MAX_VALUE, Config.MINER_ENERGY_PER_TICK.get(), multiplier);
        int[] slots = IntStream.rangeClosed(0,5).toArray();
        itemResourceHandler = new ItemResourceHandler(this.level, 6, slots, IntStream.empty().toArray(), slots, this::stillValid);
        this.configMap.put("items", itemResourceHandler);
        this.range = range;
    }

    private Boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void initialize() {
        int initx = this.worldPosition.getX();
        int inity = this.worldPosition.getY()-2;
        int initz = this.worldPosition.getZ();
        for (int y = inity; (y > level.getMinBuildHeight()); y--) {
            for (int x = (initx - range); (x <= (initx + range)); x++) {
                for (int z = (initz - range); (z <= (initz + range)); z++) {
                    BlockPos blockPos = new BlockPos(x,y,z);
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock().defaultDestroyTime() >= 0 && !state.isAir() && state.getFluidState().isEmpty()) {
                        mineableBlocks.add(blockPos);
                    }
                }
            }
        }
        mineableBlocks.sort(new BlockDistanceComparator(this.worldPosition));
    }

    @Override
    protected boolean performWork() {
        // Collect floating items in the mining area (i.e. spilled from broken chests) and add to the collection buffer
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, AABB.encapsulatingFullBlocks(worldPosition.below(2).east(range).north(range), worldPosition.west(range).south(range).below(512)), EntitySelector.ENTITY_STILL_ALIVE);
        for (ItemEntity item : items) {
            this.internalBuffer.add(item.getItem().copy());
            item.remove(Entity.RemovalReason.DISCARDED);
        }

        // Transfer contents of the collection buffer to the inventory and requeue if it cannot be transferred
        if (!this.internalBuffer.isEmpty()) {
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

        // If collection buffer still has contents, turn off miner
        if (!internalBuffer.isEmpty()){
            return false;
        }

        // When tick delay reaches 0, begin working
        if (tick == 0) {
            // If mining queue is empty, rebuild the queue
            if (this.mineableBlocks.isEmpty()) {
                initialize();
            }
            // If mining queue is not empty, mine
            if (!this.mineableBlocks.isEmpty()) {
                if (this.internalBuffer.isEmpty()) {
                    List<BlockPos> tempBlocks = this.mineableBlocks.stream().toList();
                    if (!tempBlocks.isEmpty()) {
                        BlockPos blockPos = tempBlocks.get(0);
                        BlockState state = level.getBlockState(blockPos);
                        ItemStack fakePickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
                        if (this.enchantments != null) {
                            for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : this.enchantments.entrySet()) {
                                fakePickaxe.enchant(enchantment.getKey(), enchantment.getIntValue());
                            }
                        }
                        List<ItemStack> miningResults = state.getDrops(new LootParams.Builder((ServerLevel) this.level)
                                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos))
                                .withParameter(LootContextParams.TOOL, fakePickaxe)
                        );
                        this.internalBuffer.addAll(miningResults);
                        this.level.removeBlock(blockPos, false);
                        RenderHelper.sendParticles((ServerLevel) level, ParticleTypes.REVERSE_PORTAL, blockPos.getX()+0.5D, blockPos.getY()+0.5D, blockPos.getZ()+0.5D, 5,0,0.1D,0,0.01D);
                        level.playSound(null,blockPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS,1.0f,1.0f);
                        if (blockPos.getX() % 5 == 0 && blockPos.getY() % 5 == 0 && blockPos.getZ() % 5 == 0) {
                            this.level.setBlock(blockPos, LIGHT, 3);
                        }
                        this.mineableBlocks.remove(blockPos);
                        tick += 200;
                        return true;
                    }
                }
            } else {
                // If mining queue is still empty, shut down and sleep longer
                tick -= 4000;
                return false;
            }
        } else if (tick > 0) {
            // Sleeping while on
            tick--;
            return true;
        } else {
            // Sleeping while off
            tick++;
            return false;
        }
        return false;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.loadAdditional(compound, pRegistries);
        if (compound.contains("buffer", Tag.TAG_COMPOUND)) {
            NonNullList<ItemStack> buffer = NonNullList.withSize(0,ItemStack.EMPTY);
            ContainerHelper.loadAllItems(compound.getCompound("buffer"), buffer, pRegistries);
            this.internalBuffer.addAll(buffer.stream().filter(itemstack -> itemstack != ItemStack.EMPTY).toList());
            ListTag enchants = compound.getList("enchantments", Tag.TAG_COMPOUND);
            ItemEnchantments.Mutable enchantsMutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            enchants.stream().forEach(entry -> {
                CompoundTag enchant = (CompoundTag) entry;
                enchantsMutable.set(level.holderOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(enchant.getString("enchantment")))),enchant.getInt("level"));
            });
            this.enchantments = enchantsMutable.toImmutable();
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        if (!this.internalBuffer.isEmpty()) {
            NonNullList<ItemStack> buffer = NonNullList.of(ItemStack.EMPTY, this.internalBuffer.toArray(new ItemStack[0]));
            CompoundTag compoundBuffer = new CompoundTag();
            ContainerHelper.saveAllItems(compoundBuffer, buffer, pRegistries);
            compound.put("buffer", compoundBuffer);
            ListTag listEnchants = new ListTag();
            for (Object2IntMap.Entry<Holder<Enchantment>> entry : this.enchantments.entrySet()) {
                CompoundTag enchant = new CompoundTag();
                enchant.put("enchantment", StringTag.valueOf(entry.getKey().getKey().location().toString()));
                enchant.put("level", IntTag.valueOf(entry.getIntValue()));
                listEnchants.add(enchant);
            }
            compound.put("enchantments", listEnchants);
        }
        super.saveAdditional(compound, pRegistries);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new MinerMenu(windowId, this.level, this.worldPosition, inventory, player, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public ItemResourceHandler getItemResourceHandler() {
        return this.itemResourceHandler;
    }

    public void setEnchantments(ItemEnchantments enchantments) {
        this.enchantments = enchantments;
    }

    public ItemEnchantments getEnchantments() {
        return this.enchantments;
    }
}
