package com.hatedero.compendiummod.network.SpellDataSlotSpell;

import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataSlotSpellUpdateClientPayloadHandler {
    public static void handleDataOnMain(final SpellDataSlotSpellUpdatePayload data, final IPayloadContext context) {
        PlayerSpellData oldData = context.player().getData(SPELL_DATA);
        PlayerSpellData newData;

        List<SpellSlotData> slots = oldData.slots().stream().map( s -> {
            if (s.slotName().equals(data.slot())) {
                return new SpellSlotData(
                        s.slotName(),
                        data.spell(),
                        s.chargeLevel(),
                        s.cooldown()
                );
            }
            return s;
        }).toList();

        newData = new PlayerSpellData(slots,
                oldData.chargingSlotName(),
                oldData.chargeStartTime());

        context.player().setData(SPELL_DATA, newData);
    }
}
