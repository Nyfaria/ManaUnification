package com.nyfaria.manaunification.mixin;

import com.nyfaria.manaunification.MagicDataPlayer;
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
}
