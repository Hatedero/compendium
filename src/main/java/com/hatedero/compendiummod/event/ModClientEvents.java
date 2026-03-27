package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.entity.renderer.FlatSpriteRenderer;
import com.hatedero.compendiummod.item.custom.BootsModel;
import com.hatedero.compendiummod.item.custom.MaskModel;
import com.hatedero.compendiummod.item.custom.MyModelLayers;
import com.hatedero.compendiummod.mana.GUI.ManaHudOverlay;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spells.EmptySpell;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import com.hatedero.compendiummod.particles.ModParticles;
import com.hatedero.compendiummod.particles.ParticleHelper;
import com.hatedero.compendiummod.util.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;


import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        handleSpellCharging(event);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MyModelLayers.MASK_LAYER, MaskModel::createLayer);
        event.registerLayerDefinition(MyModelLayers.BOOTS_LAYER, BootsModel::createLayer);
    }

    public static void handleSpellCharging(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Level level = player.level();

        if(!level.isClientSide()) return;

        PlayerSpellData data = player.getData(SPELL_DATA);

        if(!data.chargingSlotName().isEmpty()) {
            SpellSlotData slot = data.slots().stream()
                    .filter(s -> s.slotName().equals(data.chargingSlotName()))
                    .findFirst()
                    .orElse(null);
            if (slot == null) {
                player.displayClientMessage(Component.literal(player.getData(SPELL_DATA).chargingSlotName()), true);
                return;
            }
            Spell spell = getSpell(level, slot.spellId());

            if (spell == null || spell instanceof EmptySpell) return;

            String translationKey = "spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey();
            player.displayClientMessage(Component.literal("CHARGING ").append(Component.translatable(translationKey)).append(Component.literal(" : " + slot.chargeLevel())), true);

            if (player.tickCount%20 == 0) {
                WorldParticleBuilder.create(ModParticles.RED_SPARK.get())
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setScaleData(GenericParticleData.create(2f))
                        .setTransparencyData(GenericParticleData.create(1.0f))
                        .setLifetime(20)
                        .enableNoClip()
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setFullBrightLighting()
                        .spawn(level, getPointInFront(player, 1));
                player.sendSystemMessage(Component.literal("Should spawn particle"));
            }
        }
    }

    public static Vec3 getPointInFront(LivingEntity entity, double distance) {
        Vec3 eyePos = entity.getEyePosition();

        Vec3 lookDir = entity.getLookAngle();

        return eyePos.add(lookDir.scale(distance));
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.RED_SPARK.get(), LodestoneWorldParticleType.Factory::new);
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "mana_overlay"),
                new ManaHudOverlay());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLUE_PROJECTILE.get(), FlatSpriteRenderer::new);
        event.registerEntityRenderer(ModEntities.RED_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.MANA_BOLT_PROJECTILE.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.OPEN_SPELL_MENU);
        event.register(ModKeybinds.ULTIMATE_KEY);
        event.register(ModKeybinds.ABILITY_ONE_KEY);
        event.register(ModKeybinds.ABILITY_TWO_KEY);
        event.register(ModKeybinds.ABILITY_THREE_KEY);
    }
}