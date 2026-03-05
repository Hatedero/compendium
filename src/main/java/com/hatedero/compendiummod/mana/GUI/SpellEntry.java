package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class SpellEntry extends ObjectSelectionList.Entry<SpellEntry> {
    private final String spellName;
    private final Button button;

    public SpellEntry(SpellScreen parent, String spellName) {
        this.spellName = spellName;
        this.button = Button.builder(Component.literal(spellName), b -> {
            PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload(spellName));
            parent.onClose();
        }).bounds(0, 0, 100, 20).build();
    }

    @Override
    public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTick) {
        this.button.setX(left + (width - this.button.getWidth()) / 2);
        this.button.setY(top);
        this.button.render(graphics, mouseX, mouseY, partialTick);
    }

    public List<? extends GuiEventListener> children() { return List.of(button); }

    public List<? extends NarratableEntry> narratables() { return List.of(button); }

    @Override
    public Component getNarration() {
        return null;
    }
}