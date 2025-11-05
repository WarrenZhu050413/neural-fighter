package com.fuchengwarren.neuralfighter.mixin;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class NeuralFighterClientMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void neuralfighter$traceRenderStart(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
		// Debug hook to confirm client mixins load on Yarn.
		if (false) NeuralFighterMod.LOGGER.debug("Render tick delta: {}", tickDelta);
	}
}
