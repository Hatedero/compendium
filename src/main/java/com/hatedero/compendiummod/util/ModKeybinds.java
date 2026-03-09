package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.IEventBus;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ModKeybinds {
    public static final String KEY_CATEGORY = "key.categories."+ CompendiumMod.MODID;

    public static final KeyMapping SHOW_MANA_ACTION_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".show_mana_action",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY
    );

    public static final KeyMapping CHARGE_SPELL_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".charge_spell",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
    );

    public static final KeyMapping OPEN_SPELL_MENU = new KeyMapping(
            "key."+CompendiumMod.MODID+".open_spell_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );

    public static final KeyMapping ULTIMATE_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".ultimate",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
    );

    public static final KeyMapping ABILITY_ONE_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".ability_one",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
    );

    public static final KeyMapping ABILITY_TWO_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".ability_two",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
    );

    public static final KeyMapping ABILITY_THREE_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".ability_three",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
    );
}
