package com.hatedero.compendiummod.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.SHOW_MANA;

public class ServerPayloadHandler {
    public static void handleDataOnMain(final ShowManaUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SHOW_MANA, data.value());
    }
}
