package com.mcupdater.procenhance.render;

import com.mcupdater.procenhance.blocks.concealed_wire.ConcealedWireEntity;
import com.mcupdater.procenhance.setup.Registration;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.common.Tags;

public class ConcealedWireWallRenderer implements BlockEntityRenderer<ConcealedWireEntity> {
	private BlockRenderDispatcher blockRenderer;

	public ConcealedWireWallRenderer(BlockEntityRendererProvider.Context context) {
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(ConcealedWireEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		BlockState renderState = copyProperties(blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()), blockEntity.mimic);
		LocalPlayer player = Minecraft.getInstance().player;
		ItemStack mainHand = player.getMainHandItem();
		if (player.isDiscrete() && !mainHand.isEmpty() && mainHand.is(Tags.Items.TOOLS_WRENCH)) renderState = copyProperties(blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()), Registration.CONCEALEDWIRE_WALL_BLOCK.get().defaultBlockState());

		if (renderState != null) {
			ModelBlockRenderer.enableCaching();
			poseStack.pushPose();
			this.renderBlock(blockEntity.getBlockPos(),renderState, poseStack, bufferSource, blockEntity.getLevel(), false, packedOverlay);
			poseStack.popPose();
			ModelBlockRenderer.clearCache();
		}
	}

	private void renderBlock(BlockPos pos, BlockState blockState, PoseStack poseStack, MultiBufferSource bufferSource, Level level, Boolean extended, int packedOverlay) {
		ClientHooks.renderPistonMovedBlocks(pos, blockState, poseStack, bufferSource, level, extended, packedOverlay, blockRenderer);
	}

	@Override
	public int getViewDistance() {
		return 96;
	}

	private BlockState copyProperties(BlockState src, BlockState dest) {
		BlockState newState = dest;
		StateDefinition<Block, BlockState> oldStates = src.getBlock().getStateDefinition();
		for (Property<?> property : src.getProperties()) {
			if (newState.hasProperty(property)) {
				newState = copyValue(src, newState, property);
			}
		}
		return newState;
	}

	private <T extends Comparable<T>> BlockState copyValue(BlockState src, BlockState dest, Property<T> property) {
		return dest.trySetValue(property, src.getValue(property));
	}
}
