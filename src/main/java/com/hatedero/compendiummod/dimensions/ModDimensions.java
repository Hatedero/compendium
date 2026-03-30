package com.hatedero.compendiummod.dimensions;

import com.hatedero.compendiummod.CompendiumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> ABYSS_KEY = ResourceKey.create(
        Registries.DIMENSION,
        ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "abyss")
    );

    public static final ResourceKey<DimensionType> ABYSS_TYPE_KEY = ResourceKey.create(
        Registries.DIMENSION_TYPE, 
        ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "abyss_type")
    );
}