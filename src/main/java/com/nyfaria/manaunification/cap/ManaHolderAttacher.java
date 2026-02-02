package com.nyfaria.manaunification.cap;

import com.nyfaria.manaunification.ManaUnification;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaUnification.MODID)
public class ManaHolderAttacher extends CapabilityAttacher {
    public static final Capability<ManaHolder> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation LOCATION = new ResourceLocation(ManaUnification.MODID, "mana_holder");
    private static final Class<ManaHolder> CAPABILITY_CLASS = ManaHolder.class;

    @SuppressWarnings("ConstantConditions")
    public static ManaHolder getHolderUnwrap(Entity entity) {
        return getHolder(entity).orElse(null);
    }

    public static LazyOptional<ManaHolder> getHolder(Entity entity) {
        return entity.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Entity entity) {
        genericAttachCapability(event, new ManaHolder(entity), CAPABILITY, LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(Player.class, ManaHolderAttacher::attach, ManaHolderAttacher::getHolder);
    }
}