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
        context.player().sendSystemMessage(Component.literal("CLIENT SHOULDN'T MODIFY SERVER DATA"));
    }
}
