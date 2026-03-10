package com.hatedero.compendiummod.item.custom;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;

public class BootsModel extends HumanoidModel<LivingEntity> {
    private static final CubeDeformation BOOT_DEFORMATION = new CubeDeformation(0.04F);

    public BootsModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(BOOT_DEFORMATION, 0.0F);
        return LayerDefinition.create(mesh, 64, 32);
    }
}