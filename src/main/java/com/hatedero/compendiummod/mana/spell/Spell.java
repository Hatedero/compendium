package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.*;

public abstract class Spell{
    protected int minManaCostPerTick;
    protected int maxManaCharge;
    protected int cooldown;

    public Spell(int minManaCostPerTick,  int maxManaCharge, int cooldown) {
        this.minManaCostPerTick = minManaCostPerTick;
        this.maxManaCharge = maxManaCharge;
        this.cooldown = cooldown;
    }

    public void chargeTick(Level level, Player player, int manaLevel, String slotName) {
            int cost = (int) (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY) * player.getAttributeValue(ModAttributes.CASTING_SPEED));
            if(manaLevel > this.maxManaCharge)
                manaLevel = this.maxManaCharge;
            if (canUseMana(player, manaLevel)) {
                if(player.getData(SPELL_DATA).chargeStartTime() == player.level().getGameTime()-1)
                    startEffect(level, player, manaLevel);
                player.setData(MANA, player.getData(MANA) - cost);
                chargeEffect(level, player, manaLevel);
            } else {
                release(level, player, manaLevel, slotName);
            }
    }

    public void release (Level level, Player player, int remainingUseDuration, String slotName) {
            releaseEffect(level, player, remainingUseDuration);
            SpellSlotDataHelper.putSlotOnCooldown(player, getCooldown(), slotName);
    }

    public void startEffect(Level level, Player player, int manaLevel) {
    }

    public abstract void chargeEffect(Level level, Player player, int manaLevel);
    public abstract void releaseEffect(Level level, Player player, int manaLevel);

    public int getCooldown() {
        return this.cooldown;
    };

    public boolean canUseMana (Player player, int manaLevel) {
        return SpellSlotDataHelper.canUseMana(player) && (maxManaCharge < 0 || manaLevel <= maxManaCharge);
    }
}
