package com.nyfaria.manaunification;

import net.minecraft.world.entity.ai.attributes.Attribute;

public interface AttributeBonusDuck {
    Attribute getAttribute();
    void setAttribute(Attribute attribute);
    double getAmount();
    void setAmount(double amount);
}
