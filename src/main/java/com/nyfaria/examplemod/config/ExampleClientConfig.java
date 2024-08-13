package com.nyfaria.examplemod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ExampleClientConfig {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ExampleClientConfig CLIENT;

    static {
        Pair<ExampleClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ExampleClientConfig::new);
        CLIENT_SPEC = pair.getRight();
        CLIENT = pair.getLeft();
    }

    public final ForgeConfigSpec.BooleanValue example;

    public ExampleClientConfig(ForgeConfigSpec.Builder builder) {
        this.example = builder.define("example", true);
    }
}

