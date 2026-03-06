package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.SpellRegistry;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

        for (Spell spell : SpellRegistry.REGISTRY) {

        }

        SpellRegistry.REGISTRY.entrySet().forEach(entry -> {
            ResourceLocation id = entry.getKey().location();
            Spell spell = entry.getValue();

            String translationKey = "spell." + id.getNamespace() + "." + id.getPath();

            this.addRenderableWidget(Button.builder(
                            Component.translatable(translationKey),
                            button -> {
                                PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload(id.getPath()));
                                this.onClose();
                            })
                    .bounds((this.width - bw) / 2, 10 + index.get() * bh * 2, bw, bh)
                    .tooltip(Tooltip.create(Component.literal("Change Spell")))
                    .build());
            index.getAndIncrement();
        });
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}