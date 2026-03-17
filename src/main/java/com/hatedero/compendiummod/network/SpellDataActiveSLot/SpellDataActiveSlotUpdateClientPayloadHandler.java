package com.hatedero.compendiummod.network.SpellDataActiveSLot;

import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataActiveSlotUpdateClientPayloadHandler {
    public static void handleDataOnMain(final SpellDataActiveSlotUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SPELL_DATA, new PlayerSpellData(context.player().getData(SPELL_DATA).slots(), data.data()));
    }
}
