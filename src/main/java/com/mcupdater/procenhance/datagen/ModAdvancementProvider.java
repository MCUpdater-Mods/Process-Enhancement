package com.mcupdater.procenhance.datagen;

import com.mcupdater.mculib.setup.MCULibRegistration;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
	/**
	 * Constructs an advancement provider using the generators to write the
	 * advancements to a file.
	 *
	 * @param output             the target directory of the data generator
	 * @param registries         a future of a lookup for registries and their objects
	 * @param existingFileHelper a helper used to find whether a file exists
	 */
	public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		super(output, registries, existingFileHelper, List.of(new ModAdvancementProvider.PEAdvancementGenerator()));
	}

	private static class PEAdvancementGenerator implements AdvancementGenerator {
		@Override
		public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
			AdvancementHolder root = Advancement.Builder.advancement().display(
							Registration.GENERATORT1_BLOCKITEM.asItem(),
							Component.translatable("processenhancement.advancement.root.title"),
							Component.translatable("processenhancement.advancement.root.description"),
							ResourceLocation.withDefaultNamespace("textures/block/bricks.png"),
							AdvancementType.TASK,
							false,
							false,
							false
					)
					.requirements(AdvancementRequirements.Strategy.OR)
					.addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "root").toString())
					;
			AdvancementHolder someCopper = Advancement.Builder.advancement().display(
							Items.COPPER_INGOT,
							Component.translatable("processenhancement.advancement.some_copper.title"),
							Component.translatable("processenhancement.advancement.some_copper.description"),
							null,
							AdvancementType.TASK,
							true,
							true,
							false
					)
					.parent(root)
					.requirements(AdvancementRequirements.Strategy.OR)
					.addCriterion("ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
					.rewards(AdvancementRewards.Builder.loot(Registration.ADVANCEMENT_COPPER))
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "copper").toString())
					;
			AdvancementHolder crudeGenerator = createTask(Registration.CRUDEGENERATOR_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.crude_generator.name","book.processenhancement.art_of_processing.generation.crude_generator.description", someCopper,Registration.CRUDEGENERATOR_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "crude_generator").toString());
			AdvancementHolder generatorT1 = createTask(Registration.GENERATORT1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.generatort1.name","book.processenhancement.art_of_processing.generation.generatort1.description", crudeGenerator, Registration.GENERATORT1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "generatort1").toString());
			AdvancementHolder generatorT2 = createTask(Registration.GENERATORT2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.generatort2.name","book.processenhancement.art_of_processing.generation.generatort2.description", generatorT1, Registration.GENERATORT2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "generatort2").toString());
			AdvancementHolder generatorT3 = createTask(Registration.GENERATORT3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.generatort3.name","book.processenhancement.art_of_processing.generation.generatort3.description", generatorT2, Registration.GENERATORT3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "generatort3").toString());
			AdvancementHolder generatorT4 = createTask(Registration.GENERATORT4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.generatort4.name","book.processenhancement.art_of_processing.generation.generatort4.description", generatorT3, Registration.GENERATORT4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "generatort4").toString());
			AdvancementHolder lavageneratorT1 = createTask(Registration.LAVAGENERATORT1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.lavageneratort1.name","book.processenhancement.art_of_processing.generation.lavageneratort1.description", crudeGenerator, Registration.LAVAGENERATORT1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "lavageneratort1").toString());
			AdvancementHolder lavageneratorT2 = createTask(Registration.LAVAGENERATORT2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.lavageneratort2.name","book.processenhancement.art_of_processing.generation.lavageneratort2.description", lavageneratorT1, Registration.LAVAGENERATORT2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "lavageneratort2").toString());
			AdvancementHolder lavageneratorT3 = createTask(Registration.LAVAGENERATORT3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.lavageneratort3.name","book.processenhancement.art_of_processing.generation.lavageneratort3.description", lavageneratorT2, Registration.LAVAGENERATORT3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "lavageneratort3").toString());
			AdvancementHolder lavageneratorT4 = createTask(Registration.LAVAGENERATORT4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.lavageneratort4.name","book.processenhancement.art_of_processing.generation.lavageneratort4.description", lavageneratorT3, Registration.LAVAGENERATORT4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "lavageneratort4").toString());
			AdvancementHolder biogeneratorT1 = createTask(Registration.BIOGENERATORT1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.biogeneratort1.name","book.processenhancement.art_of_processing.generation.biogeneratort1.description", crudeGenerator, Registration.BIOGENERATORT1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "biogeneratort1").toString());
			AdvancementHolder biogeneratorT2 = createTask(Registration.BIOGENERATORT2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.biogeneratort2.name","book.processenhancement.art_of_processing.generation.biogeneratort2.description", biogeneratorT1, Registration.BIOGENERATORT2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "biogeneratort2").toString());
			AdvancementHolder biogeneratorT3 = createTask(Registration.BIOGENERATORT3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.biogeneratort3.name","book.processenhancement.art_of_processing.generation.biogeneratort3.description", biogeneratorT2, Registration.BIOGENERATORT3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "biogeneratort3").toString());
			AdvancementHolder biogeneratorT4 = createTask(Registration.BIOGENERATORT4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.biogeneratort4.name","book.processenhancement.art_of_processing.generation.biogeneratort4.description", biogeneratorT3, Registration.BIOGENERATORT4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "biogeneratort4").toString());
			AdvancementHolder solargeneratorT1 = createTask(Registration.SOLARGENERATORT1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.solargeneratort1.name","book.processenhancement.art_of_processing.generation.solargeneratort1.description", crudeGenerator, Registration.SOLARGENERATORT1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "solargeneratort1").toString());
			AdvancementHolder solargeneratorT2 = createTask(Registration.SOLARGENERATORT2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.solargeneratort2.name","book.processenhancement.art_of_processing.generation.solargeneratort2.description", solargeneratorT1, Registration.SOLARGENERATORT2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "solargeneratort2").toString());
			AdvancementHolder solargeneratorT3 = createTask(Registration.SOLARGENERATORT3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.solargeneratort3.name","book.processenhancement.art_of_processing.generation.solargeneratort3.description", solargeneratorT2, Registration.SOLARGENERATORT3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "solargeneratort3").toString());
			AdvancementHolder solargeneratorT4 = createTask(Registration.SOLARGENERATORT4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.generation.solargeneratort4.name","book.processenhancement.art_of_processing.generation.solargeneratort4.description", solargeneratorT3, Registration.SOLARGENERATORT4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "solargeneratort4").toString());
			AdvancementHolder grinderT1 = createTask(Registration.GRINDERT1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.basic_grinder.name","book.processenhancement.art_of_processing.processing.basic_grinder.description", someCopper, Registration.GRINDERT1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grindert1").toString());
			AdvancementHolder grinderT2 = createTask(Registration.GRINDERT2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.intermediate_grinder.name","book.processenhancement.art_of_processing.processing.intermediate_grinder.description", grinderT1, Registration.GRINDERT2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grindert2").toString());
			AdvancementHolder grinderT3 = createTask(Registration.GRINDERT3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.advanced_grinder.name","book.processenhancement.art_of_processing.processing.advanced_grinder.description", grinderT2, Registration.GRINDERT3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grindert3").toString());
			AdvancementHolder grinderT4 = createTask(Registration.GRINDERT4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.industrial_grinder.name","book.processenhancement.art_of_processing.processing.industrial_grinder.description", grinderT3, Registration.GRINDERT4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "grindert4").toString());
			AdvancementHolder furnaceT1 = createTask(Registration.FURNACET1_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.basic_furnace.name","book.processenhancement.art_of_processing.processing.basic_furnace.description", someCopper, Registration.FURNACET1_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "furnacet1").toString());
			AdvancementHolder furnaceT2 = createTask(Registration.FURNACET2_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.intermediate_furnace.name","book.processenhancement.art_of_processing.processing.intermediate_furnace.description", furnaceT1, Registration.FURNACET2_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "furnacet2").toString());
			AdvancementHolder furnaceT3 = createTask(Registration.FURNACET3_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.advanced_furnace.name","book.processenhancement.art_of_processing.processing.advanced_furnace.description", furnaceT2, Registration.FURNACET3_BLOCKITEM.asItem(), AdvancementType.TASK)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "furnacet3").toString());
			AdvancementHolder furnaceT4 = createTask(Registration.FURNACET4_BLOCKITEM.asItem(), "book.processenhancement.art_of_processing.processing.industrial_furnace.name","book.processenhancement.art_of_processing.processing.industrial_furnace.description", furnaceT3, Registration.FURNACET4_BLOCKITEM.asItem(), AdvancementType.GOAL)
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "furnacet4").toString());
			var zenBuilder = Advancement.Builder.advancement()
					.display(
							MCULibRegistration.MCULIB_ICON.get().asItem(),
							Component.translatable("processenhancement.advancement.zen.title"),
							Component.translatable("processenhancement.advancement.zen.description"),
							null,
							AdvancementType.CHALLENGE,
							true,
							true,
							true
					)
					.parent(someCopper)
					.requirements(AdvancementRequirements.Strategy.AND)
					;
			for (var entry :Registration.MACHINES.getEntries()) {
				zenBuilder = zenBuilder.addCriterion(entry.getKey().toString(), InventoryChangeTrigger.TriggerInstance.hasItems(entry.get().asItem()));
			}
			for (var entry :Registration.BATTERIES.getEntries()) {
				zenBuilder = zenBuilder.addCriterion(entry.getKey().toString(), InventoryChangeTrigger.TriggerInstance.hasItems(entry.get().asItem()));
			}
			for (var entry :Registration.TANKS.getEntries()) {
				zenBuilder = zenBuilder.addCriterion(entry.getKey().toString(), InventoryChangeTrigger.TriggerInstance.hasItems(entry.get().asItem()));
			}
			for (var entry :Registration.MINERS.getEntries()) {
				zenBuilder = zenBuilder.addCriterion(entry.getKey().toString(), InventoryChangeTrigger.TriggerInstance.hasItems(entry.get().asItem()));
			}
			for (var entry :Registration.TOOLS.getEntries()) {
				zenBuilder = zenBuilder.addCriterion(entry.getKey().toString(), InventoryChangeTrigger.TriggerInstance.hasItems(entry.get().asItem()));
			}
			AdvancementHolder zen = zenBuilder
					.rewards(AdvancementRewards.Builder.experience(500))
					.save(saver, ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "zen").toString());
		}

		private Advancement.Builder createTask(ItemLike icon, String titleKey, String descKey, AdvancementHolder parent, ItemLike criteria, AdvancementType type) {
			return Advancement.Builder.advancement()
					.display(
							icon,
							Component.translatable(titleKey),
							Component.translatable(descKey),
							null,
							type,
							true,
							true,
							false
					)
					.parent(parent)
					.requirements(AdvancementRequirements.Strategy.OR)
					.addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(criteria));
		}
	}
}
