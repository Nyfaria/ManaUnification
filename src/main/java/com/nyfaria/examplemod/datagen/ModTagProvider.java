package com.nyfaria.examplemod.datagen;

import com.nyfaria.examplemod.ExampleMod;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModTagProvider {

    public static class Items extends TagsProvider<Item>{

        public Items(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, Registry.ITEM, ExampleMod.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {

        }

        public void populateTag(TagKey<Item> tag, Supplier<Item>... items){
            for (Supplier<Item> item : items) {
                tag(tag).add(item.get());
            }
        }
    }

    public static class Blocks extends TagsProvider<Block>{

        public Blocks(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, Registry.BLOCK, ExampleMod.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {

        }
        public void populateTag(TagKey<Block> tag, Supplier<Block>... items){
            for (Supplier<Block> item : items) {
                tag(tag).add(item.get());
            }
        }
    }
}
