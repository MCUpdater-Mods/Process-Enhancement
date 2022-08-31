package com.mcupdater.procenhance.blocks.crude_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.InputOutputSettings;
import com.mcupdater.mculib.inventory.SideSetting;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.mcupdater.procenhance.setup.Registration.CRUDEGENERATOR_BLOCKENTITY;

public class CrudeGeneratorEntity extends AbstractConfigurableBlockEntity {
    private Component name;
    private List<Block> validSources = Arrays.asList(Blocks.LAVA,Blocks.FIRE,Blocks.SOUL_FIRE,Blocks.CAMPFIRE,Blocks.SOUL_CAMPFIRE);

    public CrudeGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        super(CRUDEGENERATOR_BLOCKENTITY.get(), blockPos, blockState);
        EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, 50000, Integer.MAX_VALUE, false);
        for (Direction side : Direction.values()) {
            InputOutputSettings ioSetting = energyResourceHandler.getIOSettings(side);
            ioSetting.setInputSetting(SideSetting.DISABLED);
            energyResourceHandler.updateIOSettings(side, ioSetting);
        }
        this.configMap.put("power", energyResourceHandler);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        if (!this.level.isClientSide) {
            Block blockBelow = this.level.getBlockState(this.worldPosition.below()).getBlock();
            if (validSources.contains(blockBelow)) {
                int added = energyStorage.getInternalHandler().receiveEnergy(Config.CRUDE_GENERATOR_PER_TICK.get(), false);
                if (added > 0) {
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
        }
        super.tick();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (this.name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        super.saveAdditional(compound);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.processenhancement.crude_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new CrudeGeneratorMenu(windowId, this.level, this.worldPosition, inventory, player, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
