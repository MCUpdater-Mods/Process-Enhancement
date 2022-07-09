package com.mcupdater.procenhance.blocks.furnace;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.gui.WidgetPower;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ElectricFurnaceScreen extends AbstractMachineScreen<ElectricFurnaceEntity,ElectricFurnaceMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/machine.png");

    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }

}
