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

public class GeneratorT2EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "generatort2";

	public GeneratorT2EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.GENERATORT2_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Intermediate Generator");
		this.pageText("""
				A slightly better generator.  Burns fuel to produce power.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "intermediate_generator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.generator_t2) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Intermediate Generator";
	}

	@Override
	protected String entryDescription() {
		return "Fuel goes in, more energy comes out";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.GENERATORT2_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
