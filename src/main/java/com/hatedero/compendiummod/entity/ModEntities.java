package com.hatedero.compendiummod.entity;

import com.hatedero.compendiummod.CompendiumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, CompendiumMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BlueProjectile>> BLUE_PROJECTILE =
            ENTITIES.register("blue_projectile", () -> EntityType.Builder.<BlueProjectile>of(BlueProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("blue_projectile"));

    public static final DeferredHolder<EntityType<?>, EntityType<RedProjectile>> RED_PROJECTILE =
            ENTITIES.register("red_projectile", () -> EntityType.Builder.<RedProjectile>of(RedProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("red_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
