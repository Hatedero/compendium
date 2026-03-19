package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.entity.BlueProjectile;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.ModEntityBehavior;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.particles.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class BlueSpell extends Spell {
    private ModEntityBehavior behavior;

    public BlueSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, ModEntityBehavior behavior) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
        this.behavior = behavior;
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
                    ParticleTypes.GLOW,
                    eyePos.x, eyePos.y, eyePos.z,
                    manaLevel / 20,
                    0.05, 0.05, 0.05,
                    0.01
            );
        } else if (level.isClientSide()){
            player.sendSystemMessage(Component.literal("SHOW ME"));
        }
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide() && manaLevel >= 60) {
            BlueProjectile projectile = new BlueProjectile(ModEntities.BLUE_PROJECTILE.get(), level, behavior, 0.5D + 0.25 * ((double) manaLevel /(manaLevel+20*2)), 1D, manaLevel + 20 * 2, 8, 0.5);
            projectile.setOwner(player);
            Vec3 eyePos = getPointInFront(player, 1);
            projectile.setPos(eyePos.x(), eyePos.y(), eyePos.z());

            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.01F, 1.0F);

            level.addFreshEntity(projectile);
        }
    }
}
