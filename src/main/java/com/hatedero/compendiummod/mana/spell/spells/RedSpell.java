package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.entity.BlueProjectile;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.RedProjectile;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RedSpell extends Spell {

    public RedSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    public Vec3 getPointInFront(LivingEntity entity, double distance) {
        Vec3 eyePos = entity.getEyePosition().add(0,-entity.getBbHeight() * 0.2,0);

        Vec3 lookDir = entity.getLookAngle();

        return eyePos.add(lookDir.scale(distance));
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
        if (level instanceof ServerLevel serverLevel) {
            Vec3 eyePos = getPointInFront(player, 0.5);
            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    eyePos.x, eyePos.y, eyePos.z,
                    manaLevel / 20,
                    0.05, 0.05, 0.05,
                    0.01
            );
        }
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide() && manaLevel >= 60) {
            RedProjectile projectile = new RedProjectile(ModEntities.RED_PROJECTILE.get(), level);
            projectile.setOwner(player);
            Vec3 eyePos = getPointInFront(player, 1);
            projectile.setPos(eyePos.x(), eyePos.y() - projectile.getBbHeight()/2, eyePos.z());

            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.01F, 1.0F);

            level.addFreshEntity(projectile);
        }
    }
}
