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
import static com.hatedero.compendiummod.particles.ParticleUtils.drawParticleCircle;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

        if (!level.isClientSide) return;

        if (player.getData(SHOW_MANA)) {
            double mana = player.getData(MANA);
            double maxMana = player.getAttributeValue(ModAttributes.MAX_MANA);

            if (maxMana <= 0)
                    return;

            double manaRegen = player.getAttributeValue(ModAttributes.MANA_REGEN);

            int manaPercentage = (int) (mana/maxMana * 10);

            Vec3 pos = player.position();

            double radius = (maxMana*0.1)/2.0;
            int dots = 180;

            //DRAW MAX MANA BOUNDARY
            drawParticleCircle(
                    player.level(),
                    pos,
                    radius,
                    dots,
                    0,
                    ParticleTypes.SMALL_FLAME,
                    new Vec3(0,0,0)
            );

            //DRAW MANA PERCENTAGE
            for(int i = 0; i < manaPercentage; i++) {
                if (player.tickCount % (i+1) == 0) {
                    drawParticleCircle(
                            player.level(),
                            pos,
                            radius * ((double) i / 10),
                            dots,
                            0.4 * ((double) i / 10),
                            ParticleTypes.CHERRY_LEAVES,
                            new Vec3(0, (manaRegen/maxMana + level.random.nextFloat()) * 0.015 , 0)
                    );
                }
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