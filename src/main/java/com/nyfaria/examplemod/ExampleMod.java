package com.nyfaria.examplemod;

import com.nyfaria.examplemod.cap.ExampleHolderAttacher;
import com.nyfaria.examplemod.config.ExampleClientConfig;
import com.nyfaria.examplemod.config.ExampleConfig;
import com.nyfaria.examplemod.datagen.*;
import com.nyfaria.examplemod.init.BlockInit;
import com.nyfaria.examplemod.init.EntityInit;
import com.nyfaria.examplemod.init.ItemInit;
import com.nyfaria.examplemod.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExampleMod.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExampleMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExampleConfig.CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ExampleClientConfig.CLIENT_SPEC);

        ItemInit.ITEMS.register(modBus);
        EntityInit.ENTITIES.register(modBus);
        BlockInit.BLOCKS.register(modBus);
        BlockInit.BLOCK_ENTITIES.register(modBus);

        ExampleHolderAttacher.register();
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();
        generator.addProvider(includeServer, new ModRecipeProvider(packOutput));
        generator.addProvider(includeServer, new ModLootTableProvider(packOutput));
        generator.addProvider(includeServer, new ModSoundProvider(packOutput, existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Blocks(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Items(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModLangProvider(packOutput));
    }
}
