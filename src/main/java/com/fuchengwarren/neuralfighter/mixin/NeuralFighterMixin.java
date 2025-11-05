package com.fuchengwarren.neuralfighter.mixin;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Server-side bootstrap hook so we know worlds are loading correctly.
 */
@Mixin(MinecraftServer.class)
public class NeuralFighterMixin {
	@Inject(method = "loadLevel", at = @At("HEAD"))
	private void neuralfighter$logServerStart(CallbackInfo info) {
		NeuralFighterMod.LOGGER.info("Neural Fighter server world loading.");
	}
}
