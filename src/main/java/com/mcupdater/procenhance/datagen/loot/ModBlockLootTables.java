package com.mcupdater.procenhance.datagen.loot;

import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        for (RegistryObject<Block> entry : Registration.MACHINES.getEntries()){
            dropSelf(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.BLOCKS.getEntries()){
            dropSelf(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.BATTERIES.getEntries()){
            LootTable.Builder builder = LootTable.lootTable().withPool(
                    applyExplosionCondition(
                            entry.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.get()).apply(RetainEnergyFunction.getBuilder()))));
            this.add(entry.get(), builder);
        }
        for (RegistryObject<Block> entry : Registration.TANKS.getEntries()){
            LootTable.Builder builder = LootTable.lootTable().withPool(
                    applyExplosionCondition(
                            entry.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.get()).apply(RetainFluidFunction.getBuilder()))));
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
        return knownBlocks;
    }
}
