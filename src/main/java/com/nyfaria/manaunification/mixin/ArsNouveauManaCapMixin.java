package com.nyfaria.manaunification.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "com.hollingsworth.arsnouveau.common.capability.ManaCap")
public abstract class ArsNouveauManaCapMixin {

    @Shadow(remap = false)
    @Final
    private LivingEntity livingEntity;

    @WrapMethod(method = "getCurrentMana", remap = false)
    public double wrapGetCurrentMana(Operation<Double> original) {
        if (livingEntity == null) {
            return original.call();
        }
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(livingEntity);
        if (holder != null) {
            return holder.getCurrentMana();
        }
        return original.call();
    }

    @WrapMethod(method = "setMana", remap = false)
    public double wrapSetMana(double mana, Operation<Double> original) {
        if (livingEntity == null) {
            return original.call(mana);
        }
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(livingEntity);
        if (holder != null) {
            holder.setCurrentMana(mana);
        }
        return original.call(mana);
    }

    @WrapMethod(method = "getMaxMana", remap = false)
    public int wrapGetMaxMana(Operation<Integer> original) {
        if (livingEntity == null) {
            return original.call();
        }
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(livingEntity);
        if (holder != null) {
            return (int) holder.getMaxMana();
        }
        return original.call();
    }
}
