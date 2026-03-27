package com.hatedero.compendiummod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueProjectile extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Integer> DATA_BEHAVIOR = SynchedEntityData.defineId(BlueProjectile.class, EntityDataSerializers.INT);

    private int age = 0;
    private int maxLife = 200;
    private double attractionForce = 0.1;
    private double radius = 3.0;
    private double range = 5.0;
    private double maxSpeed = 1.0;

    public BlueProjectile(EntityType<? extends BlueProjectile> type, Level level) {
        super(type, level);
    }

    public BlueProjectile(EntityType<? extends BlueProjectile> type, Level level, ModEntityBehavior behavior, double attractionForce, double radius, int maxLife, double range, double maxSpeed) {
        super(type, level);
        this.setBehavior(behavior);
        this.attractionForce = attractionForce;
        this.radius = radius;
        this.maxLife = maxLife;
        this.range = range;
        this.maxSpeed = maxSpeed;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_BEHAVIOR, ModEntityBehavior.THROWN.ordinal());
    }

    public void setBehavior(ModEntityBehavior behavior) {
        this.entityData.set(DATA_BEHAVIOR, behavior.ordinal());
    }

    public ModEntityBehavior getBehavior() {
        return ModEntityBehavior.values()[this.entityData.get(DATA_BEHAVIOR)];
    }

    @Override
    public void tick() {
        this.age++;

        ModEntityBehavior currentBehavior = getBehavior();

        switch (currentBehavior) {
            case ENTITY_ATTACHED -> {
                Entity owner = getOwner();
                if (owner instanceof Player player) {
                    this.setPos(getPositionInLookDirection(player, range));
                }
            }
            case THROWN -> {
                super.tick();

                Vec3 movement = this.getDeltaMovement();
                if (movement.length() > maxSpeed) {
                    this.setDeltaMovement(movement.normalize().scale(maxSpeed));
                }
            }
        }

        if (!this.level().isClientSide) {
            if (this.age >= maxLife) {
                this.discard();
                return;
            }

            AABB area = this.getBoundingBox().inflate(radius);

            for (BlockPos pos : BlockPos.betweenClosed(
                    BlockPos.containing(area.minX, area.minY, area.minZ),
                    BlockPos.containing(area.maxX, area.maxY, area.maxZ))) {
                tryTurningBlockInBlockEntity(pos);
            }

            List<Entity> entities = this.level().getEntities(this, area);
            for (Entity target : entities) {
                if (this.ownedBy(target)) continue;

                Vec3 pullVector = this.position().subtract(target.position()).normalize();
                target.setDeltaMovement(target.getDeltaMovement().add(pullVector.scale(attractionForce)));
                target.hasImpulse = true;
            }
        }
    }

    public Vec3 getPositionInLookDirection(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F);
        return eyePos.add(lookVec.scale(distance));
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Age", this.age);
        compound.putInt("Behavior", getBehavior().ordinal());
        compound.putDouble("Radius", this.radius);
        compound.putInt("MaxLife", this.maxLife);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.age = compound.getInt("Age");
        if (compound.contains("Behavior")) {
            this.setBehavior(ModEntityBehavior.values()[compound.getInt("Behavior")]);
        }
        this.radius = compound.getDouble("Radius");
        this.maxLife = compound.getInt("MaxLife");
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide()){
            if(result.getEntity() instanceof LivingEntity entity) {
                entity.hurt(this.level().damageSources().inWall(), 1.0f);
            } else if (!(result.getEntity() instanceof Player)) {
                result.getEntity().discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide()) {
            BlockPos pos = result.getBlockPos();
            if (!tryTurningBlockInBlockEntity(pos)) {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    protected boolean tryTurningBlockInBlockEntity(BlockPos pos) {
        float resistanceThreshold = 600.0F;
        BlockState state = this.level().getBlockState(pos);

        if (!state.isAir() && !state.liquid() && state.getDestroySpeed(this.level(), pos) >= 0) {
            if (state.getBlock().getExplosionResistance() < resistanceThreshold) {
                FallingBlockEntity fallingBlock = FallingBlockEntity.fall(this.level(), pos, state);
                fallingBlock.dropItem = false;
                this.level().removeBlock(pos, false);
                fallingBlock.setDeltaMovement(0, 0.1, 0);
                this.level().addFreshEntity(fallingBlock);
                return true;
            }
        }
        return false;
    }
}