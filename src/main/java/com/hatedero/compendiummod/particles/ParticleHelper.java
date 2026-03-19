package com.hatedero.compendiummod.particles;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import java.awt.Color;

public class ParticleHelper {
    public static void spawnBasicParticle(Level level, Vec3 pos) {
        if (!level.isClientSide()) return;
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("SPAWNING BASIC PARTICLE"));
        WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
            .setColorData(ColorParticleData.create(Color.CYAN, Color.BLUE).build())
            
            .setTransparencyData(GenericParticleData.create(0.5f, 0f).build())
            
            .setScaleData(GenericParticleData.create(0.2f, 0.5f).build())
            
            .setLifetime(20)
            
            .spawn(level, pos.x, pos.y, pos.z);
    }
}