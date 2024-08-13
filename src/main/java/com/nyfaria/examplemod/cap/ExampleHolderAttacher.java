package com.nyfaria.examplemod.cap;

import com.nyfaria.examplemod.ExampleMod;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ExampleHolderAttacher extends CapabilityAttacher {
    public static final Capability<ExampleHolder> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation LOCATION = new ResourceLocation(ExampleMod.MODID, "example");
    private static final Class<ExampleHolder> CAPABILITY_CLASS = ExampleHolder.class;

    @SuppressWarnings("ConstantConditions")
    public static ExampleHolder getHolderUnwrap(Entity entity) {
        return getHolder(entity).orElse(null);
    }

    public static LazyOptional<ExampleHolder> getHolder(Entity entity) {
        return entity.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Entity entity) {
        genericAttachCapability(event, new ExampleHolder(entity), CAPABILITY, LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(Entity.class, ExampleHolderAttacher::attach, ExampleHolderAttacher::getHolder);
    }
}
