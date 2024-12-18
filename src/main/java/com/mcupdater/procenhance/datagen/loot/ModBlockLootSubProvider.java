package com.mcupdater.procenhance.datagen.loot;

import com.mcupdater.procenhance.loot.functions.RetainEnchantmentsFunction;
import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {

    public ModBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected void generate() {
        Registration.MACHINES.getEntries().stream().forEach(entry -> add(entry.get(),createNameableBlockEntityTable(entry.get())));
        Registration.BLOCKS.getEntries().stream().forEach(entry -> add(entry.get(),createNameableBlockEntityTable(entry.get())));
        for (DeferredHolder<Block, ? extends Block> entry : Registration.BATTERIES.getEntries()){
            LootTable.Builder builder = LootTable.lootTable().withPool(
                    applyExplosionCondition(
                            entry.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.get()).apply(RetainEnergyFunction.getBuilder()))));
            this.add(entry.get(), builder);
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.TANKS.getEntries()){
            LootTable.Builder builder = LootTable.lootTable().withPool(
                    applyExplosionCondition(
                            entry.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.get()).apply(RetainFluidFunction.getBuilder()))));
            this.add(entry.get(), builder);
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.MINERS.getEntries()){
            LootTable.Builder builder = LootTable.lootTable().withPool(
                    applyExplosionCondition(
                            entry.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.get()).apply(RetainEnchantmentsFunction.getBuilder()))));
            this.add(entry.get(), builder);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        Registration.MACHINES.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        Registration.BLOCKS.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        Registration.BATTERIES.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        Registration.TANKS.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        Registration.MINERS.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        return knownBlocks;
    }
}
