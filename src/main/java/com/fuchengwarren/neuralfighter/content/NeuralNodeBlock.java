package com.fuchengwarren.neuralfighter.content;

import com.fuchengwarren.neuralfighter.visualization.NeuralNetworkVisualizationManager;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a neural network node visualization block.
 */
public class NeuralNodeBlock extends BlockWithEntity {
	public static final MapCodec<NeuralNodeBlock> CODEC = createCodec(NeuralNodeBlock::new);
	public static final IntProperty LAYER = IntProperty.of("layer", 0, 7);

	public NeuralNodeBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(LAYER, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LAYER);
	}

	@Override
	public MapCodec<NeuralNodeBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NeuralNodeBlockEntity(pos, state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (!world.isClient() && world instanceof ServerWorld serverWorld) {
			NeuralNetworkVisualizationManager.registerNode(serverWorld, pos);
		}
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient()) {
			return null;
		}
		return (world1, pos1, state1, blockEntity) -> {
			if (world1 instanceof ServerWorld serverWorld && blockEntity instanceof NeuralNodeBlockEntity node) {
				NeuralNodeBlockEntity.serverTick(serverWorld, pos1, state1, node);
			}
		};
	}
}
