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

public class ArsNouveauIntegration {

    private static final UUID ARS_MAX_MANA_MODIFIER = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final UUID ARS_MANA_REGEN_MODIFIER = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f12345678901");

    private static Attribute arsMaxMana;
    private static Attribute arsManaRegen;
    private static boolean initialized = false;

    public static void syncArsAttributes(Player player) {
        if (!initialized) {
            arsMaxMana = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("ars_nouveau", "ars_nouveau.perk.max_mana"));
            arsManaRegen = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("ars_nouveau", "ars_nouveau.perk.mana_regen"));
            initialized = true;
        }

        if (arsMaxMana != null) {
            AttributeInstance arsMaxManaInst = player.getAttribute(arsMaxMana);
            AttributeInstance unifiedMaxMana = player.getAttribute(ManaAttributes.MAX_MANA.get());

            if (arsMaxManaInst != null && unifiedMaxMana != null) {
                double arsBonus = arsMaxManaInst.getValue() * ManaConfig.ARS_NOUVEAU_MAX_MANA_MULTIPLIER.get();
                updateModifier(unifiedMaxMana, ARS_MAX_MANA_MODIFIER, "Ars Nouveau Max Mana", arsBonus);
            }
        }

        if (arsManaRegen != null) {
            AttributeInstance arsManaRegenInst = player.getAttribute(arsManaRegen);
            AttributeInstance unifiedManaRegen = player.getAttribute(ManaAttributes.MANA_REGEN.get());

            if (arsManaRegenInst != null && unifiedManaRegen != null) {
                double arsBonus = arsManaRegenInst.getValue() * ManaConfig.ARS_NOUVEAU_MANA_REGEN_MULTIPLIER.get();
                updateModifier(unifiedManaRegen, ARS_MANA_REGEN_MODIFIER, "Ars Nouveau Mana Regen", arsBonus);
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
