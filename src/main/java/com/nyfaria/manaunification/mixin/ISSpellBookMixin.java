package com.nyfaria.manaunification.mixin;

import com.nyfaria.manaunification.api.IDisplayManaItem;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpellBook.class)
public class ISSpellBookMixin implements IDisplayManaItem {
    @Override
    public boolean shouldDisplayMana(ItemStack stack) {
        return true;
    }
}
