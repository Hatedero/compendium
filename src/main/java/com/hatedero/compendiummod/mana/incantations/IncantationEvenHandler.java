package com.hatedero.compendiummod.mana.incantations;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class IncantationEvenHandler {
    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        var player = event.getPlayer();

        String messageText = event.getRawText();

        if (messageText.equalsIgnoreCase("blue")) {
            player.sendSystemMessage(Component.literal("Casting blue"));
        }

    }
}
