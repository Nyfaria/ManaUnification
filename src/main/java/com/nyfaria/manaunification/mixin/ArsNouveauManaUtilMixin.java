package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ManaUtil.class)
public class ArsNouveauManaUtilMixin {

    @Inject(method = "getCurrentMana", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getCurrentMana(LivingEntity e, CallbackInfoReturnable<Double> cir) {
        if (e == null) return;
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(e);
        if (holder != null) {
            cir.setReturnValue(holder.getCurrentMana());
        }
    }

    @Inject(method = "getMaxMana", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getMaxMana(Player e, CallbackInfoReturnable<Integer> cir) {
        if (e == null) return;
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(e);
        if (holder != null) {
            cir.setReturnValue((int) holder.getMaxMana());
        }
    }

    @Inject(method = "getManaRegen", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getManaRegen(Player e, CallbackInfoReturnable<Double> cir) {
        if (e == null) return;
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(e);
        if (holder != null) {
            cir.setReturnValue(holder.getManaRegen());
        }
    }
}
