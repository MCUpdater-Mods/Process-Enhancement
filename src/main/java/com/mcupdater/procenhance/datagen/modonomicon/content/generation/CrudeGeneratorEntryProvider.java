package com.mcupdater.procenhance.datagen.modonomicon.content.generation;

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
import net.minecraft.world.item.crafting.Ingredient;

public class CrudeGeneratorEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "crude_generator";

	public CrudeGeneratorEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.CRUDEGENERATOR_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Crude Generator");
		this.pageText("""
				This is an entry-level power generator that passively generates as long as there is a source of heat below it.\\
				\\
							Valid heat sources:
							- (Regular/Soul) Campfire
							- (Regular/Soul) Fire
							- Lava
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "crude_generator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				Produces [{}](value.crude_generator) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Crude Generator";
	}

	@Override
	protected String entryDescription() {
		return "Power starts here";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.CRUDEGENERATOR_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
