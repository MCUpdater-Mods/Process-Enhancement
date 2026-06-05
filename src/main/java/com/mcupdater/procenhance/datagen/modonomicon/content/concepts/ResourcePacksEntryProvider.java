package com.mcupdater.procenhance.datagen.modonomicon.content.concepts;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class ResourcePacksEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "resource_packs";

	public ResourcePacksEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookTextPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Resource Packs");
		this.pageText("""
				This mod relies heavily on using vanilla block textures to create a seamless aesthetic.\\
				\\
				Because of the way that these textures are used, most resource packs will similarly affect the way that the blocks in this mod appear to maintain this seamless aesthetic.
				""");
		//TODO: Add a screenshot

	}

	@Override
	protected String entryName() {
		return "Resource Packs";
	}

	@Override
	protected String entryDescription() {
		return "Keeping the aesthetics of your preference";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Items.PAINTING);
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
