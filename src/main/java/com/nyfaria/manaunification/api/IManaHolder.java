package com.nyfaria.manaunification.api;

public interface IManaHolder {

    double getCurrentMana();

    void setCurrentMana(double mana);

    double getMaxMana();

    double getManaRegen();


    default void addMana(double amount) {
        setCurrentMana(Math.min(getCurrentMana() + amount, getMaxMana()));
    }

    default boolean consumeMana(double amount) {
        if (getCurrentMana() >= amount) {
            setCurrentMana(getCurrentMana() - amount);
            return true;
        }
        return false;
    }

    default double getManaPercentage() {
        double max = getMaxMana();
        return max > 0 ? getCurrentMana() / max : 0;
    }

    default void regenMana() {
        addMana(getManaRegen());
    }
}
