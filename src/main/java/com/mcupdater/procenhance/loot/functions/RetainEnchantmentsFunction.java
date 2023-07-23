package com.mcupdater.procenhance.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.miner.MinerEntity;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class RetainEnchantmentsFunction extends LootItemConditionalFunction {
    public static final LootItemFunctionType RETAIN_ENCHANTMENTS_TYPE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ProcessEnhancement.MODID, "retain_enchantments"), new LootItemFunctionType(new RetainEnchantmentsFunction.Serializer()));
    protected RetainEnchantmentsFunction(LootItemCondition[] pConditions) {
        super(pConditions);
    }

    public static LootItemConditionalFunction.Builder<?> getBuilder() {
        return simpleBuilder((RetainEnchantmentsFunction::new));
    }

    public static void load() {
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof MinerEntity miner) {
            CompoundTag compoundTag = miner.getEnchantmentTags();
            pStack.setTag(compoundTag);
        }
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return RETAIN_ENCHANTMENTS_TYPE;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<RetainEnchantmentsFunction> {

        @Override
        public RetainEnchantmentsFunction deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, LootItemCondition[] pConditions) {
            return new RetainEnchantmentsFunction(pConditions);
        }
    }
}
