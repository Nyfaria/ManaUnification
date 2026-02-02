package com.nyfaria.manaunification.api;

import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class ManaAPI {

    public static Optional<IManaHolder> getManaHolder(Entity entity) {
        return ManaHolderAttacher.getHolder(entity).map(h -> h);
    }

    public static double getCurrentMana(Player player) {
        return getManaHolder(player).map(IManaHolder::getCurrentMana).orElse(0.0);
    }

    public static double getMaxMana(Player player) {
        return getManaHolder(player).map(IManaHolder::getMaxMana).orElse(0.0);
    }

    public static double getManaRegen(Player player) {
        return getManaHolder(player).map(IManaHolder::getManaRegen).orElse(0.0);
    }

    public static void setCurrentMana(Player player, double mana) {
        getManaHolder(player).ifPresent(h -> h.setCurrentMana(mana));
    }

    public static void addMana(Player player, double amount) {
        getManaHolder(player).ifPresent(h -> h.addMana(amount));
    }

    public static boolean consumeMana(Player player, double amount) {
        return getManaHolder(player).map(h -> h.consumeMana(amount)).orElse(false);
    }

    public static double getManaPercentage(Player player) {
        return getManaHolder(player).map(IManaHolder::getManaPercentage).orElse(0.0);
    }
}
