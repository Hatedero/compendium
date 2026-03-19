package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ReflectBarrierSpell extends Spell {
    float range;

    public ReflectBarrierSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, float range) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
        this.range = range;
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
        AABB area = player.getBoundingBox().inflate(range);

        List<Entity> entities = level.getEntities(player, area);

        for (Entity target : entities) {
            if (target instanceof Projectile projectile) {
                Vec3 relPos = projectile.position().subtract(player.position());
                Vec3 velocity = projectile.getDeltaMovement();

                if (velocity.dot(relPos) < 0) {

                    Vec3 motion = target.getDeltaMovement();
                    Vec3 reflectedMotion = motion.scale(-1.2);

                    projectile.shoot(
                            reflectedMotion.x,
                            motion.y,
                            reflectedMotion.z,
                            (float) reflectedMotion.length(),
                            0.0F
                    );

                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                ParticleTypes.CRIT,
                                target.getX(), target.getY(), target.getZ(),
                                1,
                                0, 0, 0,
                                0
                        );
                        serverLevel.playSound(
                                null,
                                target.getX(),
                                target.getY(),
                                target.getZ(),
                                SoundEvents.SHIELD_BLOCK,
                                SoundSource.PLAYERS,
                                1.0F,
                                1.2F
                        );
                    }

                    target.hasImpulse = true;
                    target.hurtMarked = true;

                    projectile.setOwner(player);
                }
            }
        }
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {

    }
}
