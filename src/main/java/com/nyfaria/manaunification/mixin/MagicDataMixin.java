package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.MagicDataPlayer;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MagicData.class)
public class MagicDataMixin implements MagicDataPlayer {

    @Shadow private ServerPlayer serverPlayer;

    @Override
    public ServerPlayer getPlayer() {
        return serverPlayer;
    }

    @WrapMethod(method = "getMana",remap = false)
    public float getMana(Operation<Float> original) {
        if(getPlayer() == null) {
            return original.call();
        }
        IManaCap blah = getPlayer().getCapability(CapabilityRegistry.MANA_CAPABILITY).orElse(null);
        if (blah != null) {
            return (float)blah.getCurrentMana();
        }
        return original.call();
    }
    @WrapMethod(method = "setMana",remap = false)
    public void setMana(float mana, Operation<Void> original) {
        if(getPlayer() == null) {
            original.call(mana);
            return;
        }
        IManaCap blah = getPlayer().getCapability(CapabilityRegistry.MANA_CAPABILITY).orElse(null);
        if (blah != null) {
            blah.setMana(mana);
        }
        original.call(mana);
    }
}
