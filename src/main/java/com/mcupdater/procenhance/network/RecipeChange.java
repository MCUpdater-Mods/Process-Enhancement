package com.mcupdater.procenhance.network;

import com.mcupdater.mculib.block.AbstractMachineBlockEntity;
import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;

public record RecipeChange(BlockPos blockPos, ResourceLocation recipeId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RecipeChange> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(ProcessEnhancement.MODID,"recipe_change"));
    public static final StreamCodec<FriendlyByteBuf, RecipeChange> STREAM_CODEC = StreamCodec.of(
            RecipeChange::encode,
            RecipeChange::decode
    );

    public static void encode(FriendlyByteBuf buf, RecipeChange msg) {
        buf.writeBlockPos(msg.blockPos);
        buf.writeResourceLocation(msg.recipeId);
    }

    public static RecipeChange decode(FriendlyByteBuf buf) {
        return new RecipeChange(buf.readBlockPos(), buf.readResourceLocation());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class PayloadHandler {

        public static void handle(RecipeChange recipeChange, IPayloadContext context) {
            if (context instanceof ServerPayloadContext serverPayloadContext) {
                ServerLevel level = serverPayloadContext.player().serverLevel();
                if (level.getBlockEntity(recipeChange.blockPos) instanceof AbstractMachineBlockEntity machine) {
                    machine.setCurrentRecipe(recipeChange.recipeId);
                }
            }
        }
    }
}
