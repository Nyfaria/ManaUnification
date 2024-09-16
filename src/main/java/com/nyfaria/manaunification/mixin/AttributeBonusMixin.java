package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.AttributeBonusDuck;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.bonus.AttributeBonus;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(AttributeBonus.class)
public class AttributeBonusMixin {

    @Inject(method = "addModifiers", at=@At("HEAD"), remap = false)
    public void addModifiers(ItemStack gem, LootRarity rarity, BiConsumer<Attribute, AttributeModifier> map, CallbackInfo ci) {
        if (this.attribute == AttributeRegistry.MAX_MANA.get()) {
            this.attribute = PerkAttributes.MAX_MANA.get();
        }
        if (this.attribute == AttributeRegistry.MANA_REGEN.get()) {
            this.attribute = PerkAttributes.MANA_REGEN_BONUS.get();
        }
    }

    @Final
    @Shadow(remap = false)
    @Mutable
    protected Attribute attribute;


}
