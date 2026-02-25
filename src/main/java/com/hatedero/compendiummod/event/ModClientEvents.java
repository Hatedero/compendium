package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

        if (!level.isClientSide) return;

        ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestSlot.is(ModItems.STARFIRE_PROTOCOL)) {

            double distance = 0.3;
            Vec3 look = player.getLookAngle();
            Vec3 forward = new Vec3(look.x, 0, look.z).normalize().scale(distance);

            double centerX = player.getX() + forward.x;
            double centerY = player.getY() + player.getEyeHeight() * 0.7;
            double centerZ = player.getZ() + forward.z;

            float yaw = player.getYRot();
            Vec3 right = Vec3.directionFromRotation(0, yaw + 90).normalize();
            Vec3 up = new Vec3(0, 1, 0);

            int points = 30;
            double radius = 1.5;

            var i = player.tickCount%30;
                double angle = i * (2 * Math.PI / points);

                double offsetX = (Math.cos(angle) * radius * right.x);
                double offsetY = (Math.sin(angle) * radius * up.y);
                double offsetZ = (Math.cos(angle) * radius * right.z);

                level.addParticle(
                        ParticleTypes.SOUL_FIRE_FLAME,
                        centerX + offsetX,
                        centerY + offsetY,
                        centerZ + offsetZ,
                        0, 0, 0
                );
        }
    }
}