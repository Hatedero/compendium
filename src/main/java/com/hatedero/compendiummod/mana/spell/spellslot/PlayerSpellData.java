package com.hatedero.compendiummod.mana.spell.spellslot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record PlayerSpellData(List<SpellSlotData> slots, String chargingSlotName) {
    public static final Codec<PlayerSpellData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            SpellSlotData.CODEC.listOf().fieldOf("slots").forGetter(PlayerSpellData::slots),
            Codec.STRING.fieldOf("charging_slot_name").forGetter(PlayerSpellData::chargingSlotName)
    ).apply(inst, PlayerSpellData::new));

    public static PlayerSpellData empty() {
        return new PlayerSpellData(List.of(), "");
    }
}