package com.nyfaria.manaunification.api;

import net.minecraft.world.item.ItemStack;

public interface IDisplayManaItem {
    boolean shouldDisplayMana(ItemStack stack);
}
