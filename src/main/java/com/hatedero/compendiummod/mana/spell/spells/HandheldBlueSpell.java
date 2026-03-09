package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.entity.BlueProjectile;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.ModEntityBehavior;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HandheldBlueSpell extends Spell {
    public HandheldBlueSpell(float costPerTick) {
        super(costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 100;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            Vec3 eyePos = getPointInFront(livingEntity, 1);

            serverLevel.sendParticles(
                    ParticleTypes.GLOW,
                    eyePos.x, eyePos.y, eyePos.z,
                    remainingUseDuration/20,
                    0.05, 0.05, 0.05,
                    0.01
            );
        }
        super.chargeTick(level, livingEntity, remainingUseDuration);
    }

    @Override
    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide() && livingEntity instanceof Player player && remainingUseDuration >= 60) {
            BlueProjectile projectile = new BlueProjectile(ModEntities.BLUE_PROJECTILE.get(), level, ModEntityBehavior.ENTITY_ATTACHED);
            projectile.setOwner(player);
            projectile.setBehavior(ModEntityBehavior.ENTITY_ATTACHED);
            Vec3 eyePos = getPointInFront(livingEntity, 1);
            projectile.setPos(eyePos.x(), eyePos.y(), eyePos.z());

            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.01F, 1.0F);

            level.addFreshEntity(projectile);
        }
        super.release(level, livingEntity, remainingUseDuration);
    }

    public Vec3 getPointInFront(LivingEntity entity, double distance) {
        Vec3 eyePos = entity.getEyePosition().add(0,-entity.getBbHeight() * 0.2,0);

        Vec3 lookDir = entity.getLookAngle();

        return eyePos.add(lookDir.scale(distance));
    }
}
