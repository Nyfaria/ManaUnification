package com.nyfaria.manaunification.event;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.nyfaria.manaunification.ManaUnification;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
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
                event.addModifier(PerkAttributes.MAX_MANA.get(), attributeModifier);
            });
            event.removeAttribute(AttributeRegistry.MAX_MANA.get());
        }
        if (event.getModifiers().containsKey(AttributeRegistry.MANA_REGEN.get())) {
            List<AttributeModifier> modifier = event.getModifiers().get(AttributeRegistry.MANA_REGEN.get()).stream().toList();
            modifier.forEach(attributeModifier -> {
                event.addModifier(PerkAttributes.MANA_REGEN_BONUS.get(), attributeModifier);
            });
            event.removeAttribute(AttributeRegistry.MANA_REGEN.get());
        }
    }



    @SubscribeEvent
    public static void onIronSpellsMana(ChangeManaEvent event) {
        event.setCanceled(true);
        event.getEntity().getCapability(CapabilityRegistry.MANA_CAPABILITY).ifPresent(mana -> {
            mana.removeMana(event.getOldMana() - event.getNewMana());
        });
    }
}