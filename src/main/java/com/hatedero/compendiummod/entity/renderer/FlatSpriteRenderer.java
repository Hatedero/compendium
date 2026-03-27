package com.hatedero.compendiummod.entity.renderer;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.entity.BlueProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FlatSpriteRenderer extends EntityRenderer<BlueProjectile> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "textures/entity/blue.png");

    public FlatSpriteRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BlueProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        
        poseStack.scale(1.0f, 1.0f, 1.0f);

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        PoseStack.Pose lastPose = poseStack.last();

        drawVertex(lastPose, consumer, -0.5f, -0.5f, 0, 0, 1, packedLight);
        drawVertex(lastPose, consumer, 0.5f, -0.5f, 0, 1, 1, packedLight);
        drawVertex(lastPose, consumer, 0.5f, 0.5f, 0, 1, 0, packedLight);
        drawVertex(lastPose, consumer, -0.5f, 0.5f, 0, 0, 0, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlueProjectile blueProjectile) {
        return TEXTURE;
    }

    private void drawVertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 1, 0);
    }
}