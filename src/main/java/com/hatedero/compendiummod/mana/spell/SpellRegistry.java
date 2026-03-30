package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.entity.ModEntityBehavior;
import com.hatedero.compendiummod.mana.spell.spells.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SpellRegistry {
    public static final ResourceKey<Registry<Spell>> SPELL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "spell"));

    public static final DeferredRegister<Spell> SPELLS =
            DeferredRegister.create(SPELL_REGISTRY_KEY, CompendiumMod.MODID);

    public static final Registry<Spell> REGISTRY = SPELLS.makeRegistry(builder -> builder.sync(true));

    public static Spell getSpell(Level level, String id) {
        Registry<Spell> registry = level.registryAccess().registryOrThrow(SPELL_REGISTRY_KEY);

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, id);

        return registry.get(location);
    }

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    public static final DeferredHolder<Spell, Spell> EMPTY = SPELLS.register("empty",
            () -> new EmptySpell(0, 0, 100) {
                @Override
                public void chargeEffect(Level level, Player player, int manaLevel) {
                }

                @Override
                public void releaseEffect(Level level, Player player, int manaLevel) {
                }
            });

    public static final DeferredHolder<Spell, Spell> DEBUG = SPELLS.register("debug",
            () -> new DebugSpell(2, 200, 100));

    public static final DeferredHolder<Spell, Spell> REVERSE_CURSED_TECHNIQUE = SPELLS.register("reverse_cursed_technique",
            () -> new ReverseCursedTechniqueSpell(5, -1, 200, 100));

    public static final DeferredHolder<Spell, Spell> CLEAVE = SPELLS.register("cleave",
            () -> new CleaveSpell(1, -1, 20));

    public static final DeferredHolder<Spell, Spell> BLUE = SPELLS.register("blue",
            () -> new BlueSpell(1, 200, 20, ModEntityBehavior.THROWN));

    public static final DeferredHolder<Spell, Spell> HANDHELD_BLUE = SPELLS.register("handheld_blue",
            () -> new BlueSpell(1, 200, 20, ModEntityBehavior.ENTITY_ATTACHED));

    public static final DeferredHolder<Spell, Spell> BLINK = SPELLS.register("blink",
            () -> new BlinkSpell(1, 200, 20, 15));

    public static final DeferredHolder<Spell, Spell> ICARUS_DASH = SPELLS.register("icarus_dash",
            () -> new DashSpell(1, 200, 20, 1.5));

    public static final DeferredHolder<Spell, Spell> MANA_BOLT = SPELLS.register("mana_bolt",
            () -> new ManaBoltSpell(1, 200, 20));

    public static final DeferredHolder<Spell, Spell> REFLECT_BARRIER = SPELLS.register("reflect_barrier",
            () -> new ReflectBarrierSpell(1, 200, 20, 3));

    public static final DeferredHolder<Spell, Spell> RED = SPELLS.register("red",
            () -> new RedSpell(1, 200, 20));

    public static final DeferredHolder<Spell, Spell> REPAIR_ITEM = SPELLS.register("repair_item",
            () -> new RepairItemSpell(1, 200, 20, 0.005f));

        public static final DeferredHolder<Spell, Spell> MADE_IN_HEAVEN = SPELLS.register("made_in_heaven",
            () -> new MadeInHeavenSpell(1, 200, 20));
}