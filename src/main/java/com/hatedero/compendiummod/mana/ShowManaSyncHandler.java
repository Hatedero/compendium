package com.hatedero.compendiummod.mana;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;

public class ShowManaSyncHandler implements AttachmentSyncHandler<Boolean> {

    @Override
    public void write(RegistryFriendlyByteBuf buf, Boolean attachment, boolean initialSync) {
        buf.writeBoolean(attachment);
    }

    @Override
    public Boolean read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Boolean previousValue) {
        return buf.readBoolean();
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}