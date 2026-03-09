package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;

import java.text.DecimalFormat;

import static com.hatedero.compendiummod.mana.ModAttachments.CAST_COOLDOWN;

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

        int cooldown = player.getData(CAST_COOLDOWN);
        //float maxCooldown = getSpell(player.level(), player.getData(CURRENT_SPELL_ID)).getCooldown();

        /*
        NOT ACCURATE AS PLAYER CAN CHANGE SPELL WHILE IN COOLDOWN
        But, doesn't matter since cooldown is gonna be on individual spell and not player cast
        */
        if (cooldown > 0) {
            float progress = Math.min(1.0f, cooldown / (5*20f));

            int barWidth = 50;
            int barHeight = 4;
            x = (guiGraphics.guiWidth() - barWidth) / 2;
            y = 10;

            guiGraphics.fill(x, y, x + barWidth, y + barHeight, 0xAA111111);

            int currentProgressWidth = (int) (barWidth * progress);
            guiGraphics.fill(x, y, x + currentProgressWidth, y + barHeight, 0xAAFFFFFF);

            guiGraphics.renderOutline(x - 1, y - 1, barWidth + 2, barHeight + 2, 0xAAFFFFFF);
        }
    }
}