package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagicManager.class)
public class MagicManagerMixin {

    @Inject(method="regenPlayerMana", at= @At(value = "HEAD"), cancellable = true, remap = false)
    public void regenPlayerMana(ServerPlayer serverPlayer, MagicData playerMagicData, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
