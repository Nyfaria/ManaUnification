package com.nyfaria.manaunification.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ManaConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue IRONS_SPELLS_MANA_COST_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue IRONS_SPELLS_MAX_MANA_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue IRONS_SPELLS_MANA_REGEN_MULTIPLIER;

    public static final ForgeConfigSpec.DoubleValue ARS_NOUVEAU_MAX_MANA_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue ARS_NOUVEAU_MANA_REGEN_MULTIPLIER;

    public static final ForgeConfigSpec.DoubleValue EB_WIZARDRY_MANA_COST_MULTIPLIER;

    public static final ForgeConfigSpec.IntValue MANA_BAR_X_OFFSET;
    public static final ForgeConfigSpec.IntValue MANA_BAR_Y_OFFSET;
    public static final ForgeConfigSpec.BooleanValue MANA_BAR_MOVE_FOR_ARMOR;
    public static final ForgeConfigSpec.BooleanValue SHOW_MANA_VALUES;

    static {
        BUILDER.push("Iron's Spells Integration");

        IRONS_SPELLS_MANA_COST_MULTIPLIER = BUILDER
                .comment("Multiplier for Iron's Spells mana costs")
                .defineInRange("ironSpellsManaCostMultiplier", 1.5, 0.1, 10.0);

        IRONS_SPELLS_MAX_MANA_MULTIPLIER = BUILDER
                .comment("Multiplier when converting Iron's Spells max mana bonuses")
                .defineInRange("ironSpellsMaxManaMultiplier", 0.6, 0.1, 10.0);

        IRONS_SPELLS_MANA_REGEN_MULTIPLIER = BUILDER
                .comment("Multiplier when converting Iron's Spells mana regen bonuses")
                .defineInRange("ironSpellsManaRegenMultiplier", 0.6, 0.1, 10.0);

        BUILDER.pop();
        BUILDER.push("Ars Nouveau Integration");

        ARS_NOUVEAU_MAX_MANA_MULTIPLIER = BUILDER
                .comment("Multiplier when converting Ars Nouveau max mana bonuses")
                .defineInRange("arsNouveauMaxManaMultiplier", 1.0, 0.1, 10.0);

        ARS_NOUVEAU_MANA_REGEN_MULTIPLIER = BUILDER
                .comment("Multiplier when converting Ars Nouveau mana regen bonuses")
                .defineInRange("arsNouveauManaRegenMultiplier", 1.0, 0.1, 10.0);

        BUILDER.pop();
        BUILDER.push("Electroblobs Wizardry Integration");

        EB_WIZARDRY_MANA_COST_MULTIPLIER = BUILDER
                .comment("Multiplier for Electroblob's Wizardry spell mana costs")
                .defineInRange("ebWizardryManaCostMultiplier", 1.0, 0.1, 10.0);

        BUILDER.pop();
        BUILDER.push("Mana Bar Display");

        MANA_BAR_X_OFFSET = BUILDER
                .comment("Horizontal offset for the mana bar position (from left of screen)")
                .defineInRange("manaBarXOffset", 0, -1000, 1000);

        MANA_BAR_Y_OFFSET = BUILDER
                .comment("Vertical offset for the mana bar position (from above health bar)")
                .defineInRange("manaBarYOffset", 0, -1000, 1000);

        MANA_BAR_MOVE_FOR_ARMOR = BUILDER
                .comment("Whether the mana bar should move up when armor is equipped")
                .define("manaBarMoveForArmor", true);

        SHOW_MANA_VALUES = BUILDER
                .comment("Whether to show current/max mana values as text on the mana bar")
                .define("showManaValues", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
