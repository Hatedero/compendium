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
            PARTICLES.register("spark1", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK2 =
            PARTICLES.register("spark2", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK3 =
            PARTICLES.register("spark3", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_GIANT =
            PARTICLES.register("impact_giant", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_HIT1_LARGE =
            PARTICLES.register("impact_hit1_large", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_HIT2_LARGE =
            PARTICLES.register("impact_hit2_large", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_HIT_SPARK_LARGE =
            PARTICLES.register("impact_hit_spark_large", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_LARGE =
            PARTICLES.register("impact_large", LodestoneWorldParticleType::new);
    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_MASSIVE =
            PARTICLES.register("impact_massive", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_MINI =
            PARTICLES.register("impact_mini", LodestoneWorldParticleType::new);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> IMPACT_SMALL =
            PARTICLES.register("impact_small", LodestoneWorldParticleType::new);


    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}