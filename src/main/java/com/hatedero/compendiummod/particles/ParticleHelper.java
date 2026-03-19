package com.hatedero.compendiummod.particles;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;

import java.awt.Color;

public class ParticleHelper {
    public static void spawnBasicParticle(Level level, Vec3 pos) {
        if (!level.isClientSide()) return;
        WorldParticleBuilder.create(LodestoneParticleTypes.STAR_PARTICLE)
                .setScaleData(GenericParticleData.create(0.5f, 0f).build())
                .setTransparencyData(GenericParticleData.create(1.0f, 1.0f).build())
                .spawn(level, pos.x, pos.y, pos.z);

        WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(1.0f, 0.0f).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.0f).build())
                .setLifetime(30)
                .spawn(level, pos.x, pos.y, pos.z);
    }
}