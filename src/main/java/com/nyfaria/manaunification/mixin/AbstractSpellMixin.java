package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.nyfaria.manaunification.MagicDataPlayer;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSpell.class)
public class AbstractSpellMixin {
    @WrapOperation(method = "canBeCastedBy", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"), remap = false)
    public float getMana(MagicData instance, Operation<Float> original) {
        IManaCap blah = ((MagicDataPlayer) instance).getPlayer().getCapability(CapabilityRegistry.MANA_CAPABILITY).orElse(null);
        if (blah != null) {
            return (float)blah.getCurrentMana();
        }
        return original.call();
    }
}
