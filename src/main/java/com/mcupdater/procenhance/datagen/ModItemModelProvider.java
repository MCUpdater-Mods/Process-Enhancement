package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ProcessEnhancement.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> entry : Registration.BLOCK_ITEMS.getEntries()) {
            if (entry.get() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof WallBlock) {
                    wall(blockItem);
                } else {
                    block(blockItem);
                }
            } else {
                simpleItem(entry.get());
            }
        }
        for (DeferredHolder<Item, ? extends Item> entry : Registration.ITEMS.getEntries()) {
            if (entry.get() instanceof BlockItem blockItem) {
                block(blockItem);
            } else {
                simpleItem(entry.get());
            }
        }
        for (DeferredHolder<Item, ? extends Item> entry : Registration.PATTERNS.getEntries()) {
            simpleItem(entry.get());
        }
        for (DeferredHolder<Item, ? extends Item> entry : Registration.TOOLS.getEntries()) {
            simpleItem(entry.get());
        }
        for (DeferredHolder<Item, ? extends Item> entry : Registration.HIDDEN_ITEMS.getEntries()) {
            simpleItem(entry.get());
        }
    }

    protected ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(modid, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    protected ItemModelBuilder block(BlockItem blockItem) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(blockItem).getPath(),modid + ":block/" + BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()).getPath());
    }

    protected ItemModelBuilder wall(BlockItem blockItem) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(blockItem).getPath(), modid + ":block/" + BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()).getPath() + "_inventory");
    }
}
