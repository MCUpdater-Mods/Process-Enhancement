package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.datagen.modonomicon.MachineTier;
import com.mcupdater.procenhance.datagen.modonomicon.template.SingleRecipeEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.template.TieredBlockEntryProvider;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InfrastructureCategory extends CategoryProvider {
	public static final String ID = "infrastructure";

	public InfrastructureCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"a-b-c-d-----",
				"e-f-g-h-----",
				"----------16",
				"i-j-ABCDE-27",
				"----FGHIJ-38",
				"--k-KLMNO-49",
				"----PQRST-50",
				"--l---------"
		};
	}

	@Override
	protected void generateEntries() {
		BookEntryModel batteryt1 = this.add(new TieredBlockEntryProvider(this, Registration.BATTERIES, MachineTier.BASIC, "battery", "Battery", "A block to store your energy.\\\n\\\nKeeps energy when broken and upgraded.", "Stores 500,000 FE", "Holder of Power: Novice","").generate('a'));
		BookEntryModel batteryt2 = this.add(new TieredBlockEntryProvider(this, Registration.BATTERIES, MachineTier.INTERMEDIATE, "battery", "Battery", "A block to store your energy.\\\n\\\nKeeps energy when broken and upgraded.", "Stores 1,000,000 FE", "Holder of Power: Apprentice","battery_upgrade/").generate('b'));
		batteryt2.addParent(parent(batteryt1));
		BookEntryModel batteryt3 = this.add(new TieredBlockEntryProvider(this, Registration.BATTERIES, MachineTier.ADVANCED, "battery", "Battery", "A block to store your energy.\\\n\\\nKeeps energy when broken and upgraded.", "Stores 2,000,000 FE", "Holder of Power: Adept","battery_upgrade/").generate('c'));
		batteryt3.addParent(parent(batteryt2));
		BookEntryModel batteryt4 = this.add(new TieredBlockEntryProvider(this, Registration.BATTERIES, MachineTier.INDUSTRIAL, "battery", "Battery", "A block to store your energy.\\\n\\\nKeeps energy when broken.", "Stores 4,000,000 FE", "Holder of Power: Master","battery_upgrade/").generate('d'));
		batteryt4.addParent(parent(batteryt3));

		BookEntryModel tankt1 = this.add(new TieredBlockEntryProvider(this, Registration.TANKS, MachineTier.BASIC, "tank", "Tank", "A block to store your fluids.\\\n\\\nKeeps fluids when broken and upgraded.", "Stores 10,000 mB", "Fluidic Retention: Minimal","").generate('e'));
		BookEntryModel tankt2 = this.add(new TieredBlockEntryProvider(this, Registration.TANKS, MachineTier.INTERMEDIATE, "tank", "Tank", "A block to store your fluids.\\\n\\\nKeeps fluids when broken and upgraded.", "Stores 100,000 mB", "Fluidic Retention: Minor","tank_upgrade/").generate('f'));
		tankt2.addParent(parent(tankt1));
		BookEntryModel tankt3 = this.add(new TieredBlockEntryProvider(this, Registration.TANKS, MachineTier.ADVANCED, "tank", "Tank", "A block to store your fluids.\\\n\\\nKeeps fluids when broken and upgraded.", "Stores 1,000,000 mB", "Fluidic Retention: Major","tank_upgrade/").generate('g'));
		tankt3.addParent(parent(tankt2));
		BookEntryModel tankt4 = this.add(new TieredBlockEntryProvider(this, Registration.TANKS, MachineTier.INDUSTRIAL, "tank", "Tank", "A block to store your fluids.\\\n\\\nKeeps fluids when broken.", "Stores 10,000,000 mB", "Fluidic Retention: Critical","tank_upgrade/").generate('h'));
		tankt4.addParent(parent(tankt3));

		BookEntryModel buffer = this.add(new SingleRecipeEntryProvider(this, Registration.BUFFER_BLOCKITEM.get(), "buffer", "Buffer", "Holds a small amount of items, fluids, and energy.", "", "Better than hoppers", "").generate('i'));
		BookEntryModel copperwire = this.add(new SingleRecipeEntryProvider(this, Registration.COPPERWIRE_BLOCKITEM.get(), "copper_wire", "Copper Wire", "Connects your machines to power.", "", "Moving the power", "").generate('j'));
		BookEntryModel concealedwire = this.add(new SingleRecipeEntryProvider(this, Registration.CONCEALEDWIRE_BLOCKITEM.get(), "concealed_wire", "Concealed Wire", "Connects your machines to power... stealthily\\\n\\\nHold a wrench in your main hand and a solid block in your offhand, then right-click on the block to set the texture", "", "Moving the power ...in style!", "").generate('k'));
		concealedwire.addParent(parent(copperwire));
		BookEntryModel concealedwirewall = this.add(new SingleRecipeEntryProvider(this, Registration.CONCEALEDWIRE_WALL_BLOCKITEM.get(), "concealed_wire_wall", "Concealed Wire Wall", "Connects your machines to power... stealthily\\\n\\\nHold a wrench in your main hand and a wall block in your offhand, then right-click on the block to set the texture", "", "Moving the power ...with even more style!", "").generate('l'));
		concealedwirewall.addParent(parent(concealedwire));

		BookEntryModel lantern = this.add(new SingleRecipeEntryProvider(this, Registration.ELECTRIC_LANTERN_BLOCKITEM.get(), "electric_lantern", "Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night", "").generate('A'));
		BookEntryModel copperlantern = this.add(new SingleRecipeEntryProvider(this, Registration.COPPER_ELECTRIC_LANTERN_BLOCKITEM.get(), "copper_electric_lantern", "Copper Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night... in green", "").generate('B'));
		BookEntryModel netherlantern = this.add(new SingleRecipeEntryProvider(this, Registration.NETHER_ELECTRIC_LANTERN_BLOCKITEM.get(), "nether_electric_lantern", "Nether Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night... in blue", "").generate('C'));
		BookEntryModel pridelantern = this.add(new SingleRecipeEntryProvider(this, Registration.PRIDE_ELECTRIC_LANTERN_BLOCKITEM.get(), "pride_electric_lantern", "Pride Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night... with pride", "").generate('D'));
		BookEntryModel whitelantern = this.add(new SingleRecipeEntryProvider(this, Registration.TERRACOTTA_LANTERN_BLOCK_ITEM.get(0).get(), "white_electric_lantern", "White Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night", "").generate('E'));
		for (int color=1; color < DyeColor.values().length; color++) {
			this.add(new SingleRecipeEntryProvider(this, Registration.TERRACOTTA_LANTERN_BLOCK_ITEM.get(color).get(), DyeColor.byId(color).getName() + "_electric_lantern", capitalizeName(DyeColor.byId(color).getName()) + " Electric Lantern", "Takes a little power, provides light.\\\n\\\nUses 1 FE/s (0.05 FE/t)", "", "Brightening your night", "").generate((char)(color+69)));
		}

		this.add(new SingleRecipeEntryProvider(this, Registration.ANDESITESOLIDIFIER_BLOCKITEM.get(), "andesite_solidifier", "Andesite Solidifier", "Takes a some power, produces andesite.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('1'));
		this.add(new SingleRecipeEntryProvider(this, Registration.BASALTSOLIDIFIER_BLOCKITEM.get(), "basalt_solidifier", "Basalt Solidifier", "Takes a some power, produces basalt.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('2'));
		this.add(new SingleRecipeEntryProvider(this, Registration.CALCITESOLIDIFIER_BLOCKITEM.get(), "calcite_solidifier", "Calcite Solidifier", "Takes a some power, produces calcite.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('3'));
		this.add(new SingleRecipeEntryProvider(this, Registration.COBBLESTONESOLIDIFIER_BLOCKITEM.get(), "cobblestone_solidifier", "Cobblestone Solidifier", "Takes a some power, produces cobblestone.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('4'));
		this.add(new SingleRecipeEntryProvider(this, Registration.DEEPSLATESOLIDIFIER_BLOCKITEM.get(), "deepslate_solidifier", "Deepslate Solidifier", "Takes a some power, produces deepslate.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('5'));
		this.add(new SingleRecipeEntryProvider(this, Registration.DIORITESOLIDIFIER_BLOCKITEM.get(), "diorite_solidifier", "Diorite Solidifier", "Takes a some power, produces diorite.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('6'));
		this.add(new SingleRecipeEntryProvider(this, Registration.ENDSTONESOLIDIFIER_BLOCKITEM.get(), "endstone_solidifier", "End Stone Solidifier", "Takes a some power, produces end stone.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('7'));
		this.add(new SingleRecipeEntryProvider(this, Registration.GRANITESOLIDIFIER_BLOCKITEM.get(), "granite_solidifier", "Granite Solidifier", "Takes a some power, produces granite.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('8'));
		this.add(new SingleRecipeEntryProvider(this, Registration.STONESOLIDIFIER_BLOCKITEM.get(), "stone_solidifier", "Stone Solidifier", "Takes a some power, produces stone.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('9'));
		this.add(new SingleRecipeEntryProvider(this, Registration.TUFFSOLIDIFIER_BLOCKITEM.get(), "tuff_solidifier", "Tuff Solidifier", "Takes a some power, produces tuff.\\\n\\\nUses [{}](value.solidifiers) FE/t", "", "For all your build needs", "").generate('0'));

	}

	private String capitalizeName(String input) {
		return Arrays.stream(input.split("_+"))
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
				.collect(Collectors.joining(" "));
	}

	@Override
	protected String categoryName() {
		return "Infrastructure";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Registration.COPPER_ELECTRIC_LANTERN_BLOCKITEM.get());
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
