package com.fuchengwarren.neuralfighter.event;

import com.fuchengwarren.neuralfighter.content.NeuralDisplayBlockEntity;
import com.fuchengwarren.neuralfighter.content.RegistryObjects;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * Seeds new logins with a demo Neural Display block and some starter items.
 */
public final class PlayerJoinEventHandler {
    private PlayerJoinEventHandler() {}

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            ServerCommandSource source = player.getCommandSource();
            ServerWorld world = source.getWorld();

            // Give starter items if the player doesn't already have the block.
            if (!player.getInventory().contains(new ItemStack(RegistryObjects.NEURAL_DISPLAY_BLOCK.asItem()))) {
                player.getInventory().insertStack(new ItemStack(RegistryObjects.NEURAL_DISPLAY_BLOCK.asItem(), 16));
            }
            if (!player.getInventory().contains(new ItemStack(Items.ALLAY_SPAWN_EGG))) {
                player.getInventory().insertStack(new ItemStack(Items.ALLAY_SPAWN_EGG, 1));
            }

            // Auto-place a Neural Display in front of the player.
            BlockPos basePos = player.getBlockPos().offset(player.getHorizontalFacing(), 2);
            if (world.isAir(basePos)) {
                BlockState state = RegistryObjects.NEURAL_DISPLAY_BLOCK.getDefaultState();
                world.setBlockState(basePos, state);
                if (world.getBlockEntity(basePos) instanceof NeuralDisplayBlockEntity nde) {
                    nde.setDemoGraph(8, world.getTime());
                }
            }
        });
    }
}
