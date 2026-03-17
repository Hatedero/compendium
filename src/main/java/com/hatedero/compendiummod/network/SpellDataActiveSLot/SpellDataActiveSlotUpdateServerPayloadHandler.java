package com.hatedero.compendiummod.network.SpellDataActiveSLot;

import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataActiveSlotUpdateServerPayloadHandler {
    public static void handleDataOnMain(final SpellDataActiveSlotUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SPELL_DATA, new PlayerSpellData(context.player().getData(SPELL_DATA).slots(), data.data()));
    }
}
