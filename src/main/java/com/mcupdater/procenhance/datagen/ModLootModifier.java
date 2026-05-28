package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.loot.CrusherLootModifier;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ModLootModifier extends GlobalLootModifierProvider {
	public ModLootModifier(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, ProcessEnhancement.MODID);
	}

	@Override
	protected void start() {
		add("crusher", CrusherLootModifier::new, Registration.CRUSHERS_TAG);
	}

	private void add(String name, Function<LootItemCondition[], IGlobalLootModifier> modifier, TagKey<Item> requiredToolTag) {
		add(name, modifier.apply(new LootItemCondition[]{new MatchTool(Optional.of(ItemPredicate.Builder.item().of(requiredToolTag).build()))}), List.of());
	}
}
