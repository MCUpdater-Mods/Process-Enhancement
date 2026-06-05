package com.mcupdater.procenhance.blocks.crude_generator;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.mculib.block.IMachineGuiProvider;
import com.mcupdater.mculib.capabilities.EnergyResourceHandler;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.inventory.InputOutputSettings;
import com.mcupdater.mculib.inventory.SideSetting;
import com.mcupdater.procenhance.setup.Config;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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

import static com.mcupdater.procenhance.setup.Registration.CRUDEGENERATOR_ENTITY;

public class CrudeGeneratorEntity extends AbstractConfigurableBlockEntity implements IMachineGuiProvider {

    public CrudeGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        super(CRUDEGENERATOR_ENTITY.get(), blockPos, blockState);
        EnergyResourceHandler energyResourceHandler = new EnergyResourceHandler(this.level, 50000, Integer.MAX_VALUE, false);
        Arrays.stream(Direction.values()).sequential().forEach(side -> energyResourceHandler.updateIOSettings(side, new InputOutputSettings(SideSetting.DISABLED, side.getOpposite(), SideSetting.AUTOMATED, side.getOpposite(), (byte) 0)));
        this.configMap.put("power", energyResourceHandler);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        EnergyResourceHandler energyStorage = (EnergyResourceHandler) this.configMap.get("power");
        if (!this.level.isClientSide) {
            BlockState blockBelow = this.level.getBlockState(this.worldPosition.below());
            if (blockBelow.is(Registration.HEAT_SOURCES)) {
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
    protected Component getDefaultName() {
        return Component.translatable("block.processenhancement.crude_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new CrudeGeneratorMenu(windowId, this.level, this.worldPosition, inventory, player, DataHelper.getAdjacentNames(this.level, this.worldPosition));
    }
}
