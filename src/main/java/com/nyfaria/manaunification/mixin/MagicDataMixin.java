package com.nyfaria.manaunification.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.MagicDataPlayer;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MagicData.class)
public class MagicDataMixin implements MagicDataPlayer {

    @Shadow private ServerPlayer serverPlayer;

    @Override
    public ServerPlayer getPlayer() {
        return serverPlayer;
    }

    @WrapMethod(method = "getMana",remap = false)
    public float getMana(Operation<Float> original) {
        if(getPlayer() == null) {
            return original.call();
        }
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(getPlayer());
        if (holder != null) {
            return (float)holder.getCurrentMana();
        }
        return original.call();
    }
    @WrapMethod(method = "setMana",remap = false)
    public void setMana(float mana, Operation<Void> original) {
        if(getPlayer() == null) {
            original.call(mana);
            return;
        }
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(getPlayer());
        if (holder != null) {
            holder.setCurrentMana(mana);
        }
        original.call(mana);
    }
}
