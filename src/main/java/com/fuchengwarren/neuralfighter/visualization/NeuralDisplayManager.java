package com.fuchengwarren.neuralfighter.visualization;

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
 * Tracks neural display blocks and spawns the visualization particles server-side.
 */
public final class NeuralDisplayManager {
    private static final Map<ServerWorld, Set<BlockPos>> ACTIVE_DISPLAYS = new ConcurrentHashMap<>();

    private NeuralDisplayManager() {
    }

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(NeuralDisplayManager::tickWorld);
    }

    public static void registerDisplay(ServerWorld world, BlockPos pos) {
        ACTIVE_DISPLAYS.computeIfAbsent(world, w -> new HashSet<>()).add(pos.toImmutable());
    }

    private static void tickWorld(ServerWorld world) {
        Set<BlockPos> positions = ACTIVE_DISPLAYS.get(world);
        if (positions == null || positions.isEmpty()) {
            return;
        }

        positions.removeIf(pos -> !world.getBlockState(pos).isOf(RegistryObjects.NEURAL_DISPLAY_BLOCK));

        long time = world.getTime();
        for (BlockPos pos : positions) {
            spawnRingParticles(world, pos, time);
        }
    }

    private static void spawnRingParticles(ServerWorld world, BlockPos pos, long time) {
        int nodes = 8;
        double baseX = pos.getX() + 0.5;
        double baseY = pos.getY() + 0.75;
        double baseZ = pos.getZ() + 0.5;
        float radius = 0.6f;

        float[] xs = new float[nodes];
        float[] zs = new float[nodes];
        for (int i = 0; i < nodes; i++) {
            float angle = (float) (i * (Math.PI * 2) / nodes);
            xs[i] = MathHelper.cos(angle) * radius;
            zs[i] = MathHelper.sin(angle) * radius;
            double amp = 0.5 + 0.5 * Math.sin((time / 10.0) + i * 0.6);
            world.spawnParticles(ParticleTypes.END_ROD, baseX + xs[i], baseY, baseZ + zs[i], (int) (1 + amp * 2), 0.01, 0.01, 0.01, 0.0);
        }

        for (int i = 0; i < nodes; i++) {
            int next = (i + 1) % nodes;
            for (int s = 0; s <= 6; s++) {
                float t = s / 6.0f;
                double x = MathHelper.lerp(t, xs[i], xs[next]);
                double z = MathHelper.lerp(t, zs[i], zs[next]);
                world.spawnParticles(ParticleTypes.END_ROD, baseX + x, baseY, baseZ + z, 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }
}
