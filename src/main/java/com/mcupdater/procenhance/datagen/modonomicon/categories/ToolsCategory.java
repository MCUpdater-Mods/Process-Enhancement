package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.datagen.modonomicon.template.SingleRecipeEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.template.SmithingRecipeEntryProvider;
import com.mcupdater.procenhance.setup.Registration;

public class ToolsCategory extends CategoryProvider {
	public static final String ID = "tools";

	public ToolsCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"1-2-3-4-5",
				"---6-----",
				"-A--B--C-"
		};
	}

	@Override
	protected void generateEntries() {
		BookEntryModel woodencrusher = this.add(new SingleRecipeEntryProvider(this, Registration.WOODEN_CRUSHER_ITEM.get(), "wood_crusher", "Wooden Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "Resources from the world", "").generate('1'));
		BookEntryModel stonecrusher = this.add(new SingleRecipeEntryProvider(this, Registration.STONE_CRUSHER_ITEM.get(), "stone_crusher", "Stone Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "Probably should make a Grinder soon", "").generate('2'));
		stonecrusher.addParent(parent(woodencrusher));
		BookEntryModel coppercrusher = this.add(new SingleRecipeEntryProvider(this, Registration.COPPER_CRUSHER_ITEM.get(), "copper_crusher", "Copper Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "A little cheaper than iron", "").generate('6'));
		coppercrusher.addParent(parent(stonecrusher));
		BookEntryModel ironcrusher = this.add(new SingleRecipeEntryProvider(this, Registration.IRON_CRUSHER_ITEM.get(), "iron_crusher", "Iron Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "I guess keep digging...", "").generate('3'));
		ironcrusher.addParent(parent(stonecrusher));
		BookEntryModel diamondcrusher = this.add(new SingleRecipeEntryProvider(this, Registration.DIAMOND_CRUSHER_ITEM.get(), "diamond_crusher", "Diamond Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "Still doing it by hand?", "").generate('4'));
		diamondcrusher.addParent(parent(ironcrusher));
		BookEntryModel netheritecrusher = this.add(new SmithingRecipeEntryProvider(this, Registration.NETHERITE_CRUSHER_ITEM.get(), "netherite_crusher", "Netherite Crusher", "Breaks blocks in the world to produce the same output as the Grinder", "", "You must really like digging", "smithing/").generate('5'));
		netheritecrusher.addParent(parent(diamondcrusher));

		this.add(new SingleRecipeEntryProvider(this, Registration.WRENCH_ITEM.get(), "wrench", "Wrench", "A wrench. Needed to conceal your wiring, but almost any wrench will do.", "", "A standard tool", "").generate('A'));
		this.add(new SingleRecipeEntryProvider(this, Registration.CHISEL_ITEM.get(), "chisel", "Chisel", "Carves patterns into blocks", "", "Carving your place in the world", "").generate('B'));
		this.add(new SingleRecipeEntryProvider(this, Registration.STAIRMAKER_ITEM.get(), "stairmaker", "Stairmaker", "Carves blocks into their associate stair block variant.", "Will also rotate existing stairs to your current facing or flip it over if it is already facing that way", "To easily reach new heights (or depths)", "").generate('C'));
	}

	@Override
	protected String categoryName() {
		return "Tools";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Registration.WRENCH_ITEM.get());
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
