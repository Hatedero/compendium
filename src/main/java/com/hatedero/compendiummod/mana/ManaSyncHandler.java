package com.hatedero.compendiummod.mana;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;
public class ManaSyncHandler implements AttachmentSyncHandler<Integer> {

    @Override
    public void write(RegistryFriendlyByteBuf buf, Integer attachment, boolean initialSync) {
        buf.writeVarInt(attachment);
    }

    @Override
    public Integer read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Integer previousValue) {
        return buf.readVarInt();
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}