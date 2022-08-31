package com.mcupdater.procenhance.blocks.basic_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.capabilities.ItemResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.InputOutputSettings;
import com.mcupdater.mculib.inventory.SideSetting;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import static com.mcupdater.procenhance.setup.Registration.BASICGENERATOR_BLOCKENTITY;

public class BasicGeneratorEntity extends AbstractConfigurableBlockEntity {
    //    protected NonNullList<ItemStack> itemStorage = NonNullList.withSize(2, ItemStack.EMPTY);
    //    private final LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    int burnCurrent;
    int burnTotal;
    public ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return BasicGeneratorEntity.this.burnCurrent;
                case 1:
                    return BasicGeneratorEntity.this.burnTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int newValue) {
            switch (index) {
                case 0:
                    BasicGeneratorEntity.this.burnCurrent = newValue;
                    break;
                case 1:
                    BasicGeneratorEntity.this.burnTotal = newValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    public BasicGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        super(BASICGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, 200000, Integer.MAX_VALUE, false);
        for (Direction side : Direction.values()) {
            InputOutputSettings ioSetting = energyResourceHandler.getIOSettings(side);
            ioSetting.setInputSetting(SideSetting.DISABLED);
            energyResourceHandler.updateIOSettings(side, ioSetting);
        }
        ItemResourceHandler itemResourceHandler = new ItemResourceHandler(this.level, 2, new int[]{0,1}, new int[]{0}, new int[]{1}, this::stillValid);
        itemResourceHandler.setInsertFunction(this::canPlaceItem);
        this.configMap.put("power", energyResourceHandler);
        this.configMap.put("items", itemResourceHandler);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        ItemResourceHandler itemStorage = (ItemResourceHandler) this.configMap.get("items");
        if (!this.level.isClientSide) {
            if (this.burnCurrent > 0) {
                int added = energyStorage.getInternalHandler().receiveEnergy(Config.BASIC_GENERATOR_PER_TICK.get(), false);
                if (added > 0) {
                    --this.burnCurrent;
                    boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                    if (!currentState) {
                        pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, true);
                        pLevel.setBlock(pPos, pBlockState, 3);
                    }
                } else {
                    boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                    if (currentState) {
                        pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, false);
                        pLevel.setBlock(pPos, pBlockState, 3);
                    }
                }
            } else {
                boolean currentState = pBlockState.getValue((AbstractMachineBlock.ACTIVE));
                if (currentState) {
                    pBlockState = pBlockState.setValue(AbstractMachineBlock.ACTIVE, false);
                    pLevel.setBlock(pPos, pBlockState, 3);
                }
            }
            if (this.burnCurrent == 0 && !itemStorage.getItem(0).isEmpty()) {
                int newBurnTime = ForgeHooks.getBurnTime(itemStorage.getItem(0), RecipeType.SMELTING);
                if (newBurnTime > 0) {
                    if (itemStorage.getItem(0).hasContainerItem()) {
                        if ((itemStorage.getItem(0).getContainerItem().sameItem(itemStorage.getItem(1)) && itemStorage.getItem(1).getCount() < itemStorage.getItem(1).getMaxStackSize()) || itemStorage.getItem(1).isEmpty()) {
                            if (itemStorage.getItem(1).isEmpty()) {
                                itemStorage.setItem(1, itemStorage.getItem(0).getContainerItem());
                            } else {
                                itemStorage.getItem(1).grow(1);
                            }
                        } else {
                            return;
                        }
                    }
                    itemStorage.getItem(0).shrink(1);
                    this.burnCurrent += newBurnTime;
                    this.burnTotal = this.burnCurrent;
                }
            }
        }
        super.tick();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.burnTotal = compound.getInt("burnTotal");
        this.burnCurrent = compound.getInt("burnCurrent");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putInt("burnTotal", this.burnTotal);
        compound.putInt("burnCurrent", this.burnCurrent);
        super.saveAdditional(compound);
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(ItemStack stack) {
        return (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.basic_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new BasicGeneratorMenu(windowId, this.level, this.worldPosition, inventory, player, this.data, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }

    public Container getInventory() {
        return (ItemResourceHandler) this.configMap.get("items");
    }
}
