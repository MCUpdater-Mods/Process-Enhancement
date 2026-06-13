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

public class BiogeneratorT4EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "biogeneratort4";

	public BiogeneratorT4EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.BIOGENERATORT4_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Industrial Biogenerator");
		this.pageText("""
				An advanced generator.\\
				\\
				Consumes copper dust, gunpowder, and macerated biomass to generate energy.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "industrial_biogenerator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.biogenerator_t4) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Industrial Biogenerator";
	}

	@Override
	protected String entryDescription() {
		return "Factory-level power!";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.BIOGENERATORT4_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
