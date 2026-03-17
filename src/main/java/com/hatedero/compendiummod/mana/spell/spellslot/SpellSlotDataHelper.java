package com.hatedero.compendiummod.mana.spell.spellslot;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;
import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellSlotDataHelper {
    private SpellSlotDataHelper() {
    }

    public static void cooldownHandler(Player player, int cooldown) {
        PlayerSpellData data = player.getData(SPELL_DATA);
        PlayerSpellData newData = new PlayerSpellData(
                data.slots().stream().map(s -> {
                    if (Objects.equals(s.slotName(), data.chargingSlotName())) {
                        return new SpellSlotData(
                                s.slotName(),
                                s.spellId(),
                                0,
                                cooldown
                        );
                    }
                    return s;
                }).toList(),
                ""
        );
        player.setData(SPELL_DATA, newData);
    }

    public static boolean canUseMana(Player player) {
        return player.getData(MANA) - calculateManaCost(player) >= 0;
    }

    public static int calculateManaCost(Player player) {
        return (int) (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY) * player.getAttributeValue(ModAttributes.CASTING_SPEED));
    }
}
