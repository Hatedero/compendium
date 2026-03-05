package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.atomic.AtomicInteger;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellScreen extends Screen {
    public SpellScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        int bw = 100;
        int bh = 20;
        int gap = 10;
        AtomicInteger index = new AtomicInteger();

        SPELLS.getEntries().forEach(entry -> {
            this.addRenderableWidget(Button.builder(
                            Component.literal(entry.getRegisteredName().replaceAll("compendiummod:", "")),
                            button -> {
                                PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload(entry.getRegisteredName().replaceAll("compendiummod:", "")));
                            })
                    .bounds((this.width - bw )/2, 10 + index.get() * bh * 2, bw, bh)
                    .tooltip(Tooltip.create(Component.literal("Change Spell")))
                    .build());
            index.getAndIncrement();
        });
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
