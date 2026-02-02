package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.NotEnoughManaPacket;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpellResolver.class)
public abstract class ArsNouveauSpellResolverMixin {

    @Shadow(remap = false)
    public abstract int getResolveCost();

    @Shadow(remap = false)
    public SpellContext spellContext;

    @Shadow(remap = false)
    public boolean silent;

    @Inject(method = "enoughMana", at = @At("HEAD"), cancellable = true, remap = false)
    protected void enoughMana(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(entity);
        if (holder != null) {
            int totalCost = getResolveCost();
            boolean canCast = totalCost <= holder.getCurrentMana() || (entity instanceof Player player && player.isCreative());
            if (!canCast && !entity.getCommandSenderWorld().isClientSide && !silent) {
                PortUtil.sendMessageNoSpam(entity, Component.translatable("ars_nouveau.spell.no_mana"));
                if (entity instanceof ServerPlayer serverPlayer)
                    Networking.sendToPlayerClient(new NotEnoughManaPacket(totalCost), serverPlayer);
            }
            cir.setReturnValue(canCast);
        }
    }

    @Inject(method = "expendMana", at = @At("HEAD"), cancellable = true, remap = false)
    public void expendMana(CallbackInfo ci) {
        LivingEntity caster = spellContext.getUnwrappedCaster();
        if (caster == null) return;

        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(caster);
        if (holder != null) {
            int totalCost = getResolveCost();
            holder.consumeMana(totalCost);
            ci.cancel();
        }
    }
}
