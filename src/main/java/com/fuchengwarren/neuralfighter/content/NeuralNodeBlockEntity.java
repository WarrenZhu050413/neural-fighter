package com.fuchengwarren.neuralfighter.content;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Stores activation state for node visualizations.
 */
public class NeuralNodeBlockEntity extends BlockEntity {
  private float activation;
  private final long seedOffset;

	public NeuralNodeBlockEntity(BlockPos pos, BlockState state) {
		super(RegistryObjects.NEURAL_NODE_BE, pos, state);
		this.seedOffset = Random.createLocal().nextLong();
	}

	public float getActivation() {
		return activation;
	}

	void updateActivation(float activation) {
		this.activation = activation;
		markDirty();
	}

	static void serverTick(World world, BlockPos pos, BlockState state, NeuralNodeBlockEntity node) {
		if (world == null) {
			return;
		}
		long t = world.getTime();
		float layerBias = state.contains(NeuralNodeBlock.LAYER) ? state.get(NeuralNodeBlock.LAYER) * 0.35f : 0f;
		float value = (float) (0.5 + 0.5 * Math.sin((t + node.seedOffset) / 20.0 + layerBias));
		node.updateActivation(value);
	}

  // No persistence required for visualization-only data.
}
