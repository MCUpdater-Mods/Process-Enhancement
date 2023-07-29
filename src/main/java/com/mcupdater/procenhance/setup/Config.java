package com.mcupdater.procenhance.setup;

import ca.weblite.objc.Proxy;
import joptsimple.internal.OptionNameMap;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static final String CATEGORY_GENERAL = "general";
    public static ForgeConfigSpec.IntValue CRUDE_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue BASIC_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue BASIC_LAVA_GENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue BASIC_BIOGENERATOR_PER_TICK;
    public static ForgeConfigSpec.IntValue FURNACE_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue SAWMILL_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue GRINDER_ENERGY_PER_TICK;
    public static ForgeConfigSpec.BooleanValue GRINDER_RESOURCES;
    public static ForgeConfigSpec.IntValue STONECUTTER_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue PUMP_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue MINER_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue DECONSTRUCTOR_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue SOLIDIFIER_ENERGY_PER_TICK;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        CRUDE_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Crude Generator: How much energy produced per tick").defineInRange("CrudeGeneratorProduction",5,0, Integer.MAX_VALUE);
        BASIC_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Basic Generator: How much energy produced per tick").defineInRange("BasicGeneratorProduction",20,0, Integer.MAX_VALUE);
        BASIC_LAVA_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Basic Lava Generator: How much energy produced per tick").defineInRange("BasicLavaGeneratorProduction", 30, 0, Integer.MAX_VALUE);
        BASIC_BIOGENERATOR_PER_TICK = COMMON_BUILDER.comment("Basic Biogenerator: How much energy produced per tick").defineInRange("BasicBiogeneratorProduction", 50, 0, Integer.MAX_VALUE);
        FURNACE_ENERGY_PER_TICK = COMMON_BUILDER.comment("Electric Furnace: How much energy is required per tick").defineInRange("FurnaceEnergyUse",20,0, Integer.MAX_VALUE);
        SAWMILL_ENERGY_PER_TICK = COMMON_BUILDER.comment("Sawmill: How much energy is required per tick").defineInRange("SawmillEnergyUse", 20, 0, Integer.MAX_VALUE);
        GRINDER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Grinder: How much energy is required per tick").defineInRange("GrinderEnergyUse", 20, 0, Integer.MAX_VALUE);
        GRINDER_RESOURCES = COMMON_BUILDER.comment("Grinder: Enable recipes for gravel and basalt resource generation").define("GrinderResources", true);
        STONECUTTER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Electric Stonecutter: How much energy is required per tick").defineInRange("StonecutterEnergyUse", 20, 0, Integer.MAX_VALUE);
        PUMP_ENERGY_PER_TICK = COMMON_BUILDER.comment("Pump: How much energy is required per tick").defineInRange("PumpEnergyUse", 20, 0, Integer.MAX_VALUE);
        MINER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Miner: How much energy is required per tick").defineInRange("MinerEnergyUse", 20, 0, Integer.MAX_VALUE);
        DECONSTRUCTOR_ENERGY_PER_TICK = COMMON_BUILDER.comment("Deconstructor: How much energy is required per tick").defineInRange("DeconstructorEnergyUse", 20, 0, Integer.MAX_VALUE);
        SOLIDIFIER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Solidifiers: How much energy is required per tick").defineInRange("SolidifierEnergyUser", 5, 0, Integer.MAX_VALUE);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
