package com.mcupdater.procenhance.datagen.loot;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        for (RegistryObject<Block> entry : Registration.MACHINES.getEntries()){
            dropSelf(entry.get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        Registration.MACHINES.getEntries().forEach(entry -> knownBlocks.add(entry.get()));
        return knownBlocks;
    }
}
