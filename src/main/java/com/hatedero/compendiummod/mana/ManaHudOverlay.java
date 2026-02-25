package com.hatedero.compendiummod.mana;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;

public class ManaHudOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player == null || player.isSpectator()) return;

        int mana = player.getData(ModAttachments.MANA);
        int max_mana = ((int) player.getAttributeValue(ModAttributes.MAX_MANA));
        String text = "Mana: " + mana + "/" + max_mana;

        int x = guiGraphics.guiWidth() - 10 - minecraft.font.width(text);
        int y = guiGraphics.guiHeight() - 20;

        guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF);
    }
}