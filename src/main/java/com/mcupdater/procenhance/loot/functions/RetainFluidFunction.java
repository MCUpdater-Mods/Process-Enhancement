package com.mcupdater.procenhance.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class RetainFluidFunction extends LootItemConditionalFunction {
    public static final LootItemFunctionType RETAIN_FLUID_TYPE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ProcessEnhancement.MODID, "retain_fluid"), new LootItemFunctionType(new RetainFluidFunction.Serializer()));
    protected RetainFluidFunction(LootItemCondition[] pConditions) {
        super(pConditions);
    }

    public static Builder<?> getBuilder() {
        return simpleBuilder((RetainFluidFunction::new));
    }

    public static void load() {}

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        pStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null); // Attempt to force the caps to populate
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AbstractConfigurableBlockEntity configurableBlockEntity) {
            pStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidStorage -> {
                for (int tank = 0; tank < configurableBlockEntity.getFluidHandler().getTanks(); tank++) {
                    fluidStorage.fill(configurableBlockEntity.getFluidHandler().getFluidInTank(tank), IFluidHandler.FluidAction.EXECUTE);
                }
            });
        }
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return RETAIN_FLUID_TYPE;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<RetainFluidFunction> {

        @Override
        public RetainFluidFunction deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, LootItemCondition[] pConditions) {
            return new RetainFluidFunction(pConditions);
        }
    }
}
