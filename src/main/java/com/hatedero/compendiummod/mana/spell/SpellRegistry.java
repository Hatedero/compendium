package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.spells.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    public static final DeferredHolder<Spell, Spell> REVERSE_CURSED_TECHNIQUE = SPELLS.register("reverse_cursed_technique",
            () -> new ReverseCursedTechniqueSpell(5));

    public static final DeferredHolder<Spell, Spell> DISMANTLE = SPELLS.register("dismantle",
            () -> new DismantleSpell(3));

    public static final DeferredHolder<Spell, Spell> ICARUS_DASH = SPELLS.register("icarus_dash",
            () -> new DashSpell(1));

    public static final DeferredHolder<Spell, Spell> BLINK = SPELLS.register("blink",
            () -> new BlinkSpell(1));

    public static final DeferredHolder<Spell, Spell> GRAVITY_ZONE = SPELLS.register("gravity_zone",
            () -> new GravityZoneSpell(1, 1, 10, false));

    public static final DeferredHolder<Spell, Spell> ANTI_GRAVITY_ZONE = SPELLS.register("anti_gravity_zone",
            () -> new GravityZoneSpell(1, -1, 10, true));

    public static final DeferredHolder<Spell, Spell> INFINITY = SPELLS.register("infinity",
            () -> new InfinitySpell(1, 5));

    public static final DeferredHolder<Spell, Spell> BLUE = SPELLS.register("blue",
            () -> new BlueSpell( 5));

    public static final DeferredHolder<Spell, Spell> HANDHELD_BLUE = SPELLS.register("handheld_blue",
            () -> new BlueSpell( 5));

    public static final DeferredHolder<Spell, Spell> RED = SPELLS.register("red",
            () -> new RedSpell(5));
}