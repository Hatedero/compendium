package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.spells.EmptySpell;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;


@EventBusSubscriber(modid = CompendiumMod.MODID)
public class SpellEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            handleSpellCharging(player);
            handleLeftOverCharges(player);
            handleCooldowns(player);
        }
    }

    private static void handleCooldowns(ServerPlayer player) {
        Level level = player.level();

        if (level.isClientSide()) return;

        PlayerSpellData data = player.getData(SPELL_DATA);

        PlayerSpellData newData = data;

        List<SpellSlotData> updatedSlots = data.slots().stream().map(s -> {
                if (!Objects.equals(s.slotName(), data.chargingSlotName()) && s.cooldown() > 0) {
                    return new SpellSlotData(
                            s.slotName(),
                            s.spellId(),
                            s.chargeLevel(),
                            s.cooldown() - 1
                    );
                }
                return s;
            }).toList();
        newData = new PlayerSpellData(updatedSlots, data.chargingSlotName(), data.chargeStartTime());
        player.setData(SPELL_DATA, newData);
    }

    private static void handleLeftOverCharges(ServerPlayer player) {
        Level level = player.level();

        if (level.isClientSide()) return;

        PlayerSpellData data = player.getData(SPELL_DATA);

        data.slots().forEach(s -> {
            if (s.chargeLevel() > 0 && !s.slotName().equals(data.chargingSlotName())) {
                Spell spell = getSpell(level, s.spellId());
                spell.release(level, player, s.chargeLevel(), s.slotName());
            }
        });
    }

    private static void handleSpellCharging(ServerPlayer player) {
        Level level = player.level();

        if (level.isClientSide()) return;

        PlayerSpellData data = player.getData(SPELL_DATA);

        if(data.chargingSlotName().isEmpty()) return;

        SpellSlotData slot = data.slots().stream()
                .filter(s -> s.slotName().equals(data.chargingSlotName()))
                .findFirst()
                .orElse(null);

        PlayerSpellData newData = data;

        if (slot == null || slot.cooldown() > 0) return;

        Spell spell = SpellRegistry.getSpell(level, slot.spellId());
        int chargeLevel = (int) (slot.chargeLevel() + (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY) * player.getAttributeValue(ModAttributes.CASTING_SPEED)));

        if (spell == null || spell instanceof EmptySpell) {
            player.setData(SPELL_DATA, newData);
        } else {
            List<SpellSlotData> updatedSlots = data.slots().stream().map(s -> {
                if (Objects.equals(s.slotName(), data.chargingSlotName())) {
                    return new SpellSlotData(
                            s.slotName(),
                            s.spellId(),
                            chargeLevel,
                            s.cooldown()
                    );
                }
                return s;
            }).toList();
            newData = new PlayerSpellData(updatedSlots, data.chargingSlotName(), data.chargeStartTime());
            player.setData(SPELL_DATA, newData);
            spell.chargeTick(level, player, chargeLevel, data.chargingSlotName());
        }
    }
}