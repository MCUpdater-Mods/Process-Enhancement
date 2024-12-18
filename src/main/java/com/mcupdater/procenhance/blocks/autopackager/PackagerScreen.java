package com.mcupdater.procenhance.blocks.autopackager;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PackagerScreen extends AbstractMachineScreen<PackagerEntity,PackagerMenu> {

    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "textures/gui/autopackager.png");

    public PackagerScreen(PackagerMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }
}
