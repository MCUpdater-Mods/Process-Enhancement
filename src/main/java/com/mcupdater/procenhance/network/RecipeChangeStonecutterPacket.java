package com.mcupdater.procenhance.network;

import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RecipeChangeStonecutterPacket {

    private BlockPos blockPos;
    private ResourceLocation recipeId;

    public RecipeChangeStonecutterPacket(BlockPos pos, ResourceLocation recipeId) {
        this.blockPos = pos;
        this.recipeId = recipeId;
    }

    public static void toBytes(RecipeChangeStonecutterPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.blockPos);
        buf.writeResourceLocation(msg.recipeId);
    }

    public static RecipeChangeStonecutterPacket fromBytes(FriendlyByteBuf buf) {
        return new RecipeChangeStonecutterPacket(buf.readBlockPos(), buf.readResourceLocation());
    }

    public static void handle(RecipeChangeStonecutterPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Level level = ctx.get().getSender().getLevel();
            if (level.getBlockEntity(msg.blockPos) instanceof ElectricStonecutterEntity machine) {
                StonecutterRecipe stonecutterRecipe = level.getRecipeManager().getAllRecipesFor(RecipeType.STONECUTTING).stream().filter(recipe -> recipe.getId().equals(msg.recipeId)).findFirst().orElse(null);
                machine.setCurrentRecipe(stonecutterRecipe);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
