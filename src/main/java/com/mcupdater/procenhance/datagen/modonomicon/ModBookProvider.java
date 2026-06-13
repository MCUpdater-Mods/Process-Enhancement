package com.mcupdater.procenhance.datagen.modonomicon;

import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.mcupdater.mculib.setup.MCULibRegistration;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.datagen.modonomicon.categories.*;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.resources.ResourceLocation;

public class ModBookProvider extends SingleBookSubProvider {
	public static final String ID = "art_of_processing";

	public ModBookProvider(String modid, ModonomiconLanguageProvider lang) {
		super(ID, modid, lang);
	}

	@Override
	protected void registerDefaultMacros() {

	}

	@Override
	protected void generateCategories() {
		this.add(new ConceptsCategory(this).generate());
		this.add(new GenerationCategory(this).generate());
		this.add(new ProcessingCategory(this).generate());
		this.add(new InfrastructureCategory(this).generate());
		this.add(new ToolsCategory(this).generate());
		this.add(new ArcaneCategory(this).generate());
		this.add(new ItemsCategory(this).generate());
	}

	@Override
	protected String bookName() {
		return "The Art of Processing";
	}

	@Override
	protected String bookTooltip() {
		return "A Reference Guide to Process Enhancement";
	}

	@Override
	protected BookModel additionalSetup(BookModel book) {
		return super.additionalSetup(book)
				.withCustomBookItem(Registration.BOOK.getId())
				.withGenerateBookItem(true)
				.withModel(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "book"))
				.withCreativeTab(MCULibRegistration.ITEM_GROUP.getId())
				.withAutoAddReadConditions(true);
	}
}
