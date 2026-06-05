package com.mcupdater.procenhance.datagen.modonomicon.content.concepts;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class OverdriveEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "overdrive";

	public OverdriveEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookTextPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Overdrive");
		this.pageText("""
				Machines that have power stored in excess will run faster, if enabled in the configuration
				""");

		this.page("page2", () -> BookTextPageModel.create()
				.withText(this.context().pageText())
		);
		this.pageText("""
				* Above 80%%: Machine will progress 8 times per tick
				* Above 50%%: Machine will progress 4 times per tick
				* Above 25%%: Machine will progress 2 times per tick
				""");

		this.page("page3", () -> BookTextPageModel.create()
				.withText(this.context.pageText())
		);
		this.pageText("""
			The machine will consume the same amount of power as it normally would per operation. If your power generation is not able to maintain consumption, the machine will slow down to a level that can be maintained.
				""");

	}

	@Override
	protected String entryName() {
		return "Overdrive";
	}

	@Override
	protected String entryDescription() {
		return "You have the power... Put it to use!";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Items.BOOK);
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
