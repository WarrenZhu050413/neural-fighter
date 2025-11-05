package com.fuchengwarren.neuralfighter.content;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NeuralConnectionBlockEntity extends BlockEntity {
	private BlockPos from;
	private BlockPos to;
	private float weight;

	public NeuralConnectionBlockEntity(BlockPos pos, BlockState state) {
		super(RegistryObjects.NEURAL_CONNECTION_BE, pos, state);
	}

	public BlockPos getFrom() {
		return from;
	}

	public BlockPos getTo() {
		return to;
	}

	public float getWeight() {
		return weight;
	}

	void setLinks(BlockPos from, BlockPos to) {
		this.from = from;
		this.to = to;
		markDirty();
	}

  public void initializeLinks(ServerWorld world) {
    List<BlockPos> candidates = findNearbyNodes(world);
    if (candidates.size() >= 2) {
      candidates.sort(Comparator.comparingDouble(pos -> pos.getSquaredDistance(this.pos)));
      this.from = candidates.get(0).toImmutable();
      this.to = candidates.get(1).toImmutable();
    } else {
      this.from = null;
      this.to = null;
    }
  }

	private List<BlockPos> findNearbyNodes(ServerWorld world) {
		List<BlockPos> nodes = new ArrayList<>();
		int radius = 8;
		BlockPos.iterateOutwards(pos, radius, radius, radius).forEach(candidate -> {
			if (world.getBlockState(candidate).isOf(RegistryObjects.NEURAL_NODE_BLOCK)) {
				nodes.add(candidate.toImmutable());
			}
		});
		return nodes;
	}

	static void serverTick(ServerWorld world, BlockPos pos, BlockState state, NeuralConnectionBlockEntity connection) {
    connection.initializeLinks(world);
    if (connection.from != null && connection.to != null) {
      long time = world.getTime();
      connection.weight = (float) Math.sin((time + pos.hashCode()) / 20.0);
    }
  }
}
