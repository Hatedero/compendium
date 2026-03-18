package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.SpellRegistry;
import com.hatedero.compendiummod.network.SpellDataSlotSpell.SpellDataSlotSpellUpdatePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellList extends ObjectSelectionList<SpellList.MySpellEntry> {

    public SpellList(Minecraft minecraft, int width, int height, int top, int itemHeight) {
        super(minecraft, width, height, top, itemHeight);
    }

    @Override
    public int getRowWidth() {
        return 260;
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 20;
    }

    @Override
    public void clearEntries() {
        super.clearEntries();
    }

    @Override
    public int addEntry(MySpellEntry entry) {
        return super.addEntry(entry);
    }

    public class MySpellEntry extends ObjectSelectionList.Entry<MySpellEntry> {
        private final Spell spell;

        private static final int ICON_SIZE = 18;
        private static final int ICON_SPACING = 4;
        private static final int BUTTON_START_X = 130;
        private static final List<String> SLOTS = List.of("ultimate", "ability_1", "ability_2", "ability_3");

        public MySpellEntry(Spell spell) {
            this.spell = spell;
        }

        public MutableComponent getTranslatedName() {
            return Component.translatable("spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey());
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            // Render Label
            guiGraphics.drawString(minecraft.font, getTranslatedName(), left + 5, top + (height / 2 - 4), 0xFFFFFF);

            for (int i = 0; i < SLOTS.size(); i++) {
                String slotName = SLOTS.get(i);
                int bx = left + BUTTON_START_X + (i * (ICON_SIZE + ICON_SPACING));
                int by = top + (height / 2 - (ICON_SIZE / 2));

                boolean hovered = mouseX >= bx && mouseX < bx + ICON_SIZE && mouseY >= by && mouseY < by + ICON_SIZE;

                ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "textures/gui/" + slotName + ".png");
                guiGraphics.blit(texture, bx, by, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

                if (hovered) {
                    guiGraphics.fill(bx, by, bx + ICON_SIZE, by + ICON_SIZE, 0x80FFFFFF);
                    guiGraphics.renderOutline(bx, by, ICON_SIZE, ICON_SIZE, 0xFFFFFFFF);

                    // Tooltip
                    Component tooltip = Component.translatable("slot." + CompendiumMod.MODID + "." + slotName);
                    guiGraphics.renderTooltip(minecraft.font, tooltip, mouseX, mouseY);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                int index = SpellList.this.children().indexOf(this);
                int entryTop = SpellList.this.getRowTop(index);
                int entryLeft = SpellList.this.getRowLeft();

                for (int i = 0; i < SLOTS.size(); i++) {
                    int bx = entryLeft + BUTTON_START_X + (i * (ICON_SIZE + ICON_SPACING));
                    int by = entryTop + (SpellList.this.itemHeight / 2 - (ICON_SIZE / 2));

                    if (mouseX >= bx && mouseX < bx + ICON_SIZE && mouseY >= by && mouseY < by + ICON_SIZE) {
                        this.onMagicSlotClicked(i);
                        return true;
                    }
                }
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        private void onMagicSlotClicked(int slotIndex) {
            ResourceLocation spellKey = SpellRegistry.REGISTRY.getKey(spell);
            if (spellKey != null) {
                PacketDistributor.sendToServer(new SpellDataSlotSpellUpdatePayload(SLOTS.get(slotIndex), spellKey.getPath()));
            }
        }

        @Override
        public Component getNarration() {
            return getTranslatedName();
        }
    }
}