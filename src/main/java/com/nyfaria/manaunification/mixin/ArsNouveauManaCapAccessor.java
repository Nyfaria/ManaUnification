package com.nyfaria.manaunification.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "com.hollingsworth.arsnouveau.common.capability.ManaCap", remap = false)
public interface ArsNouveauManaCapAccessor {

    @Mutable
    @Accessor("livingEntity")
    void manaunification$setLivingEntity(LivingEntity entity);

    @Accessor("livingEntity")
    LivingEntity manaunification$getLivingEntity();
}
