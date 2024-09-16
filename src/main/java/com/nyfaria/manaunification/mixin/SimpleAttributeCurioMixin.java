package com.nyfaria.manaunification.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.curios.SimpleAttributeCurio;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Iterator;
import java.util.UUID;

@Mixin(SimpleAttributeCurio.class)
public class SimpleAttributeCurioMixin {
    @Shadow(remap = false) @Final private AttributeModifier attributeModifier;

    @Shadow(remap = false) private Multimap<Attribute, AttributeModifier> attributeMap;

    @WrapMethod(method="getAttributeModifiers", remap = false)
    private Multimap<Attribute, AttributeModifier> onGetAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, Operation<Multimap<Attribute, AttributeModifier>> original) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();

        for (Attribute attribute : this.attributeMap.keySet()) {
            double amount = attributeModifier.getAmount();
            if (attribute == AttributeRegistry.MANA_REGEN.get()) {
                attribute = PerkAttributes.MANA_REGEN_BONUS.get();
            }
            if (attribute == AttributeRegistry.MAX_MANA.get()) {
                attribute = PerkAttributes.MAX_MANA.get();
                amount *= 0.6;
            }
            attributeBuilder.put(attribute, new AttributeModifier(uuid, this.attributeModifier.getName(), amount, this.attributeModifier.getOperation()));
        }
        return attributeBuilder.build();
    }
}
