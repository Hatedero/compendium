package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.entity.ManaBoltProjectile;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.RedProjectile;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ManaBoltSpell extends Spell {

    public ManaBoltSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
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
        if (!level.isClientSide()) {
            ManaBoltProjectile projectile = new ManaBoltProjectile(ModEntities.MANA_BOLT_PROJECTILE.get(), level, 200,manaLevel%20*2f);
            projectile.setOwner(player);
            Vec3 eyePos = getPointInFront(player, 1);
            projectile.setPos(eyePos.x(), eyePos.y() - projectile.getBbHeight()/2, eyePos.z());

            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.01F, 1.0F);

            level.addFreshEntity(projectile);
        }
    }
}
