package com.mcupdater.procenhance.datagen.modonomicon.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mcupdater.procenhance.datagen.modonomicon.content.generation.*;
import com.mcupdater.procenhance.setup.Registration;

public class GenerationCategory extends CategoryProvider {
	public static final String ID = "generation";

	public GenerationCategory(SingleBookSubProvider parent) {
		super(parent);
	}

	@Override
	protected String[] generateEntryMap() {
		return new String[]{
				"--1234-",
				"-------",
				"---5678",
				"#------",
				"---ABCD",
				"-&-----",
				"--EFGH-"
		};
	}

	@Override
	protected void generateEntries() {
		BookEntryModel crudeGenerator = this.add(new CrudeGeneratorEntryProvider(this).generate("#"));
		BookEntryModel generatorT1 = this.add(new GeneratorT1EntryProvider(this).generate("1"));
		generatorT1.addParent(this.parent(crudeGenerator));
		BookEntryModel generatorT2 = this.add(new GeneratorT2EntryProvider(this).generate("2"));
		generatorT2.addParent(this.parent(generatorT1));
		BookEntryModel generatorT3 = this.add(new GeneratorT3EntryProvider(this).generate("3"));
		generatorT3.addParent(this.parent(generatorT2));
		BookEntryModel generatorT4 = this.add(new GeneratorT4EntryProvider(this).generate("4"));
		generatorT4.addParent(this.parent(generatorT3));

		BookEntryModel lavageneratorT1 = this.add(new LavaGeneratorT1EntryProvider(this).generate("5"));
		lavageneratorT1.addParent(this.parent(crudeGenerator));
		BookEntryModel lavageneratorT2 = this.add(new LavaGeneratorT2EntryProvider(this).generate("6"));
		lavageneratorT2.addParent(this.parent(lavageneratorT1));
		BookEntryModel lavageneratorT3 = this.add(new LavaGeneratorT3EntryProvider(this).generate("7"));
		lavageneratorT3.addParent(this.parent(lavageneratorT2));
		BookEntryModel lavageneratorT4 = this.add(new LavaGeneratorT4EntryProvider(this).generate("8"));
		lavageneratorT4.addParent(this.parent(lavageneratorT3));

		BookEntryModel biogeneratorT1 = this.add(new BiogeneratorT1EntryProvider(this).generate("A"));
		biogeneratorT1.addParent(this.parent(crudeGenerator));
		BookEntryModel biogeneratorT2 = this.add(new BiogeneratorT2EntryProvider(this).generate("B"));
		biogeneratorT2.addParent(this.parent(biogeneratorT1));
		BookEntryModel biogeneratorT3 = this.add(new BiogeneratorT3EntryProvider(this).generate("C"));
		biogeneratorT3.addParent(this.parent(biogeneratorT2));
		BookEntryModel biogeneratorT4 = this.add(new BiogeneratorT4EntryProvider(this).generate("D"));
		biogeneratorT4.addParent(this.parent(biogeneratorT3));

		BookEntryModel compactsolargenerator = this.add(new CompactSolarGeneratorEntryProvider(this).generate("&"));
		compactsolargenerator.addParent(this.parent(crudeGenerator));
		BookEntryModel solargeneratorT1 = this.add(new SolarGeneratorT1EntryProvider(this).generate("E"));
		solargeneratorT1.addParent(this.parent(crudeGenerator));
		BookEntryModel solargeneratorT2 = this.add(new SolarGeneratorT2EntryProvider(this).generate("F"));
		solargeneratorT2.addParent(this.parent(solargeneratorT1));
		BookEntryModel solargeneratorT3 = this.add(new SolarGeneratorT3EntryProvider(this).generate("G"));
		solargeneratorT3.addParent(this.parent(solargeneratorT2));
		BookEntryModel solargeneratorT4 = this.add(new SolarGeneratorT4EntryProvider(this).generate("H"));
		solargeneratorT4.addParent(this.parent(solargeneratorT3));
	}

	@Override
	protected String categoryName() {
		return "Power Generation";
	}

	@Override
	protected BookIconModel categoryIcon() {
		return BookIconModel.create(Registration.CRUDEGENERATOR_BLOCKITEM.get());
	}

	@Override
	public String categoryId() {
		return ID;
	}
}
