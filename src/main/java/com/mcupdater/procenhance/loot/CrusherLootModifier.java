package com.mcupdater.procenhance.loot;

import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.RecipeHelper;
import com.mcupdater.procenhance.recipe.result.RecipeResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrusherLootModifier extends LootModifier {
	public static final MapCodec<CrusherLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
			LootModifier.codecStart(inst).apply(inst, CrusherLootModifier::new)
	);

	public CrusherLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

		if (blockState == null) {
			return generatedLoot;
		}

		Item input = blockState.getBlock().asItem();
		if (input == Items.AIR) {
			return generatedLoot;
		}

		RecipeHolder<GrinderRecipe> recipe = RecipeHelper.getGrinderRecipe(context.getLevel(), new SingleRecipeInput(input.getDefaultInstance()));
		if (recipe == null) {
			return generatedLoot;
		}

		ObjectArrayList<ItemStack> replacedLoot = new ObjectArrayList<>();
		List<ItemStack> prizeList = new ArrayList<>();
		for (Tuple<RecipeResult,Integer> tuple : recipe.value().getOutputs()) {
			ItemStack potentialPrize = tuple.getA().getItemStack();
			if (potentialPrize.getItem() != Items.BARRIER) {
				for (int i = 0; i < tuple.getB(); i++) {
					prizeList.add(potentialPrize);
				}
			}
		}
		Collections.shuffle(prizeList);
		int rolls = 1;
		if (prizeList.size()>1) {
			ItemStack tool = context.getParam(LootContextParams.TOOL);
			Object2IntMap.Entry<Holder<Enchantment>> fortune = tool.getTagEnchantments().entrySet().stream().filter(x -> x.getKey().is(Enchantments.FORTUNE)).findFirst().orElse(null);
			if (fortune != null) {
				rolls += fortune.getIntValue();
			}
		}
		for (int roll = 0; roll < rolls; roll++) {
			replacedLoot.add(prizeList.get(context.getLevel().getRandom().nextInt(prizeList.size())));
		}
		return replacedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}
