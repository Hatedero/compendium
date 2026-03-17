package com.hatedero.compendiummod.network.SpellDataActiveSLot;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/*public record SpellDataUpdatePayload(String value) implements CustomPacketPayload {

    public static final Type<SpellDataUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "current_spell_id_update"));

    public static final StreamCodec<ByteBuf, SpellDataUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SpellDataUpdatePayload::value,
            SpellDataUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}*/

public record SpellDataActiveSlotUpdatePayload(String data) implements CustomPacketPayload {
    public static final Type<SpellDataActiveSlotUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "spell_data_active_slot_update"));

    public static final StreamCodec<ByteBuf, SpellDataActiveSlotUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.STRING), SpellDataActiveSlotUpdatePayload::data,
            SpellDataActiveSlotUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}