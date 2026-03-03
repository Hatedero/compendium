package com.hatedero.compendiummod.animation;

import com.hatedero.compendiummod.CompendiumMod;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranim.neoforge.event.PlayerAnimationRegisterEvent;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;

public class ModAnimations {
    public static ResourceLocation ASCEND =
            ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "ascend");

    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "ascend"), 1000,
    player -> new PlayerAnimationController(player,
                (controller, state, animSetter) -> PlayState.STOP
        )
                );
}
