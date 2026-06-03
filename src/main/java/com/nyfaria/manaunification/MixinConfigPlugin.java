package com.nyfaria.manaunification;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private static final Supplier<Boolean> TRUE = () -> true;
    private static final Supplier<Boolean> IRONS_SPELLS = () -> LoadingModList.get().getModFileById("irons_spellbooks") != null;
    private static final Supplier<Boolean> ARS_NOUVEAU = () -> LoadingModList.get().getModFileById("ars_nouveau") != null;
    private static final Supplier<Boolean> APOTHEOSIS = () -> LoadingModList.get().getModFileById("apotheosis") != null;
    private static final Supplier<Boolean> EB_WIZARDRY = () -> LoadingModList.get().getModFileById("ebwizardry") != null;
    private static final Supplier<Boolean> PUT_MANA_IN = () -> LoadingModList.get().getModFileById("put_mana_in") != null;

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.<String, Supplier<Boolean>>builder()
            .put("com.nyfaria.manaunification.mixin.AbstractSpellMixin", IRONS_SPELLS)
            .put("com.nyfaria.manaunification.mixin.ISSpellBookMixin", IRONS_SPELLS)
            .put("com.nyfaria.manaunification.mixin.MagicDataMixin", IRONS_SPELLS)
            .put("com.nyfaria.manaunification.mixin.MagicManagerMixin", IRONS_SPELLS)
            .put("com.nyfaria.manaunification.mixin.ManaBarOverlayMixin", IRONS_SPELLS)
            .put("com.nyfaria.manaunification.mixin.AttributeAffixMixin", APOTHEOSIS)
            .put("com.nyfaria.manaunification.mixin.AttributeBonusMixin", APOTHEOSIS)
            .put("com.nyfaria.manaunification.mixin.GemMixin", APOTHEOSIS)
            .put("com.nyfaria.manaunification.mixin.ModifierInstMixin", APOTHEOSIS)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaCapAccessor", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaCapMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaRegenMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaHudMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaUtilMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauSpellResolverMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.ArsNouveauManaCapEventsMixin", ARS_NOUVEAU)
            .put("com.nyfaria.manaunification.mixin.EBWizardryWandMixin", EB_WIZARDRY)
            .put("com.nyfaria.manaunification.mixin.PutManaInMixin", PUT_MANA_IN)
            .build();


    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean shouldApply = CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
        return shouldApply;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}