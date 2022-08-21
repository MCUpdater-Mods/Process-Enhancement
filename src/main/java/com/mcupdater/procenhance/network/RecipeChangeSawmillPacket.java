package com.mcupdater.procenhance.network;

import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterEntity;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RecipeChangeSawmillPacket {

    private BlockPos blockPos;
    private ResourceLocation recipeId;

    public RecipeChangeSawmillPacket(BlockPos pos, ResourceLocation recipeId) {
        this.blockPos = pos;
        this.recipeId = recipeId;
    }

    public static void toBytes(RecipeChangeSawmillPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.blockPos);
        buf.writeResourceLocation(msg.recipeId);
    }

    public static RecipeChangeSawmillPacket fromBytes(FriendlyByteBuf buf) {
        return new RecipeChangeSawmillPacket(buf.readBlockPos(), buf.readResourceLocation());
    }

    public static void handle(RecipeChangeSawmillPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Level level;
            if (ctx.get().getSender() != null) {
                level = ctx.get().getSender().getLevel();
            } else {
                level = Minecraft.getInstance().level;
            }
            if (level.getBlockEntity(msg.blockPos) instanceof SawmillEntity machine) {
                SawmillRecipe sawmillRecipe = level.getRecipeManager().getAllRecipesFor(SawmillRecipe.Type.INSTANCE).stream().filter(recipe -> recipe.getId().equals(msg.recipeId)).findFirst().orElse(null);
                machine.setCurrentRecipe(sawmillRecipe);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
