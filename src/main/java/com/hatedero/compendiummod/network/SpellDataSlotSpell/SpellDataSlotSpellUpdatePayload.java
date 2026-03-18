package com.hatedero.compendiummod.network.SpellDataSlotSpell;

import com.hatedero.compendiummod.CompendiumMod;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellDataSlotSpellUpdatePayload(String slot, String spell) implements CustomPacketPayload {
    public static final Type<SpellDataSlotSpellUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "spell_data_slot_spell_update"));

    public static final StreamCodec<ByteBuf, SpellDataSlotSpellUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.STRING), SpellDataSlotSpellUpdatePayload::slot,
            ByteBufCodecs.fromCodec(Codec.STRING), SpellDataSlotSpellUpdatePayload::spell,
            SpellDataSlotSpellUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}