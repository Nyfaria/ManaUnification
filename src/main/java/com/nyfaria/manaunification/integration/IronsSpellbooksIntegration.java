package com.nyfaria.manaunification.integration;

import com.nyfaria.manaunification.cap.ManaAttributes;
import com.nyfaria.manaunification.config.ManaConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class IronsSpellbooksIntegration {

    private static final UUID IRONS_MAX_MANA_MODIFIER = UUID.fromString("c3d4e5f6-a7b8-9012-cdef-123456789012");
    private static final UUID IRONS_MANA_REGEN_MODIFIER = UUID.fromString("d4e5f6a7-b8c9-0123-defa-234567890123");

    private static Attribute ironsMaxMana;
    private static Attribute ironsManaRegen;
    private static boolean initialized = false;

    public static void syncIronsAttributes(Player player) {
        if (!initialized) {
            ironsMaxMana = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"));
            ironsManaRegen = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "mana_regen"));
            initialized = true;
        }

        if (ironsMaxMana != null) {
            AttributeInstance ironsMaxManaInst = player.getAttribute(ironsMaxMana);
            AttributeInstance unifiedMaxMana = player.getAttribute(ManaAttributes.MAX_MANA.get());

            if (ironsMaxManaInst != null && unifiedMaxMana != null) {
                double ironsBonus = ironsMaxManaInst.getValue() * ManaConfig.IRONS_SPELLS_MAX_MANA_MULTIPLIER.get();
                updateModifier(unifiedMaxMana, IRONS_MAX_MANA_MODIFIER, "Iron's Spellbooks Max Mana", ironsBonus);
            }
        }

        if (ironsManaRegen != null) {
            AttributeInstance ironsManaRegenInst = player.getAttribute(ironsManaRegen);
            AttributeInstance unifiedManaRegen = player.getAttribute(ManaAttributes.MANA_REGEN.get());

            if (ironsManaRegenInst != null && unifiedManaRegen != null) {
                double ironsBonus = ironsManaRegenInst.getValue() * ManaConfig.IRONS_SPELLS_MANA_REGEN_MULTIPLIER.get();
                updateModifier(unifiedManaRegen, IRONS_MANA_REGEN_MODIFIER, "Iron's Spellbooks Mana Regen", ironsBonus);
            }
        }
    }

    private static void updateModifier(AttributeInstance instance, UUID uuid, String name, double value) {
        AttributeModifier existing = instance.getModifier(uuid);
        if (existing != null) {
            if (existing.getAmount() == value) {
                return;
            }
            instance.removeModifier(uuid);
        }
        if (value != 0) {
            instance.addTransientModifier(new AttributeModifier(uuid, name, value, AttributeModifier.Operation.ADDITION));
        }
    }
}
