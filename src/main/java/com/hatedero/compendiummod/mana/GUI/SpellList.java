package com.hatedero.compendiummod.mana.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellList extends ObjectSelectionList<SpellEntry> {
    public SpellList(Minecraft minecraft, int width, int height, int top, int itemHeight, SpellScreen parent) {
        super(minecraft, width, height, top, itemHeight);

        SPELLS.getEntries().forEach(entry -> {
            String name = entry.getRegisteredName().replaceAll("compendiummod:", "");
            this.addEntry(new SpellEntry(parent, name));
        });
    }
}