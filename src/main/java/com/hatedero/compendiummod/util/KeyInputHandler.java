package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.network.ShowManaUpdatePayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import static com.hatedero.compendiummod.mana.ModAttachments.SHOW_MANA;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {

            if (ModKeybinds.SHOW_MANA_ACTION_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {

                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    Boolean newValue = !player.getData(SHOW_MANA);
                    player.displayClientMessage(Component.literal("Switched mana visibility to " + newValue), true);
                    PacketDistributor.sendToServer(new ShowManaUpdatePayload(newValue));

                }
            }
        }
    }
}