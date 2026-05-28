package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        for (DeferredHolder<Block, ? extends Block> entry : Registration.MACHINES.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.BLOCKS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.BATTERIES.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.TANKS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        for (DeferredHolder<Block, ? extends Block> entry : Registration.MINERS.getEntries()) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(entry.get());
        }
        this.tag(Registration.MINEABLE_WITH_CRUSHER).addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL);
    }
}
