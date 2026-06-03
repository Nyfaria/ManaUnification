package com.nyfaria.manaunification.compat;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.nyfaria.manaunification.mixin.ArsNouveauManaCapAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArsNouveauCompat {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ArsNouveauCompat());
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            bindLivingEntity(player);
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        bindLivingEntity(event.getEntity());
    }

    @SubscribeEvent
    public void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        bindLivingEntity(event.getEntity());
    }

    @SubscribeEvent
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        bindLivingEntity(event.getEntity());
    }

    private static void bindLivingEntity(Player player) {
        IManaCap cap = CapabilityRegistry.getMana(player).orElse(null);
        if (cap instanceof ArsNouveauManaCapAccessor accessor && accessor.manaunification$getLivingEntity() != player) {
            accessor.manaunification$setLivingEntity(player);
        }
    }
}
