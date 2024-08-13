package com.nyfaria.examplemod.init;

import com.nyfaria.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("mod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.hundredMediaTab"))
            .displayItems((pParameters, pOutput) -> ItemInit.ITEMS.getEntries().forEach(
                    (item) -> pOutput.accept(new ItemStack(item.get()))
            ))
            .icon(() -> new ItemStack(BlockInit.HUNDRED_MEDIA.get()))
            .build());


}
