package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(ProcessEnhancement.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.BASICGENERATOR_BLOCK.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        //ReconstructorChannel.init();
    }
}
