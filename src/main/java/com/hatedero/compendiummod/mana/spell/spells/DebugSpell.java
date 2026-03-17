package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;
import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class DebugSpell extends Spell {
    public DebugSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
    }
}
