package com.nyfaria.examplemod.event;

import com.nyfaria.examplemod.ExampleMod;
import com.nyfaria.examplemod.init.ItemInit;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void onCreativeTabAdd(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab()== CreativeModeTabs.SEARCH){
            ItemInit.ITEMS.getEntries().forEach(item->event.accept(item.get().getDefaultInstance()));
        }
    }
}
