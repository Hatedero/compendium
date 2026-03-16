package com.hatedero.compendiummod.network.SpellData;

import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.CURRENT_SPELL_ID;
import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataUpdateServerPayloadHandler {
    public static void handleDataOnMain(final SpellDataUpdatePayload data, final IPayloadContext context) {
        Player player = context.player();
        PlayerSpellData previousData = player.getData(SPELL_DATA);
        PlayerSpellData newData = previousData;
        List<SpellSlotData> updatedSlots = previousData.slots().stream().map(s -> {
            if (Objects.equals(s.slotName(), previousData.chargingSlotName())) {
                return new SpellSlotData(
                        s.slotName(),
                        s.spellId(),
                        s.chargeLevel(),
                        90
                );
            }
            return s;
        }).toList();
        newData = new PlayerSpellData(updatedSlots, newData.chargingSlotName());
        context.player().setData(SPELL_DATA, newData);
    }
}
