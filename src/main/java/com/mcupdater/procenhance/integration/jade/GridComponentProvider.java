package com.mcupdater.procenhance.integration.jade;

import com.mcupdater.procenhance.grid.INodeHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.awt.*;

public enum GridComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		if (accessor.getPlayer().getMainHandItem().is(Tags.Items.TOOLS_WRENCH) || accessor.getPlayer().getOffhandItem().is(Tags.Items.TOOLS_WRENCH)) {
			if (accessor.getServerData().contains("nodeId")) {
				CompoundTag data = accessor.getServerData();
				tooltip.add(Component.literal("Grid: ").append(data.getString("gridId")).append("; Size: ").append(Long.toString(data.getLong("gridSize"))));
				tooltip.add(Component.literal("Valid: ").append(Boolean.toString(data.getBoolean("valid"))).append("; Replacement: ").append(data.getString("replacementId")));
				tooltip.add(Component.literal("Node: ").append(data.getString("nodeId")));
			} else {
				tooltip.add(Component.literal("Node data missing").withColor(new Color(255, 0, 0).getRGB()));
			}
		}
	}

	@Override
	public ResourceLocation getUid() {
		return PEPlugin.GRID;
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
		INodeHolder holder = (INodeHolder) blockAccessor.getBlockEntity();
		compoundTag.putString("nodeId", holder.getNode().getNodeId().toString());
		compoundTag.putString("gridId", holder.getNode().getRawGrid().getGridId().toString());
		compoundTag.putLong("gridSize", holder.getNode().getRawGrid().getSize());
		compoundTag.putBoolean("valid", holder.getNode().getRawGrid().isValid());
		compoundTag.putString("replacementId", holder.getNode().getRawGrid().isValid() ? "N/A" : (holder.getNode().getRawGrid().getReplacedBy() != null) ? holder.getNode().getRawGrid().getReplacedBy().getGridId().toString() : "No Replacement");
	}
}
