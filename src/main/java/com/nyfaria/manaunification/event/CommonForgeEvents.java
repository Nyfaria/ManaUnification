package com.nyfaria.manaunification.event;

import com.nyfaria.manaunification.ManaUnification;
import com.nyfaria.manaunification.cap.ManaAttributes;
import com.nyfaria.manaunification.config.ManaConfig;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ManaUnification.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onAttributeModify(ItemAttributeModifierEvent event) {
        if (event.getModifiers().containsKey(AttributeRegistry.MAX_MANA.get())) {
            List<AttributeModifier> modifier = event.getModifiers().get(AttributeRegistry.MAX_MANA.get()).stream().toList();
            modifier.forEach(attributeModifier -> {
                event.addModifier(ManaAttributes.MAX_MANA.get(), new AttributeModifier(
                        attributeModifier.getId(),
                        attributeModifier.getName(),
                        attributeModifier.getAmount() * ManaConfig.IRONS_SPELLS_MAX_MANA_MULTIPLIER.get(),
                        attributeModifier.getOperation()));
            });
            event.removeAttribute(AttributeRegistry.MAX_MANA.get());
        }
        if (event.getModifiers().containsKey(AttributeRegistry.MANA_REGEN.get())) {
            List<AttributeModifier> modifier = event.getModifiers().get(AttributeRegistry.MANA_REGEN.get()).stream().toList();
            modifier.forEach(attributeModifier -> {
                event.addModifier(ManaAttributes.MANA_REGEN.get(), new AttributeModifier(
                        attributeModifier.getId(),
                        attributeModifier.getName(),
                        attributeModifier.getAmount() * ManaConfig.IRONS_SPELLS_MANA_REGEN_MULTIPLIER.get(),
                        attributeModifier.getOperation()));
            });
            event.removeAttribute(AttributeRegistry.MANA_REGEN.get());
        }
    }
}