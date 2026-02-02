package com.nyfaria.manaunification.mixin;

import com.nyfaria.manaunification.cap.ManaAttributes;
import dev.shadowsoffire.apotheosis.adventure.affix.AttributeAffix;
import dev.shadowsoffire.placebo.util.StepFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;
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
        ResourceLocation attrId = ForgeRegistries.ATTRIBUTES.getKey(attr);
        if (attrId != null) {
            String path = attrId.getPath();
            if (path.contains("max_mana") || path.contains("flat_mana") || path.contains("perk.max_mana")) {
                attr = ManaAttributes.MAX_MANA.get();
            }
            if (path.contains("mana_regen") || path.contains("perk.mana_regen")) {
                attr = ManaAttributes.MANA_REGEN.get();
            }
        }
    }
}
