package com.mcupdater.procenhance.datagen.modonomicon.content.arcane;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.book.page.BookSpotlightPage;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class DisenchanterEntryProvider extends EntryProvider {
	public static final String ENTRY_ID = "disenchanter";

	public DisenchanterEntryProvider(CategoryProvider parent) {
		super(parent);
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(Registration.DISENCHANTER_BLOCKITEM.get()))
				.withText(this.context().pageText())
		);
		this.pageTitle("Disenchanter");
		this.pageText("""
				The Disenchanter will remove all enchantments from an item and write them to a book at the cost of 1 lapis lazuli per enchantment.
				""");

		this.page("page2", () -> BookTextPageModel.create()
				.withText(this.context().pageText())
		);
		this.pageText("""
				You will receive the unenchanted item back as well. Additionally, this will also remove the first stored enchantment from an Enchanted Book and write that to a new Enchanted Book while returning the Enchanted Book without that enchantment.\\
				\\
				This machine does not use power on its own but is capable of passing power to adjacent machines.
				""");

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "disenchanter"))
		);

	}

	@Override
	protected String entryName() {
		return "Disenchanter";
	}

	@Override
	protected String entryDescription() {
		return "Carefully extracting enchantments";
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(Registration.DISENCHANTER_BLOCKITEM.get());
	}

	@Override
	protected String entryId() {
		return ENTRY_ID;
	}
}
