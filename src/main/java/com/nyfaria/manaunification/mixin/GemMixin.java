package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.nyfaria.manaunification.AttributeBonusDuck;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.Gem;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.bonus.AttributeBonus;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mixin(Gem.class)
public class GemMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void onInit(int weight, float quality, Set<ResourceLocation> dimensions, Optional<LootRarity> minRarity, Optional<LootRarity> maxRarity, List<GemBonus> bonuses, boolean unique, Optional<Set<String>> stages, CallbackInfo ci) {
        for(GemBonus bonus : bonuses) {
            if(bonus instanceof AttributeBonusDuck abonus){
                if(abonus.getAttribute() == AttributeRegistry.MAX_MANA.get()){
                    abonus.setAttribute(PerkAttributes.MAX_MANA.get());

                }
                if(abonus.getAttribute() == AttributeRegistry.MANA_REGEN.get()){
                    abonus.setAttribute(PerkAttributes.MANA_REGEN_BONUS.get());
                }
            }
        }
    }
}
