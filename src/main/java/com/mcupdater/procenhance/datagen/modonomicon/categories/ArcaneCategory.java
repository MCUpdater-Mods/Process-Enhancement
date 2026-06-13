package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.datagen.modonomicon.content.arcane.DisenchanterEntryProvider;
import net.minecraft.world.item.Items;

public class ArcaneCategory extends CategoryProvider {
	public static final String ID = "arcane";

	public ArcaneCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"X"
		};
	}

	@Override
	protected void generateEntries() {
		BookEntryModel disenchanter = this.add(new DisenchanterEntryProvider(this).generate("X"));
	}

	@Override
	protected String categoryName() {
		return "Arcane Devices";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Items.EXPERIENCE_BOTTLE);
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
