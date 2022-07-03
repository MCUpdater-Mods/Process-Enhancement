package com.mcupdater.procenhance.setup;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static final String CATEGORY_GENERAL = "general";
    public static ForgeConfigSpec.IntValue BASIC_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue FURNACE_ENERGY_PER_TICK;


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        BASIC_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Basic Generator: How much energy produced per tick").defineInRange("BasicGeneratorProduction",20,0,Integer.MAX_VALUE);
        FURNACE_ENERGY_PER_TICK =COMMON_BUILDER.comment("Electric Furnace: How much energy is required per tick").defineInRange("FurnaceEnergyUse",20,0,Integer.MAX_VALUE);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
