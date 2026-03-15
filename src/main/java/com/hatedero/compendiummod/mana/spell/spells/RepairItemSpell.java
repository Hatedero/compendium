package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class RepairItemSpell extends Spell {
    public RepairItemSpell(float costPerTick) {
        super(costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 100;
    }

    @Override
    public int getCooldown() {
        return 1;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (level instanceof ServerLevel && livingEntity instanceof Player player && player.tickCount%20 == 0) {
            if (canUseMana(livingEntity) && player.getItemInHand(InteractionHand.MAIN_HAND).isDamaged()) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);
                ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
                int repairAmount = (int) (1 + (item.getMaxDamage()*0.005));
                int currentDamage = item.getDamageValue();
                int newDamage = Math.max(0, currentDamage - repairAmount);
                item.setDamageValue(newDamage);
            }
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }
}
