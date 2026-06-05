package com.mcupdater.procenhance.datagen.modonomicon.template;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.modonomicon.MachineTier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TieredBlockEntryProvider extends EntryProvider {
	private final String baseName;
	private final String entryId;
	private final MachineTier tier;
	private final DeferredRegister.Blocks registry;
	private final String description;
	private final String recipeInfo;
	private final String entryDescription;
	private final Item item;
	private final ResourceLocation recipe;

	public TieredBlockEntryProvider(CategoryProvider parent, DeferredRegister.Blocks registry, MachineTier tier, String baseId, String baseName, String description, String recipeInfo, String entryDescription, String recipePrefix) {
		super(parent);
		this.tier = tier;
		this.entryId = (tier.getPrefix() + "_" + baseId).toLowerCase();
		this.baseName = baseName;
		this.registry = registry;
		this.description = description;
		this.recipeInfo = recipeInfo;
		ResourceLocation res = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, (this.tier.getPrefix() + "_" + baseId).toLowerCase());
		this.item = registry.getEntries().stream().filter(e -> e.is(res)).findFirst().orElseThrow().get().asItem();
		this.entryDescription = entryDescription;
		this.recipe = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, (recipePrefix + this.entryId).toLowerCase());
	}

	public TieredBlockEntryProvider(CategoryProvider parent, DeferredRegister.Blocks registry, MachineTier tier, String baseId, String baseName, String description, String recipeInfo, String entryDescription, ResourceLocation recipeOverride) {
		super(parent);
		this.tier = tier;
		this.entryId = (baseId + "_" + tier.getTier()).toLowerCase();
		this.baseName = baseName;
		this.registry = registry;
		this.description = description;
		this.recipeInfo = recipeInfo;
		ResourceLocation res = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, (this.tier.getPrefix() + "_" + baseId).toLowerCase());
		this.item = registry.getEntries().stream().filter(e -> e.is(res)).findFirst().orElseThrow().get().asItem();
		this.entryDescription = entryDescription;
		this.recipe = recipeOverride;
	}

	@Override
	protected void generatePages() {
		this.page("intro", () -> BookSpotlightPageModel.create()
				.withItem(Ingredient.of(this.item))
				.withText(this.context().pageText())
		);
		this.pageTitle(tier.getPrefix() + " " + baseName);
		this.pageText(this.description);

		this.page("recipe", () -> BookCraftingRecipePageModel.create()
				.withRecipeId1(this.recipe)
				.withText(this.context().pageText())
		);
		this.pageText(this.recipeInfo);

	}

	@Override
	protected String entryName() {
		return this.tier.getPrefix() + " " + this.baseName;
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
