package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import dev.shadowsoffire.apotheosis.adventure.affix.AttributeAffix;
import dev.shadowsoffire.placebo.util.StepFunction;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttributeAffix.ModifierInst.class)
public class ModifierInstMixin {

    @Shadow @Final @Mutable
    private Attribute attr;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Attribute attra, AttributeModifier.Operation op, StepFunction valueFactory, CallbackInfo ci) {
        if(attr == AttributeRegistry.MAX_MANA.get()){
            attr = PerkAttributes.MAX_MANA.get();
        }
        if(attr == AttributeRegistry.MANA_REGEN.get()){
            attr = PerkAttributes.MANA_REGEN_BONUS.get();
        }
    }
}
