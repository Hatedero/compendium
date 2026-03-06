package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.item.ModItems;
import com.hatedero.compendiummod.mana.ManaHudOverlay;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.util.KeyInputHandler;
import com.hatedero.compendiummod.util.ModKeybinds;
import com.ibm.icu.text.MessagePattern;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;
import static com.hatedero.compendiummod.particles.ParticleUtils.drawParticleCircle;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        handleShowMana(event);
        handleSpellCharging(event);
    }

    public static void handleShowMana(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

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

            drawParticleCircle(
                    player.level(),
                    pos,
                    radius,
                    dots,
                    0,
                    ParticleTypes.SMALL_FLAME,
                    new Vec3(0,0,0)
            );

            for(int i = 0; i < manaPercentage; i++) {
                if (player.tickCount % (i+1) == 0) {
                    drawParticleCircle(
                            player.level(),
                            pos,
                            radius * ((double) i / 10),
                            dots,
                            0.4 * ((double) i / 10),
                            ParticleTypes.SOUL_FIRE_FLAME,
                            new Vec3(0, (manaRegen/maxMana + level.random.nextFloat()) * 0.015 , 0)
                    );
                }
            }

        }
    }

    public static void handleSpellCharging(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

        Spell spell = getSpell(level, player.getData(CURRENT_SPELL_ID));

        if (player.getData(IS_CHARGING) && spell != null) {
            int playerChargeTime = player.getData(CHARGE_TIME);
            player.displayClientMessage(Component.literal("USING " + spell.getName() + " FOR - : " + playerChargeTime/20 + "s"), true);
            spell.chargeTick(level, player, playerChargeTime);
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();

        if (player.getData(IS_CHARGING)) {
            event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            event.getRenderer().getModel().leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            event.getRenderer().getModel().rightArm.offsetScale(new Vec3(1,5,3).toVector3f());
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        int cooldown = player.getData(CAST_COOLDOWN);
        //float maxCooldown = getSpell(player.level(), player.getData(CURRENT_SPELL_ID)).getCooldown();

        /*
        NOT ACCURATE AS PLAYER CAN CHANGE SPELL WHILE IN COOLDOWN
        But, doesn't matter since cooldown is gonna be on individual spell and not player cast
        */
        if (cooldown > 0) {
            float progress = Math.min(1.0f, cooldown / 5*20f);

            int barWidth = 50;
            int barHeight = 4;
            int x = (event.getGuiGraphics().guiWidth() - barWidth) / 2;
            int y = 10;

            event.getGuiGraphics().fill(x, y, x + barWidth, y + barHeight, 0xAA111111);

            int currentProgressWidth = (int) (barWidth * progress);
            event.getGuiGraphics().fill(x, y, x + currentProgressWidth, y + barHeight, 0xAAFFFFFF);

            event.getGuiGraphics().renderOutline(x - 1, y - 1, barWidth + 2, barHeight + 2, 0xAAFFFFFF);
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
        event.register(ModKeybinds.CHARGE_SPELL_KEY);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLUE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.RED_PROJECTILE.get(), NoopRenderer::new);
    }
}