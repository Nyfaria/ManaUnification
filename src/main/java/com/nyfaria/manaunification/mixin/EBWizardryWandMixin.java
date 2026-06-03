package com.nyfaria.manaunification.mixin;

import com.binaris.wizardry.api.content.item.*;
import com.binaris.wizardry.api.content.spell.Spell;
import com.binaris.wizardry.api.content.spell.internal.PlayerCastContext;
import com.binaris.wizardry.api.content.spell.internal.SpellModifiers;
import com.binaris.wizardry.api.content.util.*;
import com.binaris.wizardry.content.item.WandItem;
import com.binaris.wizardry.setup.registries.EBItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.manaunification.api.IDisplayManaItem;
import com.nyfaria.manaunification.cap.ManaHolder;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import com.nyfaria.manaunification.config.ManaConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WandItem.class)
public abstract class EBWizardryWandMixin implements IDisplayManaItem {

    @Override
    public boolean shouldDisplayMana(ItemStack stack) {
        return true;
    }

    @WrapMethod(method = "canCastRequirements", remap = false)
    private boolean canCastRequirements(ItemStack stack, Spell spell, PlayerCastContext ctx, Operation<Boolean> original) {
        Player player = ctx.caster();
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(player);
        if (holder == null) {
            return original.call(stack, spell, ctx);
        }

        int baseCost = (int) (spell.getCost() * ctx.modifiers().get(SpellModifiers.COST) + 0.1f);
        int cost = (int) (baseCost * ManaConfig.EB_WIZARDRY_MANA_COST_MULTIPLIER.get());

        boolean tierCheck = spell.getTier().getLevel() <= ((WandItem)(Object)this).getTier(stack).getLevel();
        boolean cooldownCheck = CastItemDataHelper.getCurrentCooldown(stack, ctx.world().getGameTime()) == 0 || player.isCreative();

        return holder.getCurrentMana() >= cost && tierCheck && cooldownCheck;
    }

    @WrapMethod(method = "consumeManaAndSetCooldown", remap = false)
    private void consumeManaAndSetCooldown(ItemStack stack, Spell spell, Player player, SpellModifiers modifiers, Operation<Void> original) {
        ManaHolder holder = ManaHolderAttacher.getHolderUnwrap(player);
        if (holder != null && !player.isCreative()) {
            int baseCost = (int) (spell.getCost() * modifiers.get(SpellModifiers.COST) + 0.1f);
            int cost = (int) (baseCost * ManaConfig.EB_WIZARDRY_MANA_COST_MULTIPLIER.get());
            holder.consumeMana(cost);

            CastItemDataHelper.setCurrentCooldown(stack, (int) (spell.getCooldown() * modifiers.get(SpellModifiers.COOLDOWN)), player.level().getGameTime());
        } else {
            original.call(stack, spell, player, modifiers);
        }
    }
}
