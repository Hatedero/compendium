package com.hatedero.compendiummod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueProjectile extends AbstractHurtingProjectile {
    private int age = 0;
    private static final int MAX_LIFE = 100;

    public BlueProjectile(EntityType<? extends BlueProjectile> type, Level level) {
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
        double speedLimit = 0.7D;

        if (!this.level().isClientSide()) {
            int attempts = this.age%20*3+2;
            double blockRadius = this.age%20*2+1;

            for (int i = 0; i < attempts; i++) {
                BlockPos randomPos = this.blockPosition().offset(
                        this.level().random.nextInt((int) blockRadius * 2) - (int) blockRadius,
                        this.level().random.nextInt((int) blockRadius * 2) - (int) blockRadius,
                        this.level().random.nextInt((int) blockRadius * 2) - (int) blockRadius
                );

                tryTurningBlockInBlockEntity(randomPos);
            }
        }

        if (movement.length() > speedLimit) {
            this.setDeltaMovement(movement.normalize().scale(speedLimit));
        }

        double pullStrength = 0.5D;
        double radius = 3.0D;

        AABB area = this.getBoundingBox().inflate(radius);

        List<Entity> entities = this.level().getEntities(this, area);

        for (Entity target : entities) {
            if (this.ownedBy(target))
                break;

            Vec3 pullVector = this.position().subtract(target.position()).normalize();

            target.setDeltaMovement(target.getDeltaMovement().add(pullVector.scale(pullStrength)));

            target.hasImpulse = true;
        }
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

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
        if (!level().isClientSide()){
            if(result.getEntity() instanceof LivingEntity entity) {
                entity.hurt(this.level().damageSources().inWall(), 1);
            } else {
                result.getEntity().discard();
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide()) {
            BlockPos pos = result.getBlockPos();
            if (!tryTurningBlockInBlockEntity(pos))
                this.setDeltaMovement(Vec3.ZERO);
            super.onHitBlock(result);
        }
    }

    protected boolean tryTurningBlockInBlockEntity(BlockPos pos) {
        float resistanceThreshold = 600.0F;
        BlockState state = this.level().getBlockState(pos);

        float blockResistance = state.getBlock().getExplosionResistance();

        if (!state.isAir() && state.getDestroySpeed(this.level(), pos) >= 0 && blockResistance < resistanceThreshold) {

            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(this.level(), pos, state);

            fallingBlock.dropItem = false;

            this.level().removeBlock(pos, false);

            fallingBlock.setDeltaMovement(0, 0.1, 0);
            this.level().addFreshEntity(fallingBlock);
            return true;
        }
        return false;
    }
}
