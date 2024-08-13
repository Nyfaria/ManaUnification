package com.nyfaria.examplemod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ExampleConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ExampleConfig INSTANCE;

    static {
        Pair<ExampleConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ExampleConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public final ForgeConfigSpec.IntValue example;

    private ExampleConfig(ForgeConfigSpec.Builder builder) {
        this.example = builder.defineInRange("example", 76, -1000, 1000);
    }
}