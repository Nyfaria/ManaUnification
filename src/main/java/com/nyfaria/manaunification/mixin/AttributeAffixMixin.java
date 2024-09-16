package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import dev.shadowsoffire.apotheosis.adventure.affix.AttributeAffix;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(AttributeAffix.class)
public class AttributeAffixMixin {

    @Shadow @Final @Mutable
    protected Attribute attribute;

    @Inject(method = "addModifiers", at=@At("HEAD"), remap = false)
    public void addModifiers(ItemStack stack, LootRarity rarity, float level, EquipmentSlot type, BiConsumer<Attribute, AttributeModifier> map, CallbackInfo ci) {
        if(this.attribute == AttributeRegistry.MAX_MANA.get()){
            this.attribute = PerkAttributes.MAX_MANA.get();
        }
        if(this.attribute == AttributeRegistry.MANA_REGEN.get()){
            this.attribute = PerkAttributes.MANA_REGEN_BONUS.get();
        }
    }
//    @ModifyArg(method = "addModifiers", at= @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"), index = 0, remap = false)
//    public Attribute modifyAttribute(Attribute t){
//        if(attribute == AttributeRegistry.MAX_MANA.get()){
//            return PerkAttributes.MAX_MANA.get();
//        }
//        if(attribute == AttributeRegistry.MANA_REGEN.get()){
//            return PerkAttributes.MANA_REGEN_BONUS.get();
//        }
//        return attribute;
//    }
}
