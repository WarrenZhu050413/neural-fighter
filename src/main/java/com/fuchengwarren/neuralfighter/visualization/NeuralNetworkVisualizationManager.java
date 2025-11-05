package com.fuchengwarren.neuralfighter.visualization;

import com.fuchengwarren.neuralfighter.content.NeuralConnectionBlockEntity;
import com.fuchengwarren.neuralfighter.content.NeuralNodeBlockEntity;
import com.fuchengwarren.neuralfighter.content.RegistryObjects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-side visualization manager for neural network node and connection blocks.
 */
public final class NeuralNetworkVisualizationManager {
	private static final Map<ServerWorld, Set<BlockPos>> NODE_POSITIONS = new ConcurrentHashMap<>();
	private static final Map<ServerWorld, Set<BlockPos>> CONNECTION_POSITIONS = new ConcurrentHashMap<>();

	private NeuralNetworkVisualizationManager() {
	}

	public static void init() {
		ServerTickEvents.END_WORLD_TICK.register(NeuralNetworkVisualizationManager::tickWorld);
	}

	public static void registerNode(ServerWorld world, BlockPos pos) {
		NODE_POSITIONS.computeIfAbsent(world, w -> new HashSet<>()).add(pos.toImmutable());
	}

	public static void unregisterNode(ServerWorld world, BlockPos pos) {
		Set<BlockPos> nodes = NODE_POSITIONS.get(world);
		if (nodes != null) {
			nodes.remove(pos);
		}
	}

	public static void registerConnection(ServerWorld world, BlockPos pos) {
		CONNECTION_POSITIONS.computeIfAbsent(world, w -> new HashSet<>()).add(pos.toImmutable());
	}

	public static void unregisterConnection(ServerWorld world, BlockPos pos) {
		Set<BlockPos> connections = CONNECTION_POSITIONS.get(world);
		if (connections != null) {
			connections.remove(pos);
		}
	}

	private static void tickWorld(ServerWorld world) {
		spawnNodeParticles(world);
		spawnConnectionParticles(world);
	}

	private static void spawnNodeParticles(ServerWorld world) {
		Set<BlockPos> nodes = NODE_POSITIONS.get(world);
		if (nodes == null || nodes.isEmpty()) {
			return;
		}
		nodes.removeIf(pos -> !world.getBlockState(pos).isOf(RegistryObjects.NEURAL_NODE_BLOCK));
		for (BlockPos pos : nodes) {
			if (!(world.getBlockEntity(pos) instanceof NeuralNodeBlockEntity node)) {
				continue;
			}
			float activation = node.getActivation();
			double baseX = pos.getX() + 0.5;
			double baseY = pos.getY() + 0.6;
			double baseZ = pos.getZ() + 0.5;
			int count = MathHelper.clamp(2 + Math.round(activation * 6), 2, 12);
			for (int i = 0; i < count; i++) {
				double angle = (System.nanoTime() / 1e7 + i * 45) % 360;
				double radius = 0.25 + activation * 0.2;
				double x = baseX + Math.cos(angle) * radius;
				double z = baseZ + Math.sin(angle) * radius;
				world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, x, baseY + activation * 0.3, z, 1, 0.02, 0.02, 0.02, 0.0);
			}
		}
	}

	private static void spawnConnectionParticles(ServerWorld world) {
		Set<BlockPos> connections = CONNECTION_POSITIONS.get(world);
		if (connections == null || connections.isEmpty()) {
			return;
		}
		connections.removeIf(pos -> !world.getBlockState(pos).isOf(RegistryObjects.NEURAL_CONNECTION_BLOCK));
		for (BlockPos pos : connections) {
			if (!(world.getBlockEntity(pos) instanceof NeuralConnectionBlockEntity connection)) {
				continue;
			}
			BlockPos from = connection.getFrom();
			BlockPos to = connection.getTo();
			if (from == null || to == null) {
				continue;
			}
			if (!(world.getBlockState(from).isOf(RegistryObjects.NEURAL_NODE_BLOCK) && world.getBlockState(to).isOf(RegistryObjects.NEURAL_NODE_BLOCK))) {
				continue;
			}
			double startX = from.getX() + 0.5;
			double startY = from.getY() + 0.6;
			double startZ = from.getZ() + 0.5;
			double endX = to.getX() + 0.5;
			double endY = to.getY() + 0.6;
			double endZ = to.getZ() + 0.5;
			float weight = connection.getWeight();
			int segments = 12;
			for (int i = 0; i <= segments; i++) {
				double t = i / (double) segments;
				double x = MathHelper.lerp(t, startX, endX);
				double y = MathHelper.lerp(t, startY, endY);
				double z = MathHelper.lerp(t, startZ, endZ);
				double vx = (endX - startX) / segments * 0.1;
				double vy = (endY - startY) / segments * 0.1;
				double vz = (endZ - startZ) / segments * 0.1;
				world.spawnParticles(weight >= 0 ? ParticleTypes.END_ROD : ParticleTypes.CLOUD, x, y, z, 0, vx, vy, vz, 0.01);
			}
		}
	}
}
