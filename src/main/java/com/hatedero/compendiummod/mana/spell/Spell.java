package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public abstract class Spell{
    protected float costPerTick;
    protected String name;

    public Spell(String name, float costPerTick) {
        this.costPerTick = costPerTick;
        this.name = name;
    }

    public abstract int getUseDuration();

    public String getName() {
        return name;
    }

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

    public abstract void release (Level level, LivingEntity livingEntity, int remainingUseDuration);

    public abstract boolean canUseMana (LivingEntity livingEntity);
}
