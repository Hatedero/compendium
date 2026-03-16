package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.entity.ModEntities;
import com.hatedero.compendiummod.item.custom.BootsModel;
import com.hatedero.compendiummod.item.custom.MaskModel;
import com.hatedero.compendiummod.item.custom.MyModelLayers;
import com.hatedero.compendiummod.mana.GUI.ManaHudOverlay;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import com.hatedero.compendiummod.util.ModKeybinds;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

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

        PlayerSpellData data = player.getData(SPELL_DATA);

        if(!data.chargingSlotName().isEmpty()) {
            SpellSlotData slot = data.slots().stream()
                    .filter(s -> s.slotName().equals(data.chargingSlotName()))
                    .findFirst()
                    .orElse(null);
            assert slot != null;
            Spell spell = getSpell(level, slot.spellId());

            String translationKey = "spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey();
            player.displayClientMessage(Component.literal("CHARGED ").append(Component.translatable(translationKey)).append(Component.literal(" WITH : " + slot.chargeLevel() )), true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();

        if (player.getData(IS_CHARGING)) {
            event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            event.getRenderer().getModel().leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "mana_overlay"),
                new ManaHudOverlay());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLUE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.RED_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.MANA_BOLT_PROJECTILE.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        //event.register(ModKeybinds.CHARGE_SPELL_KEY);
        event.register(ModKeybinds.OPEN_SPELL_MENU);
        event.register(ModKeybinds.ULTIMATE_KEY);
        event.register(ModKeybinds.ABILITY_ONE_KEY);
        event.register(ModKeybinds.ABILITY_TWO_KEY);
        event.register(ModKeybinds.ABILITY_THREE_KEY);
    }
}