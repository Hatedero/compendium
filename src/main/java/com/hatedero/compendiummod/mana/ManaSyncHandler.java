package com.hatedero.compendiummod.mana;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;
public class ManaSyncHandler implements AttachmentSyncHandler<Double> {

    @Override
    public void write(RegistryFriendlyByteBuf buf, Double attachment, boolean initialSync) {
        buf.writeDouble(attachment);
    }

    @Override
    public Double read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Double previousValue) {
        return buf.readDouble();
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}