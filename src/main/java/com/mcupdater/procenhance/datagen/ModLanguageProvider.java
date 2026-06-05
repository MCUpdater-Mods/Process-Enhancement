package com.mcupdater.procenhance.datagen;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModLanguageProvider extends AbstractModonomiconLanguageProvider {
	public ModLanguageProvider(PackOutput output, String locale, ModonomiconLanguageProvider cache) {
		super(output, ProcessEnhancement.MODID, locale, cache);
	}

	@Override
	protected void addTranslations() {
		// Blocks
		this.add(Registration.BATTERYT1_BLOCK.get(), "Basic Battery");
		this.add(Registration.BATTERYT2_BLOCK.get(), "Intermediate Battery");
		this.add(Registration.BATTERYT3_BLOCK.get(), "Advanced Battery");
		this.add(Registration.BATTERYT4_BLOCK.get(), "Industrial Battery");
		this.add(Registration.BIOGENERATORT1_BLOCK.get(), "Basic Biogenerator");
		this.add(Registration.BIOGENERATORT2_BLOCK.get(), "Intermediate Biogenerator");
		this.add(Registration.BIOGENERATORT3_BLOCK.get(), "Advanced Biogenerator");
		this.add(Registration.BIOGENERATORT4_BLOCK.get(), "Industrial Biogenerator");
		this.add(Registration.GENERATORT1_BLOCK.get(), "Basic Generator");
		this.add(Registration.GENERATORT2_BLOCK.get(), "Intermediate Generator");
		this.add(Registration.GENERATORT3_BLOCK.get(), "Advanced Generator");
		this.add(Registration.GENERATORT4_BLOCK.get(), "Industrial Generator");
		this.add(Registration.LAVAGENERATORT1_BLOCK.get(), "Basic Lava Generator");
		this.add(Registration.LAVAGENERATORT2_BLOCK.get(), "Intermediate Lava Generator");
		this.add(Registration.LAVAGENERATORT3_BLOCK.get(), "Advanced Lava Generator");
		this.add(Registration.LAVAGENERATORT4_BLOCK.get(), "Industrial Lava Generator");
		this.add(Registration.COMPACTSOLARGENERATOR_BLOCK.get(), "Compact Solar Generator");
		this.add(Registration.SOLARGENERATORT1_BLOCK.get(), "Basic Solar Generator");
		this.add(Registration.SOLARGENERATORT2_BLOCK.get(), "Intermediate Solar Generator");
		this.add(Registration.SOLARGENERATORT3_BLOCK.get(), "Advanced Solar Generator");
		this.add(Registration.SOLARGENERATORT4_BLOCK.get(), "Industrial Solar Generator");
		this.add(Registration.FURNACET1_BLOCK.get(), "Basic Furnace");
		this.add(Registration.FURNACET2_BLOCK.get(), "Intermediate Furnace");
		this.add(Registration.FURNACET3_BLOCK.get(), "Advanced Furnace");
		this.add(Registration.FURNACET4_BLOCK.get(), "Industrial Furnace");
		this.add(Registration.GRINDERT1_BLOCK.get(), "Basic Grinder");
		this.add(Registration.GRINDERT2_BLOCK.get(), "Intermediate Grinder");
		this.add(Registration.GRINDERT3_BLOCK.get(), "Advanced Grinder");
		this.add(Registration.GRINDERT4_BLOCK.get(), "Industrial Grinder");
		this.add(Registration.PUMPT1_BLOCK.get(), "Basic Pump");
		this.add(Registration.PUMPT2_BLOCK.get(), "Intermediate Pump");
		this.add(Registration.PUMPT3_BLOCK.get(), "Advanced Pump");
		this.add(Registration.PUMPT4_BLOCK.get(), "Industrial Pump");
		this.add(Registration.MINERT1_BLOCK.get(), "Basic Miner");
		this.add(Registration.MINERT2_BLOCK.get(), "Intermediate Miner");
		this.add(Registration.MINERT3_BLOCK.get(), "Advanced Miner");
		this.add(Registration.MINERT4_BLOCK.get(), "Industrial Miner");
		this.add(Registration.TANKT1_BLOCK.get(), "Basic Tank");
		this.add(Registration.TANKT2_BLOCK.get(), "Intermediate Tank");
		this.add(Registration.TANKT3_BLOCK.get(), "Advanced Tank");
		this.add(Registration.TANKT4_BLOCK.get(), "Industrial Tank");
		this.add(Registration.HARVESTER_BLOCK.get(), "AutoHarvester");
		this.add(Registration.AUTOPACKAGER_BLOCK.get(), "AutoPackager");
		this.add(Registration.BUFFER_BLOCK.get(), "Buffer");
		this.add(Registration.CONCRETEMIXER_BLOCK.get(), "Concrete Mixer");
		this.add(Registration.COPPERWIRE_BLOCK.get(), "Copper Wire");
		this.add(Registration.CRUDEGENERATOR_BLOCK.get(), "Crude Generator");
		this.add(Registration.DECONSTRUCTOR_BLOCK.get(), "Deconstructor");
		this.add(Registration.DEHYDRATOR_BLOCK.get(), "Dehydrator");
		this.add(Registration.DISENCHANTER_BLOCK.get(), "Disenchanter");
		this.add(Registration.HYDRATOR_BLOCK.get(), "Hydrator");
		this.add(Registration.PLANTER_BLOCK.get(), "Planter");
		this.add(Registration.SAWMILL_BLOCK.get(), "Sawmill");
		this.add(Registration.SOILMANAGER_BLOCK.get(), "Soil Manager");
		this.add(Registration.STONECUTTER_BLOCK.get(), "Electric Stonecutter");
		this.add(Registration.NETHER_DUST_BLOCK.get(), "Nether Dust Block");
		this.add(Registration.CONCEALEDWIRE_BLOCK.get(), "Concealed Wire");
		this.add(Registration.CONCEALEDWIRE_WALL_BLOCK.get(), "Concealed Wire Wall");
		this.add(Registration.ANDESITESOLIDIFIER_BLOCK.get(), "Andesite Solidifier");
		this.add(Registration.BASALTSOLIDIFIER_BLOCK.get(), "Basalt Solidifier");
		this.add(Registration.CALCITESOLIDIFIER_BLOCK.get(), "Calcite Solidifier");
		this.add(Registration.COBBLESTONESOLIDIFIER_BLOCK.get(), "Cobblestone Solidifier");
		this.add(Registration.DEEPSLATESOLIDIFIER_BLOCK.get(), "Deepslate Solidifier");
		this.add(Registration.DIORITESOLIDIFIER_BLOCK.get(), "Diorite Solidifier");
		this.add(Registration.ENDSTONESOLIDIFIER_BLOCK.get(), "End Stone Solidifier");
		this.add(Registration.GRANITESOLIDIFIER_BLOCK.get(), "Granite Solidifier");
		this.add(Registration.STONESOLIDIFIER_BLOCK.get(), "Stone Solidifier");
		this.add(Registration.TUFFSOLIDIFIER_BLOCK.get(), "Tuff Solidifier");
		this.add(Registration.ELECTRIC_LANTERN_BLOCK.get(), "Electric Lantern");
		this.add(Registration.COPPER_ELECTRIC_LANTERN_BLOCK.get(), "Copper Electric Lantern");
		this.add(Registration.NETHER_ELECTRIC_LANTERN_BLOCK.get(), "Nether Electric Lantern");
		for (DyeColor color : DyeColor.values()) {
			this.add(Registration.TERRACOTTA_LANTERN_BLOCK.get(color).get(), Arrays.stream(color.getName().replace("_", " ").split(" ")).map(word -> word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase()).collect(Collectors.joining(" ")) + " Electric Lantern");
		}
		this.add(Registration.PRIDE_ELECTRIC_LANTERN_BLOCK.get(), "Pride Electric Lantern");

		// Items
		this.add(Registration.CAPACITOR.get(), "Capacitor");
		this.add(Registration.CHISEL_ITEM.get(), "Chisel");
		this.add(Registration.COPPER_DUST.get(), "Copper Dust");
		this.add(Registration.GOLD_DUST.get(), "Gold Dust");
		this.add(Registration.IRON_DUST.get(), "Iron Dust");
		this.add(Registration.NETHER_DUST.get(), "Nether Dust");
		this.add(Registration.PLANT_DUST.get(), "Macerated Biomass");
		this.add(Registration.STAIRMAKER_ITEM.get(), "Stairmaker");
		this.add(Registration.WRENCH_ITEM.get(), "Wrench");
		this.add(Registration.WOODEN_CRUSHER_ITEM.get(), "Wooden Crusher");
		this.add(Registration.STONE_CRUSHER_ITEM.get(), "Stone Crusher");
		this.add(Registration.IRON_CRUSHER_ITEM.get(), "Iron Crusher");
		this.add(Registration.DIAMOND_CRUSHER_ITEM.get(), "Diamond Crusher");
		this.add(Registration.NETHERITE_CRUSHER_ITEM.get(), "Netherite Crusher");
		this.add(Registration.SLATE_2x2.get(), "Pattern Slate: 2x2");
		this.add(Registration.SLATE_3x3.get(), "Pattern Slate: 3x3");
		this.add(Registration.SLATE_CROSS.get(), "Pattern Slate: Cross");
		this.add(Registration.SLATE_DOOR.get(), "Pattern Slate: Door");
		this.add(Registration.SLATE_HOLLOW.get(), "Pattern Slate: Hollow");
		this.add(Registration.SLATE_SLAB.get(), "Pattern Slate: Slab");
		this.add(Registration.SLATE_STAIR.get(), "Pattern Slate: Stair");
		this.add(Registration.SLATE_UNPACKAGE.get(), "Pattern Slate: Unpackage");
		this.add(Registration.SLATE_WALL.get(), "Pattern Slate: Wall");

		// Config
		this.add("config.jade.plugin_processenhancement.grid", "Grid Info");
		this.add("message.energy", "Energy:");
		this.add("processenhancement.configuration.AutoHarvesterEnergyUse", "AutoHarvester");
		this.add("processenhancement.configuration.AutoPackagerEnergyUse", "AutoPackager");
		this.add("processenhancement.configuration.BasicBiogeneratorProduction", "Biogenerator");
		this.add("processenhancement.configuration.BasicGeneratorProduction", "Generator (Fuel-burning)");
		this.add("processenhancement.configuration.BasicLavaGeneratorProduction", "Lava Generator");
		this.add("processenhancement.configuration.BasicSolarGeneratorProduction", "Solar Generator");
		this.add("processenhancement.configuration.ConcreteMixerEnergyUse", "Concrete Mixer");
		this.add("processenhancement.configuration.CrudeGeneratorProduction", "Crude Generator");
		this.add("processenhancement.configuration.DeconstructorEnergyUse", "Deconstructor");
		this.add("processenhancement.configuration.DehydratorEnergyUse", "Dehydrator");
		this.add("processenhancement.configuration.FurnaceEnergyUse", "Electric Furnace");
		this.add("processenhancement.configuration.GrinderEnergyUse", "Grinder");
		this.add("processenhancement.configuration.GrinderResources", "Grinder produces resources");
		this.add("processenhancement.configuration.HydratorEnergyUse", "Hydrator");
		this.add("processenhancement.configuration.MinerEnergyUse", "Miner");
		this.add("processenhancement.configuration.PlanterEnergyUse", "Planter");
		this.add("processenhancement.configuration.PumpEnergyUse", "Pump");
		this.add("processenhancement.configuration.SawmillEnergyUse", "Sawmill");
		this.add("processenhancement.configuration.SoilManagerEnergyUse", "Soil Manager");
		this.add("processenhancement.configuration.SolidifierEnergyUser", "Solidifiers");
		this.add("processenhancement.configuration.StonecutterEnergyUse", "Stonecutter");
		this.add("processenhancement.configuration.energy_use", "Energy Use");
		this.add("processenhancement.configuration.general", "General");
		this.add("processenhancement.configuration.production", "Energy Production");
	}

}
