package com.mcupdater.procenhance.datagen.modonomicon.content.generation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class CompactSolarGeneratorEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "compactsolargenerator";

	public CompactSolarGeneratorEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.COMPACTSOLARGENERATOR_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Compact Solar Generator");
		this.pageText("""
				A tiny solar generator. Generates energy while the sun is shining.\\
				\\	
				Only enough power for a few lights
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "compact_solar_generator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces 1 FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Compact Solar Generator";
	}

	@Override
	protected String entryDescription() {
		return "It fits on lanterns!";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.COMPACTSOLARGENERATOR_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
