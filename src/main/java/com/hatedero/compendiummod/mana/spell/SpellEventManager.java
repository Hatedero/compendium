package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.spell.spells.EmptySpell;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;


@EventBusSubscriber(modid = CompendiumMod.MODID)
public class SpellEventManager {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            handleSpellCharging(player);
        }
    }

    private static void handleSpellCharging(ServerPlayer player) {
        Level level = player.level();

        PlayerSpellData data = player.getData(SPELL_DATA);
        SpellSlotData slot = data.slots().stream()
                .filter(s -> s.slotName().equals(data.chargingSlotName()))
                .findFirst()
                .orElse(null);

        PlayerSpellData newData = data;
        boolean needsUpdate = newData.slots().stream().anyMatch(s -> s.cooldown() > 0);

        if (needsUpdate) {
            List<SpellSlotData> updatedSlots = data.slots().stream().map(s -> {
                if (s.cooldown() > 0) {
                    return new SpellSlotData(
                            s.slotName(),
                            s.spellId(),
                            s.chargeLevel(),
                            s.cooldown() - 1
                    );
                }
                return s;
            }).toList();
            newData = new PlayerSpellData(updatedSlots, newData.chargingSlotName());
        }

        if (slot == null) {
            player.setData(SPELL_DATA, newData);
            return;
        }

        Spell spell = SpellRegistry.getSpell(level, slot.slotName());

        if (spell instanceof EmptySpell || spell == null) {
        } else {
            List<SpellSlotData> updatedSlots = data.slots().stream().map(s -> {
                if (Objects.equals(s.slotName(), slot.slotName())) {
                    return new SpellSlotData(
                            s.slotName(),
                            s.spellId(),
                            s.chargeLevel() + 1,
                            s.cooldown()
                    );
                }
                return s;
            }).toList();
            newData = new PlayerSpellData(updatedSlots, newData.chargingSlotName());
            spell.chargeTick(level, player, player.getData(CHARGE_TIME));
        }
        player.setData(SPELL_DATA, newData);
    }
}