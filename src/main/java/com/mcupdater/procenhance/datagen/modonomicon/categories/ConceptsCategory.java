package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.datagen.modonomicon.content.concepts.ConfigurationEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.content.concepts.OverdriveEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.content.concepts.ResourcePacksEntryProvider;
import com.mcupdater.procenhance.datagen.modonomicon.content.concepts.SharingEntryProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;

public class ConceptsCategory extends CategoryProvider {
	public static final String ID = "concepts";

	public ConceptsCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[0];
/*
		return new String[]{
				"C  O",
				"    ",
				"    ",
				"R  S"
		};
 */
	}

	@Override
	protected void generateEntries() {
		BookEntryModel configuration = this.add(new ConfigurationEntryProvider(this).generate(new Vec2(-2f,-2f)));
		BookEntryModel overdrive = this.add(new OverdriveEntryProvider(this).generate(new Vec2(2f,-2f)));
		overdrive.addParent(this.parent(configuration));
		BookEntryModel resourcepacks = this.add(new ResourcePacksEntryProvider(this).generate(new Vec2(-2f, 2f)));
		BookEntryModel sharing = this.add(new SharingEntryProvider(this).generate(new Vec2(2f,2f)));
	}

	@Override
	protected String categoryName() {
		return "General Concepts";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Items.BOOK);
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
