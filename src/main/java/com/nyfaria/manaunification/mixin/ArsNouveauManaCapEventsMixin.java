package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.common.event.ManaCapEvents;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ManaCapEvents.class)
public class ArsNouveauManaCapEventsMixin {

    @Inject(method = "playerOnTick", at = @At("HEAD"), cancellable = true, remap = false)
    private static void playerOnTick(TickEvent.PlayerTickEvent e, CallbackInfo ci) {
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(e.player);
        if (holder != null) {
            ci.cancel();
        }
    }

    @Inject(method = "syncPlayerEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void syncPlayerEvent(Player playerEntity, CallbackInfo ci) {
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(playerEntity);
        if (holder != null) {
            holder.updateTracking();
            ci.cancel();
        }
    }
}
