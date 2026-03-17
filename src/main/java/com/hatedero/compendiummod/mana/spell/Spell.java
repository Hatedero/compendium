package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;

public abstract class Spell{
    protected float costPerTick;

    public Spell(float costPerTick) {
        this.costPerTick = costPerTick;
    }

    public abstract int getUseDuration();

    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);
            }
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }

    public void release (Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
            livingEntity.setData(SPELL_DATA, SpellSlotDataHelper.cooldownHandler(player, 0));
        }
    }

    public int getCooldown() {
        return 100;
    };

    public boolean canUseMana (LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
            if (player.getData(MANA) - cost >= 0 &&  player.getData(CHARGE_TIME) <= getUseDuration())
                return true;
        }
        return false;
    }
}
