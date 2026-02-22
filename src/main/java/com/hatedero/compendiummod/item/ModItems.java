package com.hatedero.compendiummod.item;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.item.custom.BlockingSwordItem;
import com.hatedero.compendiummod.item.custom.FuelItem;
import com.hatedero.compendiummod.item.custom.SpearItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TridentItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CompendiumMod.MODID);

    public static final DeferredItem<Item> CORE = ITEMS.register("core",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> OLD_SPEAR_HEAD = ITEMS.register("old_spear_head",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> OLD_SPEAR_SHAFT = ITEMS.register("old_spear_shaft",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> OLD_SPEAR_SCARF = ITEMS.register("old_spear_scarf",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> OLD_IRON_SPEAR = ITEMS.register("old_iron_spear",
            () -> new SpearItem(new Item.Properties().attributes(TridentItem.createAttributes()).durability(250)));

    public static final DeferredItem<Item> OLD_DRAGON_SLAYER_SPEAR = ITEMS.register("old_dragon_slayer_spear_3d",
            () -> new SpearItem(new Item.Properties().attributes(TridentItem.createAttributes()).durability(250)));

    public static final DeferredItem<Item> DAWNBREAKER_HILT = ITEMS.register("dawnbreaker_hilt",
            () -> new FuelItem(new Item.Properties(), 90000));

    public static final DeferredItem<Item> DAWNBREAKER_BLADE = ITEMS.register("dawnbreaker_blade",
            () -> new FuelItem(new Item.Properties(), 100000));

    public static final DeferredItem<Item> DAWNBREAKER_TIP = ITEMS.register("dawnbreaker_tip",
            () -> new FuelItem(new Item.Properties(), 90000));

    public static final DeferredItem<Item> DAWNBREAKER = ITEMS.register("dawnbreaker",
            () -> new BlockingSwordItem(Tiers.NETHERITE,
                    new Item.Properties().attributes(BlockingSwordItem.createAttributes(Tiers.NETHERITE, 5, 1F))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
