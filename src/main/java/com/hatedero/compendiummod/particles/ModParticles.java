package com.hatedero.compendiummod.particles;

import com.hatedero.compendiummod.CompendiumMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.screen.LodestoneScreenParticle;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, CompendiumMod.MODID);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> RED_SPARK =
            PARTICLES.register("red_spark", () -> new LodestoneWorldParticleType());


    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}