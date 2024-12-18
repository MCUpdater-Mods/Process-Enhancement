package com.mcupdater.procenhance.loot.functions;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.procenhance.capabilities.ItemEnergyStorage;
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
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RetainEnergyFunction extends LootItemConditionalFunction {
    public static final MapCodec<RetainEnergyFunction> CODEC = RecordCodecBuilder.mapCodec(inst -> commonFields(inst).apply(inst, RetainEnergyFunction::new));

    protected RetainEnergyFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    public static LootItemConditionalFunction.Builder<?> getBuilder() {
        return simpleBuilder((RetainEnergyFunction::new));
    }

    @Override
    protected @NotNull ItemStack run(ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AbstractConfigurableBlockEntity configurableBlockEntity) {
            @Nullable IEnergyStorage energyStorage = pStack.getCapability(Capabilities.EnergyStorage.ITEM, null);
            ((ItemEnergyStorage) energyStorage).setStoredEnergy(configurableBlockEntity.getEnergyStorage().getStoredEnergy());
        }
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return Registration.RETAIN_ENERGY.get();
    }

}
