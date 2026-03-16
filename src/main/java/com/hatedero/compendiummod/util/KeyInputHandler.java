package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.GUI.SpellScreen;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().screen != null)
            return;
        if (event.getAction() == GLFW.GLFW_PRESS) {
            AtomicReference<String> pressedSlot = new AtomicReference<>();

            Map<KeyMapping, String> keys =
                    Map.ofEntries(Map.entry(ModKeybinds.ULTIMATE_KEY, "ultimate"),
                            Map.entry(ModKeybinds.ABILITY_ONE_KEY, "ability_1"),
                            Map.entry(ModKeybinds.ABILITY_TWO_KEY, "ability_2"),
                            Map.entry(ModKeybinds.ABILITY_THREE_KEY, "ability_3"));

            keys.forEach((k, v) -> {
                if (k.consumeClick())
                    pressedSlot.set(v);
            });

            if (pressedSlot.get() != null) {
                Player player =  Minecraft.getInstance().player;
                PacketDistributor.sendToServer(new SpellDataUpdatePayload(new PlayerSpellData(player.getData(SPELL_DATA).slots(), pressedSlot.get())));
            }

            /*if (ModKeybinds.CHARGE_SPELL_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    PacketDistributor.sendToServer(new IsChargingUpdatePayload(true));
                }
            } else */if (ModKeybinds.OPEN_SPELL_MENU.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                Minecraft.getInstance().setScreen(new SpellScreen(Component.literal("Spell List")));
            }
        } else if (event.getAction() == GLFW.GLFW_RELEASE) {
            AtomicReference<String> pressedSlot = new AtomicReference<>();

            Map<KeyMapping, String> keys =
                    Map.ofEntries(Map.entry(ModKeybinds.ULTIMATE_KEY, "ultimate"),
                            Map.entry(ModKeybinds.ABILITY_ONE_KEY, "ability_1"),
                            Map.entry(ModKeybinds.ABILITY_TWO_KEY, "ability_2"),
                            Map.entry(ModKeybinds.ABILITY_THREE_KEY, "ability_3"));

            keys.forEach((k, v) -> {
                if (k.consumeClick())
                    pressedSlot.set(v);
            });

            if (pressedSlot.get() != null) {
                Player player =  Minecraft.getInstance().player;
                PacketDistributor.sendToServer(new SpellDataUpdatePayload(new PlayerSpellData(player.getData(SPELL_DATA).slots(), "")));
            }
            /*if (ModKeybinds.CHARGE_SPELL_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    PacketDistributor.sendToServer(new IsChargingUpdatePayload(false));
                }
            }*/
        }
    }
}