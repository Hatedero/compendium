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

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class ReflectBarrierSpell extends Spell {
    float range;

    public ReflectBarrierSpell(float costPerTick, float range) {
        super(costPerTick);
        this.range = range;
    }

    @Override
    public int getUseDuration() {
        return 100;
    }

    @Override
    public int getCooldown() {
        return 60;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);

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
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }
}
