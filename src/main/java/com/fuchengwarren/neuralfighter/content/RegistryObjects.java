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
import net.minecraft.util.Identifier;

public class RegistryObjects {
    public static final Block NEURAL_DISPLAY_BLOCK = new NeuralDisplayBlock(
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(2.0f).nonOpaque());

    public static final BlockEntityType<NeuralDisplayBlockEntity> NEURAL_DISPLAY_BE =
            FabricBlockEntityTypeBuilder.create(NeuralDisplayBlockEntity::new, NEURAL_DISPLAY_BLOCK).build();

    public static void register() {
        Registry.register(Registries.BLOCK, id("neural_display"), NEURAL_DISPLAY_BLOCK);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, id("neural_display"), NEURAL_DISPLAY_BE);
        Registry.register(Registries.ITEM, id("neural_display"), new BlockItem(NEURAL_DISPLAY_BLOCK, new Item.Settings()));
    }

    public static Identifier id(String path) {
        return Identifier.of(NeuralFighterMod.MOD_ID, path);
    }
}
