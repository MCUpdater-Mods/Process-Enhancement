package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.modonomicon.MachineTier;
import com.mcupdater.procenhance.datagen.modonomicon.template.SingleRecipeEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.template.TieredBlockEntryProvider;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.resources.ResourceLocation;

public class ProcessingCategory extends CategoryProvider {
	public static final String ID = "processing";

	public ProcessingCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"a-b-c-d--1-2",
				"---------4-5",
				"e-f-g-h--6-7",
				"-----3------",
				"i-j-k-l-----",
				"------------",
				"m-n-o-p-----",
				"------------",
				"q-r-s-------",

		};
	}

	@Override
	protected void generateEntries() {
		BookEntryModel furnacet1 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.BASIC, "furnace", "Electric Furnace", "Cooks and smelts, like its non-electric counterpart", "Uses [{}](value.furnace_t1) FE/t", "", "").generate('a'));
		BookEntryModel furnacet2 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INTERMEDIATE, "furnace", "Electric Furnace", "Cooks and smelts, like its non-electric counterpart", "Uses [{}](value.furnace_t2) FE/t", "","").generate('b'));
		furnacet2.addParent(parent(furnacet1));
		BookEntryModel furnacet3 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.ADVANCED, "furnace", "Electric Furnace", "Cooks and smelts, like its non-electric counterpart", "Uses [{}](value.furnace_t3) FE/t", "","").generate('c'));
		furnacet3.addParent(parent(furnacet2));
		BookEntryModel furnacet4 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INDUSTRIAL, "furnace", "Electric Furnace", "Cooks and smelts, like its non-electric counterpart", "Uses [{}](value.furnace_t4) FE/t", "","").generate('d'));
		furnacet4.addParent(parent(furnacet3));

		BookEntryModel grindert1 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.BASIC, "grinder", "Grinder", "Grinds resources into other resources", "Uses [{}](value.grinder_t1) FE/t", "","").generate('e'));
		BookEntryModel grindert2 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INTERMEDIATE, "grinder", "Grinder", "Grinds resources into other resources", "Uses [{}](value.grinder_t2) FE/t", "","").generate('f'));
		grindert2.addParent(parent(grindert1));
		BookEntryModel grindert3 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.ADVANCED, "grinder", "Grinder", "Grinds resources into other resources", "Uses [{}](value.grinder_t3) FE/t", "","").generate('g'));
		grindert3.addParent(parent(grindert2));
		BookEntryModel grindert4 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INDUSTRIAL, "grinder", "Grinder", "Grinds resources into other resources", "Uses [{}](value.grinder_t4) FE/t", "","").generate('h'));
		grindert4.addParent(parent(grindert3));

		BookEntryModel minert1 = this.add(new TieredBlockEntryProvider(this, Registration.MINERS, MachineTier.BASIC, "miner", "Miner", "Digs below itself down to bedrock, starting 2 blocks below the Miner.\\\n\\\nInherits enchantments from the pickaxe used to craft it.", "Uses [{}](value.miner_t1) FE/t\\\n\\\nDig footprint: 3x3", "","miner/").generate('i'));
		BookEntryModel minert2 = this.add(new TieredBlockEntryProvider(this, Registration.MINERS, MachineTier.INTERMEDIATE, "miner", "Miner", "Digs below itself down to bedrock, starting 2 blocks below the Miner.", "Uses [{}](value.miner_t2) FE/t\\\n\\\nDig footprint: 9x9", "","miner/").generate('j'));
		minert2.addParent(parent(minert1));
		BookEntryModel minert3 = this.add(new TieredBlockEntryProvider(this, Registration.MINERS, MachineTier.ADVANCED, "miner", "Miner", "Digs below itself down to bedrock, starting 2 blocks below the Miner.", "Uses [{}](value.miner_t3) FE/t\\\n\\\nDig footprint: 19x19", "","miner/").generate('k'));
		minert3.addParent(parent(minert2));
		BookEntryModel minert4 = this.add(new TieredBlockEntryProvider(this, Registration.MINERS, MachineTier.INDUSTRIAL, "miner", "Miner", "Digs below itself down to bedrock, starting 2 blocks below the Miner.", "Uses [{}](value.miner_t4) FE/t\\\n\\\nDig footprint: 31x31", "","miner/").generate('l'));
		minert4.addParent(parent(minert3));

		BookEntryModel pumpt1 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.BASIC, "pump", "Pump", "Extracts fluids from the world.", "Uses [{}](value.pump_t1) FE/t\\\n\\\nArea footprint: 3x3, 2 deep", "Good enough for water","").generate('m'));
		BookEntryModel pumpt2 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INTERMEDIATE, "pump", "Pump", "Extracts fluids from the world.", "Uses [{}](value.pump_t2) FE/t\\\n\\\nArea footprint: 9x9, 8 deep", "Get some lava","").generate('n'));
		pumpt2.addParent(parent(pumpt1));
		BookEntryModel pumpt3 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.ADVANCED, "pump", "Pump", "Extracts fluids from the world.", "Uses [{}](value.pump_t3) FE/t\\\n\\\nArea footprint: 19x19, 18 deep", "I need more lava!","").generate('o'));
		pumpt3.addParent(parent(pumpt2));
		BookEntryModel pumpt4 = this.add(new TieredBlockEntryProvider(this, Registration.MACHINES, MachineTier.INDUSTRIAL, "pump", "Pump", "Extracts fluids from the world.", "Uses [{}](value.pump_t4) FE/t\\\n\\\nArea footprint: 31x31, 30 deep", "I want that lake gone!","").generate('p'));
		pumpt4.addParent(parent(pumpt3));

		BookEntryModel autoharvester = this.add(new SingleRecipeEntryProvider(this, Registration.HARVESTER_BLOCKITEM.get(), "autoharvester", "AutoHarvester", "Automatically harvests crops and trees.", "Uses [{}](value.autoharvester) FE/t", "", "").generate('q'));
		BookEntryModel planter = this.add(new SingleRecipeEntryProvider(this, Registration.PLANTER_BLOCKITEM.get(), "planter", "Planter", "Plants a 9x9 of crops or saplings by row.", "Uses [{}](value.planter) FE/t", "Crops in single rows grow faster than the same crop in adjacent rows.", "").generate('r'));
		planter.addParent(parent(autoharvester));
		BookEntryModel soilmanager = this.add(new SingleRecipeEntryProvider(this, Registration.SOILMANAGER_BLOCKITEM.get(), "soilmanager", "Soil Manager", "Hydrates farmland and applies bonemeal.", "Uses [{}](value.soil_manager) FE/t", "", "").generate('s'));
		soilmanager.addParent((parent(planter)));

		BookEntryModel autopackager = this.add(new SingleRecipeEntryProvider(this, Registration.AUTOPACKAGER_BLOCKITEM.get(), "autopackager", "AutoPackager", "Crafts using identical ingredients based on shape templates.", "Uses [{}](value.autopackager) FE/t", "", "").generate('1'));
		BookEntryModel concrete_mixer = this.add(new SingleRecipeEntryProvider(this, Registration.CONCRETEMIXER_BLOCKITEM.get(), "concrete_mixer", "Concrete Mixer", "Produces concrete dust. 4 sand, 4 gravel, 1 dye = 10 concrete dust", "Uses [{}](value.concrete_mixer) FE/t", "", "").generate('2'));
		BookEntryModel deconstructor = this.add(new SingleRecipeEntryProvider(this, Registration.DECONSTRUCTOR_BLOCKITEM.get(), "deconstructor", "Deconstructor", "Uncrafts items", "Uses [{}](value.deconstructor) FE/t", "", "").generate('3'));
		deconstructor.addParent(parent(grindert3));
		BookEntryModel dehydrator = this.add(new SingleRecipeEntryProvider(this, Registration.DEHYDRATOR_BLOCKITEM.get(), "dehydrator", "Dehydrator", "Extracts fluids from items", "Uses [{}](value.dehydrator) FE/t", "", "").generate('4'));
		BookEntryModel hydrator = this.add(new SingleRecipeEntryProvider(this, Registration.HYDRATOR_BLOCKITEM.get(), "hydrator", "Hydrator", "Injects fluids into items", "Uses [{}](value.hydrator) FE/t", "", "").generate('5'));
		BookEntryModel sawmill = this.add(new SingleRecipeEntryProvider(this, Registration.SAWMILL_BLOCKITEM.get(), "sawmill", "Sawmill", "Efficiently cuts wood", "Uses [{}](value.sawmill) FE/t", "Got Wood?", "").generate('6'));
		BookEntryModel stonecutter = this.add(new SingleRecipeEntryProvider(this, Registration.STONECUTTER_BLOCKITEM.get(), "stonecutter", "Electric Stonecutter", "Like the vanilla stonecrusher, but can be automated", "Uses [{}](value.stonecutter) FE/t", "Pairs well with Solidifiers", "").generate('7'));
	}

	@Override
	protected String categoryName() {
		return "Processing Machines";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Registration.GRINDERT1_BLOCKITEM.get());
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
