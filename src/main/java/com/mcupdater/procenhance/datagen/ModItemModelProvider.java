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
    }

    protected ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(), new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(modid, "item/" + item.getRegistryName().getPath()));
    }

    protected ItemModelBuilder block(BlockItem blockItem) {
        return withExistingParent(blockItem.getRegistryName().getPath(),modid + ":block/" + blockItem.getBlock().getRegistryName().getPath());
    }
}
