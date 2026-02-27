package com.hatedero.compendiummod.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {
    public static void drawParticleCircle(Level level, Vec3 center, double radius, int density, double jitter, ParticleOptions particle, Vec3 movement) {
        var random = level.getRandom();

        for (int i = 0; i < density; i++) {
            double angle = 2 * Math.PI * i / density;

            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);

            double rx = (random.nextDouble() - 0.5) * 2 * jitter;
            double rz = (random.nextDouble() - 0.5) * 2 * jitter;

            level.addParticle(particle,
                    center.x + xOffset + rx,
                    center.y + 0.2,
                    center.z + zOffset + rz,
                    movement.x, movement.y, movement.z);
        }
    }
}