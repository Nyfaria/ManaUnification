package com.nyfaria.manaunification.mixin;

import com.nyfaria.manaunification.cap.ManaAttributes;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.bonus.AttributeBonus;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
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

    @Final
    @Shadow(remap = false)
    @Mutable
    protected Attribute attribute;

    @Inject(method = "addModifiers", at=@At("HEAD"), remap = false)
    public void addModifiers(ItemStack gem, LootRarity rarity, BiConsumer<Attribute, AttributeModifier> map, CallbackInfo ci) {
        ResourceLocation attrId = ForgeRegistries.ATTRIBUTES.getKey(this.attribute);
        if (attrId != null) {
            String path = attrId.getPath();
            if (path.contains("max_mana") || path.contains("flat_mana") || path.contains("perk.max_mana")) {
                this.attribute = ManaAttributes.MAX_MANA.get();
            }
            if (path.contains("mana_regen") || path.contains("perk.mana_regen")) {
                this.attribute = ManaAttributes.MANA_REGEN.get();
            }
        }
    }
}
