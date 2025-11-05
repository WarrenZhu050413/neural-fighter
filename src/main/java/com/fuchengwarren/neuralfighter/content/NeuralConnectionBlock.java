package com.fuchengwarren.neuralfighter.content;

import com.fuchengwarren.neuralfighter.visualization.NeuralNetworkVisualizationManager;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NeuralConnectionBlock extends BlockWithEntity {
	public static final MapCodec<NeuralConnectionBlock> CODEC = createCodec(NeuralConnectionBlock::new);

	public NeuralConnectionBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<NeuralConnectionBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NeuralConnectionBlockEntity(pos, state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (!world.isClient() && world instanceof ServerWorld serverWorld) {
			NeuralNetworkVisualizationManager.registerConnection(serverWorld, pos);
			if (world.getBlockEntity(pos) instanceof NeuralConnectionBlockEntity connection) {
				connection.initializeLinks(serverWorld);
			}
		}
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient()) {
			return null;
		}
		return (world1, pos1, state1, blockEntity) -> {
			if (world1 instanceof ServerWorld serverWorld && blockEntity instanceof NeuralConnectionBlockEntity connection) {
				NeuralConnectionBlockEntity.serverTick(serverWorld, pos1, state1, connection);
			}
		};
	}
}
