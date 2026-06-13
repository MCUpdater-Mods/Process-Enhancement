package com.mcupdater.procenhance.datagen.modonomicon.content.concepts;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mcupdater.mculib.MCULib;
import com.mcupdater.mculib.setup.MCULibRegistration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class SharingEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "sharing";

	public SharingEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookTextPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Power Sharing");
		this.pageText("""
				Machines in this and other related mods will share power in excess of 50%% of their capacity with their adjacent machines if configured to output power, eliminating the need to run cables to every machine separately.
				""");
		//TODO: Add a screenshot

	}

	@Override
	protected String entryName() {
		return "Power Sharing";
	}

	@Override
	protected String entryDescription() {
		return "Sharing is caring!";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(MCULibRegistration.MCULIB_ICON.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
