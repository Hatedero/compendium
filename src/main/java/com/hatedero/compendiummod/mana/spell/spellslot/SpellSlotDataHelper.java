package com.hatedero.compendiummod.mana.spell.spellslot;

import net.minecraft.world.entity.player.Player;

public class SpellSlotDataHelper {
    private SpellSlotDataHelper() {
    }

    public static SpellSlotData cooldownHandler(Player player, int cooldown) {
        return new SpellSlotData("","",0,cooldown);
    }
}
