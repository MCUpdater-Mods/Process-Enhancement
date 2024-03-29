package com.mcupdater.procenhance.setup;

import ca.weblite.objc.Proxy;
import joptsimple.internal.OptionNameMap;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static final String CATEGORY_GENERAL = "general";
    public static ForgeConfigSpec.IntValue CRUDE_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue BASIC_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue FURNACE_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue SAWMILL_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue GRINDER_ENERGY_PER_TICK;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        CRUDE_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Crude Generator: How much energy produced per tick").defineInRange("CrudeGeneratorProduction",5,0,Integer.MAX_VALUE);
        BASIC_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Basic Generator: How much energy produced per tick").defineInRange("BasicGeneratorProduction",20,0,Integer.MAX_VALUE);
        FURNACE_ENERGY_PER_TICK = COMMON_BUILDER.comment("Electric Furnace: How much energy is required per tick").defineInRange("FurnaceEnergyUse",20,0,Integer.MAX_VALUE);
        SAWMILL_ENERGY_PER_TICK = COMMON_BUILDER.comment("Sawmill: How much energy is required per tick").defineInRange("SawmillEnergyUse", 20, 0,Integer.MAX_VALUE);
        GRINDER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Grinder: How much energy is required per tick").defineInRange("GrinderEnergyUse", 20, 0,Integer.MAX_VALUE);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
