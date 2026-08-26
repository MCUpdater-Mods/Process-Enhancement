package com.mcupdater.procenhance.items;

import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.client.gui.book.BookAddress;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import com.klikli_dev.modonomicon.registry.DataComponentRegistry;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GuideBookItem extends ModonomiconItem {
	public static final ResourceLocation ART_OF_PROCESSING = ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID, "art_of_processing");

	public GuideBookItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
		if (!itemInHand.has(DataComponentRegistry.BOOK_ID)) {
			itemInHand.set(DataComponentRegistry.BOOK_ID, ART_OF_PROCESSING);
		}

		if (pLevel.isClientSide) {
			Book book = BookDataManager.get().getBook(ART_OF_PROCESSING);
			BookGuiManager.get().openBook(BookAddress.defaultFor(book));
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide);
	}
}
