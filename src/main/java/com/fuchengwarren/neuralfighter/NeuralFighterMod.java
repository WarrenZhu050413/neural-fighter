package com.fuchengwarren.neuralfighter;

import com.fuchengwarren.neuralfighter.content.RegistryObjects;
import com.fuchengwarren.neuralfighter.event.PlayerJoinEventHandler;
import com.fuchengwarren.neuralfighter.visualization.NeuralDisplayManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.minecraft.server.command.CommandManager.literal;

public class NeuralFighterMod implements ModInitializer {
	public static final String MOD_ID = "neuralfighter";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RegistryObjects.register();
		registerCommands();
		PlayerJoinEventHandler.register();
		NeuralDisplayManager.init();
		LOGGER.info("Neural Fighter mod initialized.");
	}

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("neuralfighter")
                .then(literal("demo_graph")
                    .executes(ctx -> {
                        var source = ctx.getSource();
                        World w = source.getWorld();
                        BlockPos ppos = BlockPos.ofFloored(source.getPosition());
                        BlockPos target = null;
                        for (BlockPos bp : BlockPos.iterateOutwards(ppos, 8, 8, 8)) {
                            if (w.getBlockState(bp).isOf(RegistryObjects.NEURAL_DISPLAY_BLOCK)) {
                                target = bp.toImmutable();
                                break;
                            }
                        }
                        if (target != null && w instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                            NeuralDisplayManager.registerDisplay(serverWorld, target);
                            ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("Activated Neural Display visualization."), false);
                        } else {
                            ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("Place a Neural Display nearby first."), false);
                        }
                        return 1;
                    }))
            );
        });
    }
}
