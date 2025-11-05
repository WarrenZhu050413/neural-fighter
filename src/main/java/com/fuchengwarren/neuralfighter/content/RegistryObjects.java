package com.fuchengwarren.neuralfighter.content;

import com.fuchengwarren.neuralfighter.NeuralFighterMod;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RegistryObjects {
    public static Block NEURAL_NODE_BLOCK;
    public static Block NEURAL_CONNECTION_BLOCK;
    public static BlockEntityType<NeuralNodeBlockEntity> NEURAL_NODE_BE;
    public static BlockEntityType<NeuralConnectionBlockEntity> NEURAL_CONNECTION_BE;

    public static void register() {
        registerNodeBlock();
        registerConnectionBlock();
    }

    public static Identifier id(String path) {
        return Identifier.of(NeuralFighterMod.MOD_ID, path);
    }

    private static void registerNodeBlock() {
        Identifier nodeId = id("neural_node");
        AbstractBlock.Settings nodeSettings = AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, nodeId))
                .mapColor(MapColor.EMERALD_GREEN)
                .strength(1.5f)
                .luminance(state -> 6)
                .nonOpaque();
        NEURAL_NODE_BLOCK = Registry.register(Registries.BLOCK, nodeId, new NeuralNodeBlock(nodeSettings));

        Item.Settings nodeItemSettings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, nodeId));
        Registry.register(Registries.ITEM, nodeId, new BlockItem(NEURAL_NODE_BLOCK, nodeItemSettings));

        NEURAL_NODE_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, nodeId,
                FabricBlockEntityTypeBuilder.create(NeuralNodeBlockEntity::new, NEURAL_NODE_BLOCK).build());
    }

    private static void registerConnectionBlock() {
        Identifier connectionId = id("neural_connection");
        AbstractBlock.Settings connectionSettings = AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, connectionId))
                .mapColor(MapColor.LIGHT_BLUE)
                .strength(1.0f)
                .nonOpaque();
        NEURAL_CONNECTION_BLOCK = Registry.register(Registries.BLOCK, connectionId,
                new NeuralConnectionBlock(connectionSettings));

        Item.Settings connectionItemSettings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, connectionId));
        Registry.register(Registries.ITEM, connectionId, new BlockItem(NEURAL_CONNECTION_BLOCK, connectionItemSettings));

        NEURAL_CONNECTION_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, connectionId,
                FabricBlockEntityTypeBuilder.create(NeuralConnectionBlockEntity::new, NEURAL_CONNECTION_BLOCK).build());
    }
}
