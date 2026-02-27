package com.hatedero.compendiummod.mana;

import com.hatedero.compendiummod.CompendiumMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE, CompendiumMod.MODID);

    public static final Holder<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana", () -> new RangedAttribute(
            "attributes."+CompendiumMod.MODID+".max_mana",
            10,
            0,
            1000000
    ).setSyncable(true)
    );

    public static final Holder<Attribute> MANA_REGEN = ATTRIBUTES.register("mana_regen", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".mana_regen",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static final Holder<Attribute> MANA_OUTPUT = ATTRIBUTES.register("mana_output", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".mana_output",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static final Holder<Attribute> MANA_INPUT = ATTRIBUTES.register("mana_input", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".mana_input",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static final Holder<Attribute> MANA_EFFICIENCY = ATTRIBUTES.register("mana_efficiency", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".mana_efficiency",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static final Holder<Attribute> COOLDOWN_MULTIPLIER = ATTRIBUTES.register("cooldown_multiplier", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".cooldown_multiplier",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static final Holder<Attribute> CASTING_SPEED = ATTRIBUTES.register("casting_speed", () -> new RangedAttribute(
                    "attributes."+CompendiumMod.MODID+".casting_speed",
                    1,
                    0,
                    1000000
            ).setSyncable(true)
    );

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
