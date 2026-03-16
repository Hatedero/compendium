package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.SpellRegistry;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellList extends ObjectSelectionList<SpellList.MySpellEntry> {
    public SpellList(Minecraft minecraft, int width, int height, int top, int itemHeight) {
        super(minecraft, width, height, top, itemHeight);
    }

    @Override
    public int addEntry(@NotNull MySpellEntry entry) {
        return super.addEntry(entry);
    }

    @Override
    public void clearEntries() {
        super.clearEntries();
    }

    public class MySpellEntry extends ObjectSelectionList.Entry<MySpellEntry> {
        private final Spell spell;

        public MySpellEntry(Spell spell) {
            this.spell = spell;
        }

        public MutableComponent getTranslatedName() {
            return Component.translatable("spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey());
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            guiGraphics.drawString(minecraft.font, getTranslatedName(), left + 5, top + 5, 0xFFFFFF);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload(SpellRegistry.REGISTRY.getKey(spell).getPath()));
            assert Minecraft.getInstance().player != null;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public Component getNarration() {
            return getTranslatedName();
        }
    }
}
