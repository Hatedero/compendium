package com.hatedero.compendiummod.mana;

import com.hatedero.compendiummod.CompendiumMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.server.level.ServerPlayer;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ManaEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Double currentMana = player.getData(ModAttachments.MANA);
            Double maxMana = player.getAttributeValue(ModAttributes.MAX_MANA);
            Double manaRegen = player.getAttributeValue(ModAttributes.MANA_REGEN)/20;

            if (currentMana < maxMana ) {
                if (currentMana + manaRegen > maxMana) {
                    player.setData(ModAttachments.MANA, maxMana);
                    return;
                }
                player.setData(ModAttachments.MANA, currentMana + manaRegen);
            }
        }
    }
}