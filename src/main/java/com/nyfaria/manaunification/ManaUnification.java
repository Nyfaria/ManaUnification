package com.nyfaria.manaunification;

import com.nyfaria.manaunification.cap.ManaAttributes;
import com.nyfaria.manaunification.cap.ManaHolderAttacher;
import com.nyfaria.manaunification.config.ManaConfig;
import com.nyfaria.manaunification.network.NetworkHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ManaUnification.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManaUnification {
    public static final String MODID = "manaunification";
    public static final Logger LOGGER = LogManager.getLogger();

    public ManaUnification() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ManaAttributes.register(modEventBus);
        ManaHolderAttacher.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ManaConfig.SPEC);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

}
