package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public static final TagKey<Item> IRON_DUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/iron"));
    public static final TagKey<Item> COPPER_DUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/copper"));
    public static final TagKey<Item> GOLD_DUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/gold"));
    public static final TagKey<Item> PLANT_DUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/plant"));
    public static final TagKey<Item> NETHER_DUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/netherrack"));

    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider modBlockTagsProvider, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, modBlockTagsProvider.contentsGetter(), modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(IRON_DUST).add(Registration.IRON_DUST.get());
        this.tag(COPPER_DUST).add(Registration.COPPER_DUST.get());
        this.tag(GOLD_DUST).add(Registration.GOLD_DUST.get());
        this.tag(PLANT_DUST).add(Registration.PLANT_DUST.get());
        this.tag(NETHER_DUST).add(Registration.NETHER_DUST.get());

        this.tag(Tags.Items.DUSTS).addTags(IRON_DUST,COPPER_DUST,GOLD_DUST,PLANT_DUST,NETHER_DUST);

        this.tag(Registration.CRUSHERS_TAG).add(Registration.WOODEN_CRUSHER_ITEM.get(), Registration.STONE_CRUSHER_ITEM.get(), Registration.DIAMOND_CRUSHER_ITEM.get(), Registration.NETHERITE_CRUSHER_ITEM.get());

        this.tag(ItemTags.MINING_ENCHANTABLE).addTag(Registration.CRUSHERS_TAG).add(Registration.MINERT1_BLOCKITEM.get(),Registration.MINERT2_BLOCKITEM.get(),Registration.MINERT3_BLOCKITEM.get(),Registration.MINERT4_BLOCKITEM.get());
        this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(Registration.MINERT1_BLOCKITEM.get(),Registration.MINERT2_BLOCKITEM.get(),Registration.MINERT3_BLOCKITEM.get(),Registration.MINERT4_BLOCKITEM.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(Registration.CRUSHERS_TAG).add(Registration.CHISEL_ITEM.get(),Registration.STAIRMAKER_ITEM.get());

        this.tag(Tags.Items.TOOLS_WRENCH).add(Registration.WRENCH_ITEM.get());
    }
}
