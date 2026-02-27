package com.hatedero.compendiummod.mana;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

import java.text.DecimalFormat;

public class ManaHudOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player == null || player.isSpectator()) return;

        DecimalFormat df = new DecimalFormat("#.#");

        double mana = player.getData(ModAttachments.MANA);
        double max_mana = player.getAttributeValue(ModAttributes.MAX_MANA);
        double mana_regen = player.getAttributeValue(ModAttributes.MANA_REGEN);
        String text = df.format(mana) + "/" + df.format(max_mana);
        String secondText = (mana_regen > 0 ? "+ " : "- ") + df.format(mana_regen);

        int x = guiGraphics.guiWidth() - 10 - minecraft.font.width(text);
        int x2 = guiGraphics.guiWidth() - 10 - minecraft.font.width(secondText);
        int y = guiGraphics.guiHeight() - 20;

        guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF);
        guiGraphics.drawString(minecraft.font, secondText, x2, y-20, 0xFFFFFF);
    }
}