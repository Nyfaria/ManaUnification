package com.nyfaria.manaunification.mixin;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpellBook.class)
public class ISSpellBookMixin implements IDisplayMana {
    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return true;
    }
}
