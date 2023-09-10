package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProcessEnhancement.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> entry : Registration.ITEMS.getEntries()) {
            if (entry.get() instanceof BlockItem blockItem) {
                block(blockItem);
            } else {
                simpleItem(entry.get());
            }
        }
        for (RegistryObject<Item> entry : Registration.PATTERNS.getEntries()) {
            simpleItem(entry.get());
        }
    }

    protected ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(modid, "item/" + ForgeRegistries.ITEMS.getKey(item).getPath()));
    }

    protected ItemModelBuilder block(BlockItem blockItem) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(blockItem).getPath(),modid + ":block/" + ForgeRegistries.BLOCKS.getKey(blockItem.getBlock()).getPath());
    }
}
