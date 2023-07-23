package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for (RegistryObject<Block> entry : Registration.MACHINES.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.BLOCKS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.BATTERIES.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.TANKS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (RegistryObject<Block> entry : Registration.MINERS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
    }
}
