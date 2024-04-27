package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
    public static final TagKey<Item> IRON_DUST = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "dusts/iron"));
    public static final TagKey<Item> COPPER_DUST = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "dusts/copper"));
    public static final TagKey<Item> GOLD_DUST = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "dusts/gold"));
    public static final TagKey<Item> PLANT_DUST = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "dusts/plant"));
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider modBlockTagsProvider, String modid, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modBlockTagsProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(IRON_DUST).add(Registration.IRON_DUST.get());
        this.tag(COPPER_DUST).add(Registration.COPPER_DUST.get());
        this.tag(GOLD_DUST).add(Registration.GOLD_DUST.get());
        this.tag(PLANT_DUST).add(Registration.PLANT_DUST.get());

        this.tag(Tags.Items.DUSTS).addTags(IRON_DUST,COPPER_DUST,GOLD_DUST,PLANT_DUST);
    }
}
