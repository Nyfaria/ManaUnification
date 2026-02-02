package com.nyfaria.manaunification.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.hollingsworth.arsnouveau.common.event.ManaCapEvents")
public class ArsNouveauManaRegenMixin {

    @Inject(method = "playerOnTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cancelManaRegen(CallbackInfo ci) {
        ci.cancel();
    }
}
