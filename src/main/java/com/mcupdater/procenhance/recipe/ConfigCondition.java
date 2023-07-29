package com.mcupdater.procenhance.recipe;

import com.google.gson.JsonObject;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigCondition implements ICondition {
    private final static ResourceLocation ID = new ResourceLocation(ProcessEnhancement.MODID,"config");
    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @SuppressWarnings("removal")
    @Override
    public boolean test() {
        return Config.GRINDER_RESOURCES.get();
    }

    public static class Serializer implements IConditionSerializer<ConfigCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ConfigCondition value) { }

        @Override
        public ConfigCondition read(JsonObject json) {
            return new ConfigCondition();
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
    }
}
