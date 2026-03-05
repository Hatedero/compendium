package com.hatedero.compendiummod.mana.GUI;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SpellScreen extends Screen {
    private SpellList spellList;

    public SpellScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        this.spellList = new SpellList(this.minecraft, this.width, this.height, 32, 25, this);
        this.addRenderableWidget(this.spellList);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);
    }
}