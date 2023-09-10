package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.compat.BYGCompat;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProcessEnhancement.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(true, new ModRecipeProvider(dataGenerator));
        dataGenerator.addProvider(true, new ModLootTableProvider(dataGenerator));
        dataGenerator.addProvider(true, new ModBlockStateProvider(dataGenerator,existingFileHelper));
        dataGenerator.addProvider(true, new ModItemModelProvider(dataGenerator,existingFileHelper));
        dataGenerator.addProvider(true, new ModBlockTagsProvider(dataGenerator, ProcessEnhancement.MODID, existingFileHelper));

        dataGenerator.addProvider(true, new BYGCompat(dataGenerator));

    }
}
