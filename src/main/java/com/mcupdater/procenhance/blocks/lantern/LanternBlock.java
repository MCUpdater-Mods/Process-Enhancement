package com.mcupdater.procenhance.blocks.lantern;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class LanternBlock extends AbstractMachineBlock {
	public static final MapCodec<LanternBlock> CODEC = simpleCodec(LanternBlock::new);
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), map -> {
		map.put(Direction.NORTH, NORTH);
		map.put(Direction.SOUTH, SOUTH);
		map.put(Direction.EAST, EAST);
		map.put(Direction.WEST, WEST);
	}));


	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public LanternBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(ACTIVE, Boolean.valueOf(false))
						.setValue(NORTH, Boolean.valueOf(false))
						.setValue(SOUTH, Boolean.valueOf(false))
						.setValue(EAST, Boolean.valueOf(false))
						.setValue(WEST, Boolean.valueOf(false))
		);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockGetter blockGetter = context.getLevel();
		BlockPos selfPos = context.getClickedPos();
		BlockPos northPos = selfPos.north();
		BlockPos southPos = selfPos.south();
		BlockPos eastPos = selfPos.east();
		BlockPos westPos = selfPos.west();
		BlockState northState = blockGetter.getBlockState(northPos);
		BlockState southState = blockGetter.getBlockState(southPos);
		BlockState eastState = blockGetter.getBlockState(eastPos);
		BlockState westState = blockGetter.getBlockState(westPos);
		return super.getStateForPlacement(context)
				.setValue(NORTH, northState.isFaceSturdy(blockGetter, northPos, Direction.SOUTH))
				.setValue(SOUTH, southState.isFaceSturdy(blockGetter, southPos, Direction.NORTH))
				.setValue(EAST, eastState.isFaceSturdy(blockGetter, eastPos, Direction.WEST))
				.setValue(WEST, westState.isFaceSturdy(blockGetter, westPos, Direction.EAST));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(ACTIVE,FACING,NORTH,EAST,WEST,SOUTH);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		BlockState newState = getState(level, pos, state);
		if (!state.getProperties().stream().allMatch(property -> state.getValue(property).equals(newState.getValue(property)))) {
			level.setBlockAndUpdate(pos, newState);
		}
	}

	public BlockState getState(Level level, BlockPos pos, @Nullable BlockState oldState) {
		return defaultBlockState()
				.setValue(NORTH, level.getBlockState(pos.north()).isFaceSturdy(level, pos.north(), Direction.SOUTH))
				.setValue(SOUTH, level.getBlockState(pos.south()).isFaceSturdy(level, pos.south(), Direction.NORTH))
				.setValue(EAST, level.getBlockState(pos.east()).isFaceSturdy(level, pos.east(), Direction.WEST))
				.setValue(WEST, level.getBlockState(pos.west()).isFaceSturdy(level, pos.west(), Direction.EAST));
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new LanternEntity(blockPos, blockState);
	}

	@Override
	public @NotNull RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		VoxelShape shape = Block.box(5,0,5,11,16,11);
		shape = Shapes.or(shape, Block.box(3,2,3, 13,14, 13));
		return shape;
	}

	@Nullable
	@Override
	public <T extends BlockEntity>BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return (lvl, pos, state, entity) -> {
			if (entity instanceof LanternEntity lantern) {
				lantern.tick(lvl, pos, state);
			}
		};
	}
}
