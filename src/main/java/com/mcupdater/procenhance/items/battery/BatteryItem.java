package com.mcupdater.procenhance.items.battery;

import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class BatteryItem extends Item {
	public BatteryItem(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);
		if (!level.isClientSide) {
			var battery = stack.getCapability(Capabilities.EnergyStorage.ITEM);
			if (battery != null && entity instanceof Player player) {
				player.getInventory().items.stream().forEach(invStack -> {
					if (!invStack.is(Registration.BATTERY_ITEM.get())) {
						var target = invStack.getCapability(Capabilities.EnergyStorage.ITEM);
						if (target != null && target.canReceive() && target.getEnergyStored() < target.getMaxEnergyStored()) {
							int received = target.receiveEnergy(battery.extractEnergy(Integer.MAX_VALUE, true), false);
							battery.extractEnergy(received, false);
						}
					}
				});
				CuriosApi.getCuriosInventory(player).ifPresent(curiosInventory -> {
					int slots = curiosInventory.getEquippedCurios().getSlots();
					for (int slot = 0; slot < slots; slot++) {
						var invStack = curiosInventory.getEquippedCurios().getStackInSlot(slot);
						if (!invStack.is(Registration.BATTERY_ITEM.get())) {
							var target = invStack.getCapability(Capabilities.EnergyStorage.ITEM);
							if (target != null && target.canReceive() && target.getEnergyStored() < target.getMaxEnergyStored()) {
								int received = target.receiveEnergy(battery.extractEnergy(Integer.MAX_VALUE, true), false);
								battery.extractEnergy(received, false);
							}
						}
					}
				});
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pFlag) {
		super.appendHoverText(pStack, pContext, pTooltipComponents, pFlag);

		IEnergyStorage energyStorage = pStack.getCapability(Capabilities.EnergyStorage.ITEM, null);
		if (energyStorage != null) {
			pTooltipComponents.add(Component.literal(String.format("%d / %d FE",energyStorage.getEnergyStored(),energyStorage.getMaxEnergyStored())));
		}
	}

	@Override
	public boolean isBarVisible(ItemStack pStack) {
		return true;
	}

	@Override
	public int getBarWidth(ItemStack pStack) {
		@Nullable IEnergyStorage energyStore = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
		if (energyStore == null || energyStore.getMaxEnergyStored() == 0) return 13;

		return Math.min(13 * energyStore.getEnergyStored() / energyStore.getMaxEnergyStored(), 13);
	}

	@Override
	public int getBarColor(ItemStack pStack) {
		return Mth.color(1,0,0);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.NONE;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
}
