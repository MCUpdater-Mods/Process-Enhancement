package com.mcupdater.procenhance.datagen.modonomicon.template;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSmithingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class SmithingRecipeEntryProvider extends EntryProvider {
	private final String baseName;
	private final String entryId;
	private final String description;
	private final String recipeInfo;
	private final String entryDescription;
	private final Item item;
	private final ResourceLocation recipe;

	public SmithingRecipeEntryProvider(CategoryProvider parent, Item item, String baseId, String baseName, String description, String recipeInfo, String entryDescription, String recipePrefix) {
		super(parent);
		this.entryId = baseId;
		this.baseName = baseName;
		this.description = description;
		this.recipeInfo = recipeInfo;
		this.item = item;
		this.entryDescription = entryDescription;
		this.recipe = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, (recipePrefix + this.entryId).toLowerCase());
	}

	public SmithingRecipeEntryProvider(CategoryProvider parent, Item item, String baseId, String baseName, String description, String recipeInfo, String entryDescription, ResourceLocation recipeOverride) {
		super(parent);
		this.entryId = baseId;
		this.baseName = baseName;
		this.description = description;
		this.recipeInfo = recipeInfo;
		this.item = item;
		this.entryDescription = entryDescription;
		this.recipe = recipeOverride;
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(this.item))
				.withText(this.context().pageText())
		);
		this.pageTitle(baseName);
		this.pageText(this.description);

		this.page("recipe", () -> BookSmithingRecipePageModel.create()
				.withRecipeId1(this.recipe)
				.withText(this.context().pageText())
		);
		this.pageText(this.recipeInfo);

	}

	@Override
	protected String entryName() {
		return this.baseName;
	}

	@Override
	protected String entryDescription() {
		return this.entryDescription;
	}

	@Override
	protected Pair<Integer, Integer> entryBackground() {
		return EntryBackground.DEFAULT;
	}

	@Override
	protected BookIconModel entryIcon() {
		return BookIconModel.create(this.item);
	}

	@Override
	protected String entryId() {
		return entryId;
	}
}
