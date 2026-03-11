package com.hatedero.compendiummod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueProjectile extends AbstractHurtingProjectile {
    private int age = 0;
    private int maxLife;
    private double attractionForce;
    private double radius;
    private ModEntityBehavior behavior;

    public BlueProjectile(EntityType<? extends BlueProjectile> type, Level level, ModEntityBehavior behavior, double attractionForce, double radius, int maxLife) {
        super(type, level);
        this.behavior = behavior;
        this.attractionForce = attractionForce;
        this.radius = radius;
        this.maxLife = maxLife;
    }

    public BlueProjectile(EntityType<BlueProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        this.age++;

        if (this.age >= maxLife) {
            if (!this.level().isClientSide) {
                this.discard();
            }
        }

        if(behavior == null) return;

        switch (behavior) {
            case ENTITY_ATTACHED -> {
                if (!this.level().isClientSide()) {
                    Entity owner = getOwner();
                    if (owner instanceof Player player) {
                        this.setPos(getPositionInLookDirection(player, 8));
                    }
                }
            }
            case THROWN -> {
                super.tick();
                Vec3 movement = this.getDeltaMovement();
                double speedLimit = 0.5D;

                if (!this.level().isClientSide()) {
                    if (movement.length() > speedLimit) {
                        this.setDeltaMovement(movement.normalize().scale(speedLimit));
                    }
                }
            }
        }

        if (!this.level().isClientSide()) {

            AABB area = this.getBoundingBox().inflate(radius);

            for (BlockPos pos : BlockPos.betweenClosed(
                    BlockPos.containing(area.minX, area.minY, area.minZ),
                    BlockPos.containing(area.maxX, area.maxY, area.maxZ))) {
                tryTurningBlockInBlockEntity(pos);
            }

            List<Entity> entities = this.level().getEntities(this, area);

            for (Entity target : entities) {
                if (this.ownedBy(target))
                    break;

                Vec3 pullVector = this.position().subtract(target.position()).normalize();

                target.setDeltaMovement(target.getDeltaMovement().add(pullVector.scale(attractionForce)));

                target.hasImpulse = true;
            }
        }
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

        }
    }

    public Vec3 getPositionInLookDirection(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition(1.0F);

        Vec3 lookVec = player.getViewVector(1.0F);

        return eyePos.add(lookVec.scale(distance));
    }

    public void setBehavior(ModEntityBehavior behavior) {
        this.behavior = behavior;
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

        if (!state.isAir() && !state.liquid() && state.getDestroySpeed(this.level(), pos) >= 0 && blockResistance < resistanceThreshold) {

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
