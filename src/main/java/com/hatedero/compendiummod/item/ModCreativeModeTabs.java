package com.hatedero.compendiummod.item;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CompendiumMod.MODID);

    public static final Supplier<CreativeModeTab> COMPENDIUM_TAB = CREATIVE_MODE_TAB.register("compendium_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DAWNBREAKER_HILT.get()))
                    .title(Component.translatable("creativetab.compendiummod.compendium_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.OLD_SPEAR_HEAD.get());
                        output.accept(ModItems.OLD_SPEAR_SHAFT.get());
                        output.accept(ModItems.OLD_SPEAR_SCARF.get());
                        output.accept(ModItems.OLD_IRON_SPEAR.get());
                        output.accept(ModItems.DAWNBREAKER_TIP.get());
                        output.accept(ModItems.OLD_DRAGON_SLAYER_SPEAR.get());
                        output.accept(ModItems.DAWNBREAKER.get());
                        output.accept(ModItems.DAWNBREAKER_HILT.get());
                        output.accept(ModItems.DAWNBREAKER_BLADE.get());
                        output.accept(ModItems.DAWNBREAKER_TIP.get());
                        output.accept(ModItems.STARFIRE_PROTOCOL.get());
                        output.accept(ModItems.CONSUME_SPELL.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}