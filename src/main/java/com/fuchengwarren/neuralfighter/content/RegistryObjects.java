package com.fuchengwarren.neuralfighter.content;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RegistryObjects {
    public static Block NEURAL_DISPLAY_BLOCK;

    public static void register() {
        Identifier blockId = id("neural_display");
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, blockId);
        if (blockKey == null) {
            throw new IllegalStateException("Failed to create block registry key for " + blockId);
        }
        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .registryKey(blockKey)
                .mapColor(MapColor.IRON_GRAY)
                .strength(2.0f)
                .nonOpaque();
        NEURAL_DISPLAY_BLOCK = Registry.register(Registries.BLOCK, blockId,
                new NeuralDisplayBlock(settings));
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockId);
        Item.Settings itemSettings = new Item.Settings().registryKey(itemKey);
        Registry.register(Registries.ITEM, blockId,
                new BlockItem(NEURAL_DISPLAY_BLOCK, itemSettings));
    }

    public static Identifier id(String path) {
        return Identifier.of(NeuralFighterMod.MOD_ID, path);
    }
}
