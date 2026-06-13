package com.mcupdater.procenhance.datagen.modonomicon.content.items;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Pair;

public class DustsEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "dusts";

	public DustsEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("copper_dust", () -> BookSpotlightPageModel.create()
				.withItem(Registration.COPPER_DUST.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Copper Dust");
		this.pageText("Produced by grinding copper ores or ingots in a Grinder or breaking the corresponding blocks with a Crusher.");

		this.page("iron_dust", () -> BookSpotlightPageModel.create()
				.withItem(Registration.IRON_DUST.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Iron Dust");
		this.pageText("Produced by grinding iron ores or ingots in a Grinder or breaking the corresponding blocks with a Crusher.");

		this.page("gold_dust", () -> BookSpotlightPageModel.create()
				.withItem(Registration.GOLD_DUST.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Gold Dust");
		this.pageText("Produced by grinding gold ores or ingots in a Grinder or breaking the corresponding blocks with a Crusher.");

		this.page("plant_dust", () -> BookSpotlightPageModel.create()
				.withItem(Registration.PLANT_DUST.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Macerated Biomass");
		this.pageText("Produced by grinding Sugar Cane, Bamboo, or Kelp in a Grinder.");

		this.page("nether_dust", () -> BookSpotlightPageModel.create()
				.withItem(Registration.NETHER_DUST.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Nether Dust");
		this.pageText("Produced by grinding netherrack in a Grinder or breaking it with a Crusher.");

		this.page("nether_dust_block", () -> BookSpotlightPageModel.create()
				.withItem(Registration.NETHER_DUST_BLOCKITEM.get())
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Nether Dust Block");
		this.pageText("Crafted from 9 Nether Dust.  Can be used in a Dehydrator to produce lava.");
	}

	@Override
	protected String entryName() {
		return "Dusts";
	}

	@Override
	protected String entryDescription() {
		return "Yield to my powder!";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.IRON_DUST.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
