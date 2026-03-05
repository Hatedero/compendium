package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.spells.BlinkSpell;
import com.hatedero.compendiummod.mana.spell.spells.DashSpell;
import com.hatedero.compendiummod.mana.spell.spells.DismantleSpell;
import com.hatedero.compendiummod.mana.spell.spells.ReverseCursedTechniqueSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SpellRegistry {
    public static final ResourceKey<Registry<Spell>> SPELL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "spells"));

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
            () -> new ReverseCursedTechniqueSpell("Reverse Cursed Technique", 5));

    public static final DeferredHolder<Spell, Spell> DISMANTLE = SPELLS.register("dismantle",
            () -> new DismantleSpell("Dismantle", 3));

    public static final DeferredHolder<Spell, Spell> ICARUS_DASH = SPELLS.register("icarus_dash",
            () -> new DashSpell("Icarus Dash", 1));

    public static final DeferredHolder<Spell, Spell> BLINK = SPELLS.register("blink",
            () -> new BlinkSpell("Blink", 1));
}