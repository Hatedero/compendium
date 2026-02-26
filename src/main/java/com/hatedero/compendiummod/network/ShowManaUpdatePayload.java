package com.hatedero.compendiummod.network;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/*public record ShowManaUpdatePayload(boolean on) implements CustomPacketPayload {
    public static final Type<ShowManaUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "show_mana_update"));

    public static final StreamCodec<ByteBuf, ShowManaUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ShowManaUpdatePayload::on,
            ShowManaUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(final ShowManaUpdatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            player.setData(ModAttachments.SHOW_MANA, payload.on());

            player.displayClientMessage(Component.literal("Mana display: " + payload.on()), true);
        });
    }
}*/

public record ShowManaUpdatePayload(Boolean value) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ShowManaUpdatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "show_mana_update"));

    public static final StreamCodec<ByteBuf, ShowManaUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ShowManaUpdatePayload::value,
            ShowManaUpdatePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}