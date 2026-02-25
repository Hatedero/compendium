package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(
                EntityType.PLAYER,
                ModAttributes.MAX_MANA
        );
        event.add(
                EntityType.PLAYER,
                ModAttributes.MANA_REGEN
        );
    }
}
