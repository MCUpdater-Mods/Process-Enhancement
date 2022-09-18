package com.mcupdater.procenhance.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.capabilities.InternalEnergyStorage;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.energy.CapabilityEnergy;

public class RetainEnergyFunction extends LootItemConditionalFunction {
    public static final LootItemFunctionType RETAIN_ENERGY_TYPE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ProcessEnhancement.MODID, "retain_energy"), new LootItemFunctionType(new RetainEnergyFunction.Serializer()));
    protected RetainEnergyFunction(LootItemCondition[] pConditions) {
        super(pConditions);
    }

    public static LootItemConditionalFunction.Builder<?> getBuilder() {
        return simpleBuilder((RetainEnergyFunction::new));
    }

    public static void load() {}

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        pStack.getCapability(CapabilityEnergy.ENERGY).orElse(null); // Attempt to force the caps to populate
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AbstractConfigurableBlockEntity configurableBlockEntity) {
            pStack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
                ((InternalEnergyStorage) energyStorage).setStoredEnergy(configurableBlockEntity.getEnergyStorage().getStoredEnergy());
            });
        }
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return RETAIN_ENERGY_TYPE;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<RetainEnergyFunction> {

        @Override
        public RetainEnergyFunction deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, LootItemCondition[] pConditions) {
            return new RetainEnergyFunction(pConditions);
        }
    }
}
