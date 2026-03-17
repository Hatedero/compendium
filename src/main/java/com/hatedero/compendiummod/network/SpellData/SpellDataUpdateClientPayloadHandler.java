package com.hatedero.compendiummod.network.SpellData;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataUpdateClientPayloadHandler {
    public static void handleDataOnMain(final SpellDataUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SPELL_DATA, data.data());
    }
}
