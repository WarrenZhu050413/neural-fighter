package com.fuchengwarren.neuralfighter.content;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;

public class NeuralDisplayBlockEntity extends BlockEntity {
    private int nodeCount = 6;
    private int[] edgeU = new int[]{0,1,2,3,4,5};
    private int[] edgeV = new int[]{1,2,3,4,5,0};
    private float[] weights = new float[]{0.5f, -0.3f, 0.8f, -0.6f, 0.4f, 0.2f};
    private float[] activations = new float[nodeCount];
    private long phaseSeed = 0L;

    public NeuralDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(RegistryObjects.NEURAL_DISPLAY_BE, pos, state);
    }

    public int getNodeCount() { return nodeCount; }
    public int[] getEdgeU() { return edgeU; }
    public int[] getEdgeV() { return edgeV; }
    public float[] getWeights() { return weights; }
    public float[] getActivations() { return activations; }

    public void setDemoGraph(int nodes, long seed) {
        this.nodeCount = Math.max(2, Math.min(16, nodes));
        this.activations = new float[this.nodeCount];
        // ring edges
        this.edgeU = new int[this.nodeCount];
        this.edgeV = new int[this.nodeCount];
        this.weights = new float[this.nodeCount];
        for (int i = 0; i < this.nodeCount; i++) {
            edgeU[i] = i;
            edgeV[i] = (i + 1) % this.nodeCount;
        }
        Random r = Random.create(seed);
        for (int i = 0; i < this.nodeCount; i++) {
            weights[i] = (r.nextFloat() * 2f) - 1f; // [-1,1]
        }
        this.phaseSeed = seed;
        markDirty();
    }

    public long getPhaseSeed() { return phaseSeed; }

    // Server tick spawns particles to visualize nodes/edges.
    public void serverTick() {
        if (!(world instanceof ServerWorld sw)) return;
        int n = nodeCount;
        if (n <= 0) return;
        // layout nodes on a circle
        float radius = 0.5f;
        double baseX = pos.getX() + 0.5;
        double baseY = pos.getY() + 0.7;
        double baseZ = pos.getZ() + 0.5;
        float[] xs = new float[n];
        float[] zs = new float[n];
        long t = sw.getTime();
        for (int i = 0; i < n; i++) {
            float angle = (float) (i * (Math.PI * 2) / n);
            xs[i] = MathHelper.cos(angle) * radius;
            zs[i] = MathHelper.sin(angle) * radius;
            // pulse nodes
            double amp = 0.5 + 0.5 * Math.sin((t / 10.0) + i * 0.6);
            sw.spawnParticles(ParticleTypes.END_ROD, baseX + xs[i], baseY, baseZ + zs[i], (int) (1 + amp * 2), 0.02, 0.02, 0.02, 0.0);
        }
        // edges: spawn a few particles along the segment
        for (int i = 0; i < Math.min(edgeU.length, edgeV.length); i++) {
            int a = MathHelper.clamp(edgeU[i], 0, n-1);
            int b = MathHelper.clamp(edgeV[i], 0, n-1);
            for (int s = 0; s <= 6; s++) {
                float t0 = s / 6.0f;
                double x = MathHelper.lerp(t0, xs[a], xs[b]);
                double z = MathHelper.lerp(t0, zs[a], zs[b]);
                sw.spawnParticles(ParticleTypes.END_ROD, baseX + x, baseY, baseZ + z, 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

}
