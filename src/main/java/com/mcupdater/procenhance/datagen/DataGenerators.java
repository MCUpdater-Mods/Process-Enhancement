package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.loot.ModBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ProcessEnhancement.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(
                event.includeServer(),
                (DataProvider.Factory<LootTableProvider>) output -> new LootTableProvider(
                        output,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                        ),
                        lookupProvider
                ));
        dataGenerator.addProvider(event.includeServer(), new ModBlockStateProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModItemModelProvider(packOutput, existingFileHelper));
        ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, ProcessEnhancement.MODID, existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), modBlockTagsProvider);
        dataGenerator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, modBlockTagsProvider, ProcessEnhancement.MODID, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModLootModifier(packOutput, lookupProvider));
    }
}
