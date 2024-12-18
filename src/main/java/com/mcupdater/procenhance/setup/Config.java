package com.mcupdater.procenhance.setup;


import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static ModConfigSpec COMMON_CONFIG;
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_ENERGYUSE = "energy_use";
    public static final String CATEGORY_ENERGYPRODUCTION = "production";
    public static ModConfigSpec.BooleanValue GRINDER_RESOURCES;
    public static ModConfigSpec.IntValue AUTOHARVESTER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue AUTOPACKAGER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue BASIC_BIOGENERATOR_PER_TICK;
    public static ModConfigSpec.IntValue BASIC_GENERATOR_PER_TICK;
    public static ModConfigSpec.IntValue BASIC_LAVA_GENERATOR_PER_TICK;
    public static ModConfigSpec.IntValue CRUDE_GENERATOR_PER_TICK;
    public static ModConfigSpec.IntValue DECONSTRUCTOR_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue DEHYDRATOR_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue FURNACE_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue GRINDER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue HYDRATOR_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue MINER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue PLANTER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue PUMP_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue SAWMILL_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue SOLIDIFIER_ENERGY_PER_TICK;
    public static ModConfigSpec.IntValue STONECUTTER_ENERGY_PER_TICK;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
        COMMON_BUILDER.push(CATEGORY_GENERAL);
        GRINDER_RESOURCES = COMMON_BUILDER.comment("Grinder: Enable recipes for gravel and basalt resource generation").define("GrinderResources", true);
        COMMON_BUILDER.push(CATEGORY_ENERGYPRODUCTION);
        CRUDE_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("CrudeGeneratorProduction",5,0, Integer.MAX_VALUE);
        BASIC_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("BasicGeneratorProduction",20,0, Integer.MAX_VALUE);
        BASIC_LAVA_GENERATOR_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("BasicLavaGeneratorProduction", 30, 0, Integer.MAX_VALUE);
        BASIC_BIOGENERATOR_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("BasicBiogeneratorProduction", 50, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push(CATEGORY_ENERGYUSE);
        AUTOHARVESTER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("AutoHarvesterEnergyUse",  10, 0, Integer.MAX_VALUE);
        AUTOPACKAGER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("AutoPackagerEnergyUse", 10, 0, Integer.MAX_VALUE);
        DECONSTRUCTOR_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("DeconstructorEnergyUse", 20, 0, Integer.MAX_VALUE);
        DEHYDRATOR_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("DehydratorEnergyUse", 10, 0, Integer.MAX_VALUE);
        FURNACE_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("FurnaceEnergyUse",20,0, Integer.MAX_VALUE);
        GRINDER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("GrinderEnergyUse", 20, 0, Integer.MAX_VALUE);
        HYDRATOR_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("HydratorEnergyUse", 10, 0, Integer.MAX_VALUE);
        MINER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("MinerEnergyUse", 20, 0, Integer.MAX_VALUE);
        PLANTER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("PlanterEnergyUse", 10, 0, Integer.MAX_VALUE);
        PUMP_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("PumpEnergyUse", 20, 0, Integer.MAX_VALUE);
        SAWMILL_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("SawmillEnergyUse", 20, 0, Integer.MAX_VALUE);
        SOLIDIFIER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("SolidifierEnergyUser", 5, 0, Integer.MAX_VALUE);
        STONECUTTER_ENERGY_PER_TICK = COMMON_BUILDER.comment("Base energy per tick").defineInRange("StonecutterEnergyUse", 20, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
