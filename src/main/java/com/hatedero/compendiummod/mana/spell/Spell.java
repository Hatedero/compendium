package com.hatedero.compendiummod.mana.spell;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.CompendiumModClient;
import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

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
        if (!level.isClientSide()) {
            livingEntity.setData(CAST_COOLDOWN, getCooldown());
            livingEntity.setData(IS_CHARGING, false);
            livingEntity.setData(CHARGE_TIME, 0);
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

    public String getTranslationKey() {
        return "spell." + CompendiumMod.MODID + ".";
    }
}
