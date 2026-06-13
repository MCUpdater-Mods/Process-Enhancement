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

public class SolarGeneratorT3EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "solargeneratort3";

	public SolarGeneratorT3EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.SOLARGENERATORT3_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Advanced Solar Generator");
		this.pageText("""
				A passive generator. Generates energy while the sun is shining.\\
				\\
				Obviously needs to see the sky.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "advanced_solar_generator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.solar_generator_t3) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Advanced Solar Generator";
	}

	@Override
	protected String entryDescription() {
		return "Harnessing even more of the sun";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.SOLARGENERATORT3_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
