package com.mcupdater.procenhance.blocks.sawmill;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SawmillScreen extends AbstractMachineScreen<SawmillEntity,SawmillMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/machine.png");

    public SawmillScreen(SawmillMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }

}

