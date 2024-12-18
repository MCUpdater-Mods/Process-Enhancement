package com.mcupdater.procenhance.loot.functions;

import com.mcupdater.procenhance.blocks.miner.MinerEntity;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RetainEnchantmentsFunction extends LootItemConditionalFunction {
    public static final MapCodec<RetainEnchantmentsFunction> CODEC = RecordCodecBuilder.mapCodec(inst -> commonFields(inst).apply(inst, RetainEnchantmentsFunction::new));

    protected RetainEnchantmentsFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Registration.RETAIN_ENCHANTMENTS.get();
    }

    public static LootItemConditionalFunction.Builder<?> getBuilder() {
        return simpleBuilder((RetainEnchantmentsFunction::new));
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof MinerEntity miner) {
            ItemEnchantments enchantments = miner.getEnchantments();
            for (Object2IntMap.Entry<Holder<Enchantment>> enchant : enchantments.entrySet()) {
                pStack.enchant(enchant.getKey(), enchant.getIntValue());
            }
        }
        return pStack;
    }
}
