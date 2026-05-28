package com.mcupdater.procenhance.items.tools.crusher;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class CrusherItem extends DiggerItem {
	private int maxDurability;

	public CrusherItem(Tier tier, Properties properties, int durability) {
		super(tier, Registration.MINEABLE_WITH_CRUSHER, properties);
		this.maxDurability = durability;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return (this == Registration.WOODEN_CRUSHER_ITEM.get() ? 200 : 0);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return this.maxDurability;
	}
}
