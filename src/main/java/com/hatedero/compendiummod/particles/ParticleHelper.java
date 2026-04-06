package com.hatedero.compendiummod.particles;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;

import java.awt.Color;
import java.util.Random;

public class ParticleHelper {
    public static void spawnBasicParticle(Level level, Vec3 pos) {
        if (!level.isClientSide()) return;
        WorldParticleBuilder.create(LodestoneParticleTypes.STAR_PARTICLE)
                .setScaleData(GenericParticleData.create(0.5f, 0f))
                .setTransparencyData(GenericParticleData.create(1.0f, 1.0f).build())
                .spawn(level, pos.x, pos.y, pos.z);
    }

    public static void spawnImpactAt(Level level, Vec3 pos) {
        WorldParticleBuilder.create(ModParticles.IMPACT_MASSIVE.get())
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setScaleData(GenericParticleData.create(2.5f))
                .setColorData(ColorParticleData.create(Color.WHITE, Color.RED))
                .setTransparencyData(GenericParticleData.create(1f))
                .setLifetime(6)
                .enableNoClip()
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setFullBrightLighting()
                .spawn(level, pos);
    }

    public static void spawnRandomStarAt(Level level, Vec3 pos) {
        Random random = new Random();

        switch (random.nextInt(3)) {
            case 0 -> {
                WorldParticleBuilder.create(ModParticles.SPARK.get())
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setScaleData(GenericParticleData.create(random.nextFloat()))
                        .setTransparencyData(GenericParticleData.create(0.3f))
                        .setLifetime(6)
                        .setColorData(ColorParticleData.create(new Color(0xFF00ffd1)))
                        .enableNoClip()
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setFullBrightLighting()
                        .spawn(level, pos);
            }
            case 1 -> {
                WorldParticleBuilder.create(ModParticles.SPARK2.get())
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setScaleData(GenericParticleData.create(random.nextFloat()))
                        .setTransparencyData(GenericParticleData.create(0.3f))
                        .setColorData(ColorParticleData.create(new Color(0xFF00ffd1)))
                        .setLifetime(6)
                        .enableNoClip()
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setFullBrightLighting()
                        .spawn(level, pos);
            }
            case 2 -> {
                WorldParticleBuilder.create(ModParticles.SPARK3.get())
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setScaleData(GenericParticleData.create(random.nextFloat()))
                        .setTransparencyData(GenericParticleData.create(0.3f))
                        .setLifetime(7)
                        .setColorData(ColorParticleData.create(new Color(0xFF00ffd1)))
                        .enableNoClip()
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setFullBrightLighting()
                        .spawn(level, pos);
            }
        }


    }
}