package com.nyfaria.examplemod.datagen;

import com.nyfaria.examplemod.init.BlockInit;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.getBlockStream().filter(this::shouldDropSelf).forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.getBlockStream().filter(this::shouldGenerateLoot).toList();
    }

    protected Stream<Block> getBlockStream() {
        return BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get);
    }

    protected boolean shouldDropSelf(Block block) {
        return shouldGenerateLoot(block);
    }

    protected boolean shouldGenerateLoot(Block block) {
        return block.asItem() != Items.AIR && !(block instanceof DropExperienceBlock);
    }

    protected LootTable.Builder createOreStoneDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder state, LootItemCondition.Builder state2, LootItemCondition.Builder state3) {
        return this.applyExplosionDecay(pCropBlock,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(1).when(state))
                                .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(1).when(state2))
                                .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(1).when(state3))
                                .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1).when(state))
                                .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1).when(state2))
                                .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1).when(state3))
                        ));
    }
}
