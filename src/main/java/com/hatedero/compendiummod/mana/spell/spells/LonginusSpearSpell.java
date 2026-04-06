package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.RedProjectile;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.particles.ParticleHelper;
import com.hatedero.compendiummod.util.SpaceHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LonginusSpearSpell extends Spell {

    public LonginusSpearSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    public Vec3 getPointInFront(LivingEntity entity, double distance) {
        Vec3 eyePos = entity.getEyePosition().add(0,-entity.getBbHeight() * 0.2,0);

        Vec3 lookDir = entity.getLookAngle();

        return eyePos.add(lookDir.scale(distance));
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if(manaLevel < 60)
            return;
        if (!level.isClientSide()) {
            HitResult result = SpaceHelper.getPlayerLookingAt(player, 50);

            if (result instanceof EntityHitResult) {
                var entity = ((EntityHitResult) result).getEntity();

                entity.hurt(level.damageSources().playerAttack(player), (float) manaLevel /20);
            }
        } else {
            ParticleHelper.spawnImpactAt(level, SpaceHelper.getPointInFront(player, 1));
        }
    }
}
