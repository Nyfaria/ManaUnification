package com.nyfaria.manaunification.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.config.ManaConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSpell.class)
public class AbstractSpellMixin {

    @WrapMethod(method="getManaCost", remap = false)
    public int getManaCost(int level, Operation<Integer> original) {
        return Mth.floor(original.call(level) * ManaConfig.IRONS_SPELLS_MANA_COST_MULTIPLIER.get());
    }
}
