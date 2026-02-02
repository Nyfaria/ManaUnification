package com.nyfaria.manaunification.cap;

import com.nyfaria.manaunification.ManaUnification;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ManaAttributes {

    public static final double DEFAULT_MAX_MANA = 100.0;
    public static final double DEFAULT_MANA_REGEN = 5.0;

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ManaUnification.MODID);

    public static final RegistryObject<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana",
            () -> new RangedAttribute("attribute.manaunification.max_mana", DEFAULT_MAX_MANA, 0.0, 100000.0).setSyncable(true));

    public static final RegistryObject<Attribute> MANA_REGEN = ATTRIBUTES.register("mana_regen",
            () -> new RangedAttribute("attribute.manaunification.mana_regen", DEFAULT_MANA_REGEN, 0.0, 10000.0).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
