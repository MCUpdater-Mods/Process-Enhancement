package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ProcessEnhancement.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(new ModRecipeProvider(dataGenerator));
        dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));
        dataGenerator.addProvider(new ModBlockStateProvider(dataGenerator,existingFileHelper));
        dataGenerator.addProvider(new ModItemModelProvider(dataGenerator,existingFileHelper));
        dataGenerator.addProvider(new ModBlockTagsProvider(dataGenerator, ProcessEnhancement.MODID, existingFileHelper));
    }
}
