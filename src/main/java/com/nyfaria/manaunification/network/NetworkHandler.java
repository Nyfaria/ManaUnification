package com.nyfaria.manaunification.network;

import com.google.common.collect.*;
import com.nyfaria.manaunification.*;
import com.nyfaria.manaunification.cap.*;
import dev._100media.capabilitysyncer.network.*;
import net.minecraft.resources.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;

import java.util.*;
import java.util.function.*;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ManaUnification.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int nextId = 0;

    public static void register() {
        List<BiConsumer<SimpleChannel, Integer>> packets = ImmutableList.<BiConsumer<SimpleChannel, Integer>>builder()
                .add(SimpleEntityCapabilityStatusPacket::register)
                .build();

        SimpleEntityCapabilityStatusPacket.registerRetriever(ManaHolderAttacher.LOCATION, ManaHolderAttacher::getHolderUnwrap);

        packets.forEach(consumer -> consumer.accept(INSTANCE, getNextId()));
    }

    private static int getNextId() {
        return nextId++;
    }
}