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

public class BiogeneratorT2EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "biogeneratort2";

	public BiogeneratorT2EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.BIOGENERATORT2_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Intermediate Biogenerator");
		this.pageText("""
				An advanced generator.\\
				\\
				Consumes copper dust, gunpowder, and macerated biomass to generate energy.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "intermediate_biogenerator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.biogenerator_t2) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Intermediate Biogenerator";
	}

	@Override
	protected String entryDescription() {
		return "Making more power";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.BIOGENERATORT2_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
