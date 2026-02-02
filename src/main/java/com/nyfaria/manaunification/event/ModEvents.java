package com.nyfaria.manaunification.event;

import com.nyfaria.manaunification.ManaUnification;
import com.nyfaria.manaunification.cap.ManaAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaUnification.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ManaAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, ManaAttributes.MANA_REGEN.get());
    }
}
