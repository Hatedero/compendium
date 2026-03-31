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

public class MadeInHeavenSpell extends Spell {

    public MadeInHeavenSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
        if(level instanceof ServerLevel serverLevel) {
            serverLevel.setDayTimePerTick(serverLevel.getDayTimePerTick() + 1);
        }
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if(level instanceof ServerLevel serverLevel) {
            serverLevel.setDayTimePerTick(1);
        }
    }
}
