package com.mcupdater.procenhance.loot.functions;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RetainFluidFunction extends LootItemConditionalFunction {
    public static final MapCodec<RetainFluidFunction> CODEC = RecordCodecBuilder.mapCodec(inst -> commonFields(inst).apply(inst, RetainFluidFunction::new));

    protected RetainFluidFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    public static Builder<?> getBuilder() {
        return simpleBuilder((RetainFluidFunction::new));
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AbstractConfigurableBlockEntity configurableBlockEntity) {
            @Nullable IFluidHandlerItem fluidStorage = pStack.getCapability(Capabilities.FluidHandler.ITEM);
                for (int tank = 0; tank < configurableBlockEntity.getFluidHandler().getInternalHandler().getTanks(); tank++) {
                    fluidStorage.fill(configurableBlockEntity.getFluidHandler().getInternalHandler().getFluidInTank(tank), IFluidHandler.FluidAction.EXECUTE);
                }
            }
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return Registration.RETAIN_FLUID.get();
    }
}
