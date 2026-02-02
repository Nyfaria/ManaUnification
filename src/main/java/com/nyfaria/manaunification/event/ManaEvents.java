package com.nyfaria.manaunification.event;

import com.nyfaria.manaunification.ManaUnification;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import com.nyfaria.manaunification.integration.ArsNouveauIntegration;
import com.nyfaria.manaunification.integration.IronsSpellbooksIntegration;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaUnification.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaEvents {

    private static final boolean ARS_LOADED = ModList.get().isLoaded("ars_nouveau");
    private static final boolean IRONS_LOADED = ModList.get().isLoaded("irons_spellbooks");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;

        if (ARS_LOADED) {
            ArsNouveauIntegration.syncArsAttributes(event.player);
        }

        if (IRONS_LOADED) {
            IronsSpellbooksIntegration.syncIronsAttributes(event.player);
        }

        ManaHolderAttacher.getHolder(event.player).ifPresent(ManaHolder::tickManaRegen);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ManaHolderAttacher.getHolder(event.getEntity()).ifPresent(ManaHolder::updateTracking);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        ManaHolderAttacher.getHolder(event.getOriginal()).ifPresent(oldHolder -> {
            ManaHolderAttacher.getHolder(event.getEntity()).ifPresent(newHolder -> {
                if (event.isWasDeath()) {
                    newHolder.setCurrentMana(newHolder.getMaxMana());
                } else {
                    newHolder.setCurrentMana(oldHolder.getCurrentMana());
                }
            });
        });
        event.getOriginal().invalidateCaps();
    }
}
