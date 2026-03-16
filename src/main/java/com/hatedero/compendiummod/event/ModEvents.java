package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) return;

        PlayerSpellData currentData = player.getData(ModAttachments.SPELL_DATA);

        List<SpellSlotData> updatedSlots = new ArrayList<>(currentData.slots());

        List<String> slots = List.of("ultimate", "ability_1", "ability_2", "ability_3");
        slots.forEach(slot -> {
            boolean hasSlot = updatedSlots.stream()
                    .anyMatch(s -> s.slotName().equals(slot));

            if (!hasSlot) {
                updatedSlots.add(new SpellSlotData(
                        slot,
                        "empty",
                        1,
                        0
                ));

                player.setData(ModAttachments.SPELL_DATA, new PlayerSpellData(List.copyOf(updatedSlots), slot));

                player.sendSystemMessage(Component.literal( "Added " + slot + " spell slot to " + player.getName().getString()));
            }
        });
    }

}
