package com.hatedero.compendiummod.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedProjectile extends AbstractHurtingProjectile {
    private int age = 0;
    private static final int MAX_LIFE = 100;

    public RedProjectile(EntityType<? extends RedProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        this.age++;

        if (this.age >= MAX_LIFE) {
            if (!this.level().isClientSide) {
                this.discard();
            }
        }

        super.tick();

        Vec3 movement = this.getDeltaMovement();
        double speedLimit = 0.5D;

        if (movement.length() > speedLimit) {
            this.setDeltaMovement(movement.normalize().scale(speedLimit));
        }

        if (!this.level().isClientSide() && this.tickCount%2 == 0) {
            this.level().explode(this, getX(), getY(), getZ(), 1, Level.ExplosionInteraction.BLOCK);
        }

        if (this.level().isClientSide()) {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Age", this.age);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.age = compound.getInt("Age");
    }

    @Override
    protected void onDeflection(@Nullable Entity entity, boolean deflectedByPlayer) {
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if(!level().isClientSide()) {
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity le)
                le.knockback(this.age%20, getRandomX(1), getRandomY());
        }

        super.onHitEntity(result);
    }
}
