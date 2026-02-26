package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.item.ModItems;
import com.hatedero.compendiummod.mana.ManaHudOverlay;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.util.ModKeybinds;
import com.ibm.icu.text.MessagePattern;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;
import static com.hatedero.compendiummod.mana.ModAttachments.SHOW_MANA;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

        if (!level.isClientSide) return;

        ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);
        if (player.getData(SHOW_MANA)) {

            double distance = 0.3;
            Vec3 look = player.getLookAngle();
            Vec3 forward = new Vec3(look.x, 0, look.z).normalize().scale(distance);

            float bodyYaw = player.yBodyRot;
            Direction bodyDirection = Direction.fromYRot(bodyYaw);
            Vec3 bodyVector = Vec3.directionFromRotation(0, bodyYaw);
            /*Vec3 forward = new Vec3(bodyVector.x, 0, bodyVector.z).normalize().scale(distance);*/

            double centerX = player.getX() + forward.x;
            double centerY = player.getY() + player.getEyeHeight() * 0.7;
            double centerZ = player.getZ() + forward.z;

            float yaw = player.getYRot();
            Vec3 right = Vec3.directionFromRotation(0, yaw + 90).normalize();
            Vec3 up = new Vec3(0, 1, 0);

            int points = (int) player.getAttributeValue(ModAttributes.MAX_MANA);
            double radius = 0.3;

            double mana = player.getData(MANA);

            if (mana == 0)
                return;

            List<SimpleParticleType> particles = List.of(ParticleTypes.FLAME, ParticleTypes.SOUL_FIRE_FLAME);
            double gap = player.getAttributeValue(ModAttributes.MAX_MANA)/particles.size();

            /*Map<Vec3, SimpleParticleType> directions = new HashMap<>();
            directions.put(new Vec3(1,1,0), ParticleTypes.FLAME);
            directions.put(new Vec3(-1,-1,0), ParticleTypes.SOUL_FIRE_FLAME);
            directions.put(new Vec3(1,-1,0), ParticleTypes.FLAME);
            directions.put(new Vec3(-1,1,0), ParticleTypes.SOUL_FIRE_FLAME);
            directions.put(new Vec3(0,0,1), ParticleTypes.END_ROD);
            float amp = 0.02f;

            directions.forEach((mov, part) -> {
                level.addParticle(
                        part,
                        centerX,
                        centerY,
                        centerZ,
                        mov.x * amp,
                        mov.y * amp,
                        mov.z * amp
                );
            });*/

            var i = player.tickCount%mana;

            for (int j = 0; j < particles.size(); j++) {
                var angle = (gap*j + i) * (2 * Math.PI / points);

                double offsetX = (Math.cos(angle) * radius * right.x);
                double offsetY = (Math.sin(angle) * radius * up.y);
                double offsetZ = (Math.cos(angle) * radius * right.z);

                level.addParticle(
                        particles.get(j),
                        centerX + offsetX,
                        centerY + offsetY,
                        centerZ + offsetZ,
                        0, 0, 0
                );
            }
        }
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "mana_overlay"),
                new ManaHudOverlay());
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.SHOW_MANA_ACTION_KEY);
    }
}