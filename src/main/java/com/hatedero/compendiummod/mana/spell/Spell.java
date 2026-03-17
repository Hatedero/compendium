package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;

public abstract class Spell{
    protected int minManaCostPerTick;
    protected int maxManaCharge;
    protected int cooldown;

    public Spell(int minManaCostPerTick,  int maxManaCharge, int cooldown) {
        this.minManaCostPerTick = minManaCostPerTick;
        this.maxManaCharge = maxManaCharge;
        this.cooldown = cooldown;
    }

    public void chargeTick(Level level, Player player, int manaLevel) {
        if (!level.isClientSide) {
            int cost = (int) (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY) * player.getAttributeValue(ModAttributes.CASTING_SPEED));
            if (canUseMana(player, cost, manaLevel)) {
                player.setData(MANA, player.getData(MANA) - cost);
                chargeEffect(level, player, manaLevel);
            } else {
                release(level, player, manaLevel);
            }
        }
    }

    public void release (Level level, Player player, int remainingUseDuration) {
        if (!level.isClientSide()) {
            releaseEffect(level, player, remainingUseDuration);
            SpellSlotDataHelper.cooldownHandler(player, getCooldown());
        }
    }

    public abstract void chargeEffect(Level level, Player player, int manaLevel);
    public abstract void releaseEffect(Level level, Player player, int manaLevel);

    public int getCooldown() {
        return this.cooldown;
    };

    public boolean canUseMana (Player player, int cost, int manaLevel) {
        if (SpellSlotDataHelper.canUseMana(player) && manaLevel <= maxManaCharge)
            return true;
        return false;
    }
}
