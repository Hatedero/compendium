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

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK =
            PARTICLES.register("spark1", () -> new LodestoneWorldParticleType());

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK2 =
            PARTICLES.register("spark2", () -> new LodestoneWorldParticleType());

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK3 =
            PARTICLES.register("spark3", () -> new LodestoneWorldParticleType());


    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}