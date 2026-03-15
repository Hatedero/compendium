package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

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

        boolean isCharging = player.getData(ModAttachments.IS_CHARGING);
        int manaCharged = player.getData(CHARGE_TIME);
        int cooldown = player.getData(CAST_COOLDOWN);
        Spell spell = getSpell(player.level(), player.getData(CURRENT_SPELL_ID));

        if (spell == null) {
            player.setData(IS_CHARGING, false);
            player.setData(CHARGE_TIME, 0);
            player.setData(CAST_COOLDOWN, 0);
            return;
        }

        if (isCharging) {
            player.setData(CHARGE_TIME, player.getData(CHARGE_TIME) + 1);
            spell.chargeTick(level, player, player.getData(CHARGE_TIME));
        } else if (cooldown > 0) {
            player.setData(CAST_COOLDOWN, cooldown - 1);
        } else if (manaCharged > 0) {
            spell.release(level, player, player.getData(CHARGE_TIME));
        }
    }
}