package com.hatedero.compendiummod.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class SpaceHelper {
    public static Vec3 getPointInFrontWithRandomOffset(LivingEntity entity, double distance, double minOffset, double maxOffset) {
        RandomSource random = entity.getRandom();
        Vec3 forward = entity.getLookAngle().normalize();

        Vec3 right = forward.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 actualUp = right.cross(forward).normalize();

        double horizontalOffset = minOffset + (random.nextDouble() * (maxOffset - minOffset));
        double verticalOffset = minOffset + (random.nextDouble() * (maxOffset - minOffset));

        Vec3 centerPoint = entity.getEyePosition().add(forward.scale(distance));
        return centerPoint.add(right.scale(horizontalOffset)).add(actualUp.scale(verticalOffset));
    }

    public static Vec3 getPointInFront(LivingEntity entity, double distance) {
        Vec3 eyePos = entity.getEyePosition();

        Vec3 lookDir = entity.getLookAngle();

        return eyePos.add(lookDir.scale(distance));
    }

    public static HitResult getPlayerLookingAt(Player player, double range) {
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition();
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 reachVec = eyePos.add(viewVec.scale(range));

        BlockHitResult blockHit = level.clip(new ClipContext(
                eyePos, reachVec, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        double effectiveRange = blockHit.getType() != HitResult.Type.MISS ?
                blockHit.getLocation().distanceTo(eyePos) : range;

        AABB box = player.getBoundingBox().expandTowards(viewVec.scale(range)).inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                eyePos,
                eyePos.add(viewVec.scale(effectiveRange)),
                box,
                (entity) -> !entity.isSpectator() && entity.isPickable(),
                effectiveRange * effectiveRange
        );

        return (entityHit != null) ? entityHit : blockHit;
    }


}
