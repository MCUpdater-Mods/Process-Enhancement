package com.mcupdater.procenhance.blocks.tank;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TankBlockItem extends BlockItem {
    protected final int capacity;

    public TankBlockItem(@NotNull TankBlock tankBlock, Item.Properties properties, int capacity) {
        super(tankBlock, properties);
        this.capacity = capacity;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pFlag);

        @Nullable IFluidHandlerItem fluidStorage = pStack.getCapability(Capabilities.FluidHandler.ITEM, null);
        if (fluidStorage != null) {
            pTooltipComponents.add(Component.literal("Contains: ").append(fluidStorage.getFluidInTank(0).isEmpty() ? Component.literal("Empty") : Component.translatable(fluidStorage.getFluidInTank(0).getFluid().getFluidType().getDescriptionId())));
            int amount = fluidStorage.getFluidInTank(0).isEmpty() ? 0 : fluidStorage.getFluidInTank(0).getAmount();
            pTooltipComponents.add(Component.literal(String.valueOf(amount)).append("/").append(String.valueOf(fluidStorage.getTankCapacity(0))).append(" mB"));
        }
    }

    public int getCapacity() {
        return capacity;
    }
}
