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

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "com.nyfaria.manaunification.mixin.AttributeAffixMixin", () ->LoadingModList.get().getModFileById("irons_spellbooks") !=null && LoadingModList.get().getModFileById("apotheosis") != null,
            "com.nyfaria.manaunification.mixin.GemMixin", () ->LoadingModList.get().getModFileById("irons_spellbooks") !=null && LoadingModList.get().getModFileById("apotheosis") != null,
            "com.nyfaria.manaunification.mixin.ModifierInstMixin", () ->LoadingModList.get().getModFileById("irons_spellbooks") !=null && LoadingModList.get().getModFileById("apotheosis") != null

    );


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