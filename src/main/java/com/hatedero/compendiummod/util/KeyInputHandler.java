package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.GUI.SpellScreen;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import com.hatedero.compendiummod.network.SpellDataActiveSLot.SpellDataActiveSlotUpdatePayload;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().screen != null)
            return;
        if (event.getAction() == GLFW.GLFW_PRESS) {
            String slotToPacket = null;

            if (ModKeybinds.ULTIMATE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ultimate";
            } else if (ModKeybinds.ABILITY_ONE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_1";
            } else if (ModKeybinds.ABILITY_TWO_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_2";
            } else if (ModKeybinds.ABILITY_THREE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_3";
            }

            if (slotToPacket != null) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    PacketDistributor.sendToServer(new SpellDataActiveSlotUpdatePayload(slotToPacket));
                }
            }

            if (ModKeybinds.OPEN_SPELL_MENU.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                Minecraft.getInstance().setScreen(new SpellScreen(Component.literal("Spell List")));
            }
        } else if (event.getAction() == GLFW.GLFW_RELEASE) {
            String slotToPacket = null;

            if (ModKeybinds.ULTIMATE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ultimate";
            } else if (ModKeybinds.ABILITY_ONE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_1";
            } else if (ModKeybinds.ABILITY_TWO_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_2";
            } else if (ModKeybinds.ABILITY_THREE_KEY.matches(event.getKey(), event.getScanCode())) {
                slotToPacket = "ability_3";
            }

            if (slotToPacket != null) {
                assert Minecraft.getInstance().player != null;
                Player player = Minecraft.getInstance().player;
                if (Objects.equals(player.getData(SPELL_DATA).chargingSlotName(), slotToPacket)) {
                    PacketDistributor.sendToServer(new SpellDataActiveSlotUpdatePayload(""));
                }
            }
        }
    }
}