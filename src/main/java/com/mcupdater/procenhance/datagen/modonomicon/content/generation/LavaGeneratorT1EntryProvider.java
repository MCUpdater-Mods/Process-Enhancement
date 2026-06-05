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

public class LavaGeneratorT1EntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "lavageneratort1";

	public LavaGeneratorT1EntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.LAVAGENERATORT1_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Basic Lava Generator");
		this.pageText("""
				A modest generator.  Consumes lava to generate energy.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "basic_lava_generator"))
				.withText(this.context().pageText())
		);
		this.pageText("""
				This tier produces [{}](value.lava_generator_t1) FE/t
				""");

	}

	@Override
	protected String entryName() {
		return "Basic Lava Generator";
	}

	@Override
	protected String entryDescription() {
		return "Pump in lava, power your machines";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.LAVAGENERATORT1_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
