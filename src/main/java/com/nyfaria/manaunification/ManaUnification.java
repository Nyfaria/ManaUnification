package com.nyfaria.manaunification;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ManaUnification.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManaUnification {
    public static final String MODID = "manaunification";
    public static final Logger LOGGER = LogManager.getLogger();

    public ManaUnification() {

    }


}
