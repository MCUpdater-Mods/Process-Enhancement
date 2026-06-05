package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mcupdater.procenhance.datagen.modonomicon.content.items.DustsEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.template.SingleRecipeEntryProvider;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ItemsCategory extends CategoryProvider {
	public static final String ID = "items";

	public ItemsCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"1-2",
				"---",
				"3-4"
		};
	}

	@Override
	protected void generateEntries() {
		this.add(new SingleRecipeEntryProvider(this, Registration.CAPACITOR.get(), "capacitor", "Capacitor", "A crafting component for energy based things.", "", "The basic component", "").generate('1'));
		this.add(new DustsEntryProvider(this).generate('2'));
		this.add(new EntryProvider(this){

			@Override
			protected void generatePages() {
				this.page("intro", () -> BookSpotlightPageModel.create()
						.withItem(Items.GUNPOWDER)
						.withTitle(this.context().pageTitle())
						.withText(this.context().pageText())
				);
				this.pageTitle("Gunpowder");
				this.pageText("Gunpowder can be produced by grinding flint in a Grinder");
			}

			@Override
			protected String entryName() {
				return "Gunpowder";
			}

			@Override
			protected String entryDescription() {
				return "No creepers were harmed... unfortunately";
			}

			@Override
			protected Pair<Integer, Integer> entryBackground() {
				return EntryBackground.DEFAULT;
			}

			@Override
			protected BookIconModel entryIcon() {
				return BookIconModel.create(Items.GUNPOWDER);
			}

			@Override
			protected String entryId() {
				return "gunpowder";
			}
		}.generate('3'));
		this.add(new SingleRecipeEntryProvider(this, Items.SLIME_BALL, "slime_ball", "Slime Ball", "A way to make slime balls without finding and killing slimes", "", "A sticky situation", ResourceLocation.withDefaultNamespace("slime_ball")).generate('4'));

	}

	@Override
	protected String categoryName() {
		return "Miscellaneous Items";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Registration.PLANT_DUST.get());
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
