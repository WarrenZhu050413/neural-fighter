package com.fuchengwarren.neuralfighter.mixin;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class NeuralFighterClientMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void neuralfighter$traceRenderStart(RenderTickCounter tickCounter, boolean tick, CallbackInfo info) {
		// Debug hook to confirm client mixin wiring on modern Yarn signatures.
		if (false) {
			NeuralFighterMod.LOGGER.debug("Render tick counter: {}", tickCounter);
		}
	}
}
