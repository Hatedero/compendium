package com.hatedero.compendiummod.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

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
}
