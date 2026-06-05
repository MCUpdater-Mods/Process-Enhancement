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

public class BiogeneratorT1EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "biogeneratort1";

	public BiogeneratorT1EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.BIOGENERATORT1_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Basic Biogenerator");
		this.pageText("""
				An advanced generator.\\
				\\
				Consumes copper dust, gunpowder, and macerated biomass to generate energy.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "basic_biogenerator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.biogenerator_t1) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Basic Biogenerator";
	}

	@Override
	protected String entryDescription() {
		return "A generator worthy of processing";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.BIOGENERATORT1_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
