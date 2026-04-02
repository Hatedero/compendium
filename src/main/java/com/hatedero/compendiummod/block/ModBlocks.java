package com.hatedero.compendiummod.block;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.block.custom.AbyssPortalBlock;
import com.hatedero.compendiummod.item.ModItems;
import com.hatedero.compendiummod.item.custom.SpearItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CompendiumMod.MODID);

    public static final DeferredBlock<Block> ABYSS_PORTAL = registerBlock("abyss_portal",
            () -> new AbyssPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBWEB).sound(SoundType.SCULK).noLootTable()));

    public static final DeferredBlock<Block> OSTEANIAN_GRATED_BLOCK = registerBlock("osteanian_grated_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).sound(SoundType.SCULK)));

    public static final DeferredBlock<Block> OSTEANIAN_BLOCK = registerBlock("osteanian_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).sound(SoundType.SCULK)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}