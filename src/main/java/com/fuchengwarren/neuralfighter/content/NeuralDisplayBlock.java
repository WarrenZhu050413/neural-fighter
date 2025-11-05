package com.fuchengwarren.neuralfighter.content;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class NeuralDisplayBlock extends BlockWithEntity {
    public NeuralDisplayBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<NeuralDisplayBlock> CODEC = createCodec(NeuralDisplayBlock::new);

    @Override
    public MapCodec<NeuralDisplayBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NeuralDisplayBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.World world, BlockState state, net.minecraft.block.entity.BlockEntityType<T> type) {
        return world.isClient() ? null : (w, p, s, be) -> {
            if (be instanceof NeuralDisplayBlockEntity nde) nde.serverTick();
        };
    }
}
