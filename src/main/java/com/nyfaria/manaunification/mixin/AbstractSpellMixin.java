package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.MagicDataPlayer;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSpell.class)
public class AbstractSpellMixin {
//    @WrapOperation(method = "canBeCastedBy", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"), remap = false)
//    public float getMana(MagicData instance, Operation<Float> original) {
//        IManaCap blah = ((MagicDataPlayer) instance).getPlayer().getCapability(CapabilityRegistry.MANA_CAPABILITY).orElse(null);
//        if (blah != null) {
//            return (float)blah.getCurrentMana();
//        }
//        return original.call();
//    }
//    @WrapOperation(method="castSpell", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/magic/MagicData;getMana()F"), remap = false)
//    public float getMana2(MagicData instance, Operation<Float> original) {
//        IManaCap blah = ((MagicDataPlayer) instance).getPlayer().getCapability(CapabilityRegistry.MANA_CAPABILITY).orElse(null);
//        if (blah != null) {
//            return (float)blah.getCurrentMana();
//        }
//        return original.call();
//    }

    @WrapMethod(method="getManaCost", remap = false)
    public int getManaCost(int level, Operation<Integer> original) {
        return Mth.floor(original.call(level) * 1.5);
    }
}
