package com.mcupdater.procenhance.datagen.modonomicon.content.concepts;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ConfigurationEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "configuration";

	public ConfigurationEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookTextPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		);
		this.pageTitle("Machine Configuration");
		this.pageText("""
				Every machine can be configured based on what capabilities it has and each side can be configured for input or output independently.\\
				\\
				The name of the block adjacent on each side of the machine will be displayed as well as icons for what capabilities those blocks have.
				""");
		//TODO: Add a screenshot

	}

	@Override
	protected String entryName() {
		return "Machine Configuration";
	}

	@Override
	protected String entryDescription() {
		return "Configuring the input and output of machines";
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
