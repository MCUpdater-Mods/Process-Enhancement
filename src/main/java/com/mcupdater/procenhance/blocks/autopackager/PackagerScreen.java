package com.mcupdater.procenhance.blocks.autopackager;

import com.mcupdater.mculib.block.AbstractMachineScreen;
import com.mcupdater.mculib.gui.ConfigPanel;
import com.mcupdater.mculib.gui.TabConfig;
import com.mcupdater.mculib.gui.WidgetPower;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PackagerScreen extends AbstractMachineScreen<PackagerEntity,PackagerMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(ProcessEnhancement.MODID, "textures/gui/autopackager.png");

    public PackagerScreen(PackagerMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

 /*
    @Override
    protected void init() {
        super.init();
        for (Widget renderable : this.renderables) {
            if (renderable instanceof WidgetPower) {
                this.renderables.remove(renderable);
                break;
            }
        }
        this.addRenderableWidget(new WidgetPower(this.leftPos + 161, this.topPos + 4, 10, 48, menu.getEnergyHandler(), WidgetPower.Orientation.VERTICAL));
    }
  */

    @Override
    protected ResourceLocation getGUIResourceLocation() {
        return GUI;
    }
}
