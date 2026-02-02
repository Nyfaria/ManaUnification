package com.nyfaria.manaunification.mixin;

import com.nyfaria.manaunification.AttributeBonusDuck;
import com.nyfaria.manaunification.cap.ManaAttributes;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.Gem;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
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
                ResourceLocation attrId = ForgeRegistries.ATTRIBUTES.getKey(abonus.getAttribute());
                if (attrId != null) {
                    String path = attrId.getPath();
                    if (path.contains("max_mana") || path.contains("flat_mana") || path.contains("perk.max_mana")) {
                        abonus.setAttribute(ManaAttributes.MAX_MANA.get());
                    }
                    if (path.contains("mana_regen") || path.contains("perk.mana_regen")) {
                        abonus.setAttribute(ManaAttributes.MANA_REGEN.get());
                    }
                }
            }
        }
    }
}
