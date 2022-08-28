package com.mcupdater.procenhance.network;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RecipeChangePacket {

    private BlockPos blockPos;
    private ResourceLocation recipeId;

    public RecipeChangePacket(BlockPos pos, ResourceLocation recipeId) {
        this.blockPos = pos;
        this.recipeId = recipeId;
    }

    public static void toBytes(RecipeChangePacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.blockPos);
        buf.writeResourceLocation(msg.recipeId);
    }

    public static RecipeChangePacket fromBytes(FriendlyByteBuf buf) {
        return new RecipeChangePacket(buf.readBlockPos(), buf.readResourceLocation());
    }

    public static void handle(RecipeChangePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Level level = ctx.get().getSender().getLevel();
            if (level.getBlockEntity(msg.blockPos) instanceof AbstractMachineBlockEntity machine) {
                machine.setCurrentRecipe(msg.recipeId);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
