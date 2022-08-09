package com.mcupdater.procenhance.blocks.copper_wire;

import com.mcupdater.procenhance.ProcessEnhancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CopperWireBlock extends BaseEntityBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public CopperWireBlock() {
        super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.5f).noOcclusion());
        registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH, false)
                        .setValue(SOUTH, false)
                        .setValue(EAST, false)
                        .setValue(WEST, false)
                        .setValue(UP, false)
                        .setValue(DOWN, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH,SOUTH,EAST,WEST,UP,DOWN);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CopperWireEntity(pPos, pState);
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState,pLevel,pPos,pBlock,pFromPos,pIsMoving);
        BlockState newState = getState(pLevel, pPos, pState);
        if (!pState.getProperties().stream().allMatch(property -> pState.getValue(property).equals(newState.getValue(property)))) {
            pLevel.setBlockAndUpdate(pPos, newState);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(pContext.getLevel(), pContext.getClickedPos(), null);
    }

    public BlockState getState(Level pLevel, BlockPos pPos, @Nullable BlockState pOldState) {
        return defaultBlockState()
                .setValue(UP, isSideValid(pLevel, pPos, Direction.UP))
                .setValue(DOWN, isSideValid(pLevel, pPos, Direction.DOWN))
                .setValue(NORTH, isSideValid(pLevel, pPos, Direction.NORTH))
                .setValue(SOUTH, isSideValid(pLevel, pPos, Direction.SOUTH))
                .setValue(EAST, isSideValid(pLevel, pPos, Direction.EAST))
                .setValue(WEST, isSideValid(pLevel, pPos, Direction.WEST));
    }

    private boolean isSideValid(Level pLevel, BlockPos pPos, Direction side) {
        BlockEntity neighborEntity = pLevel.getBlockEntity(pPos.relative(side));
        return neighborEntity != null && neighborEntity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).isPresent();
    }

    public BooleanProperty getSideProperty(Direction side) {
        return switch (side) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = WIRE_CORE;
        if (pState.getValue(UP)) {
            shape = Shapes.or(shape, WIRE_UP);
        }
        if (pState.getValue(DOWN)) {
            shape = Shapes.or(shape, WIRE_DOWN);
        }
        if (pState.getValue(NORTH)) {
            shape = Shapes.or(shape, WIRE_NORTH);
        }
        if (pState.getValue(SOUTH)) {
            shape = Shapes.or(shape, WIRE_SOUTH);
        }
        if (pState.getValue(EAST)) {
            shape = Shapes.or(shape, WIRE_EAST);
        }
        if (pState.getValue(WEST)) {
            shape = Shapes.or(shape, WIRE_WEST);
        }
        return shape;
    }

    // Shapes for collision
    public static final VoxelShape WIRE_CORE = Block.box(6D,6D,6D,10D,10D,10D);
    public static final VoxelShape WIRE_UP = Block.box(7D, 9D, 7D, 9D, 16D, 9D);
    public static final VoxelShape WIRE_DOWN = Block.box(7D, 0D, 7D, 9D, 7D, 9D);
    public static final VoxelShape WIRE_NORTH = Block.box(7D, 7D, 0D, 9D, 9D, 7D);
    public static final VoxelShape WIRE_SOUTH = Block.box(7D, 7D, 9D, 9D, 9D, 16D);
    public static final VoxelShape WIRE_EAST = Block.box(9D, 7D, 7D, 16D, 9D, 9D);
    public static final VoxelShape WIRE_WEST = Block.box(0D, 7D, 7D, 7D, 9D, 9D);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (lvl, pos, state, entity) -> {
            if (entity instanceof CopperWireEntity wire) {
                wire.tick(lvl, pos, state);
            }
        };
    }
}
