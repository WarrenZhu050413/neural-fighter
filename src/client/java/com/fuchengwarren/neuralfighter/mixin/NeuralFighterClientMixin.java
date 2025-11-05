package com.fuchengwarren.neuralfighter.mixin;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class NeuralFighterClientMixin {
	@Inject(method = "run", at = @At("HEAD"))
	private void neuralfighter$logClientStart(CallbackInfo info) {
		NeuralFighterMod.LOGGER.info("Neural Fighter client loop initialized.");
	}
}
