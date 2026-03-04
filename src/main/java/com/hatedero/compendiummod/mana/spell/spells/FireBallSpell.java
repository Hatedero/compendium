package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;
import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class FireBallSpell extends Spell {
    public FireBallSpell(String name, float costPerTick) {
        super(name, costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 0;
    }

    @Override
    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            //raycast from player to mob and damage them
        }
    }

    @Override
    public boolean canUseMana(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
            if (player.getData(MANA) - cost >= 0 &&  player.getData(CHARGE_TIME) <= getUseDuration())
                return true;
        }
        return false;
    }
}
