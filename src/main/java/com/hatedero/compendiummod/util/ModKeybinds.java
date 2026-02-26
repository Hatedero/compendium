package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final String KEY_CATEGORY = "key.categories."+ CompendiumMod.MODID;

    public static final KeyMapping SHOW_MANA_ACTION_KEY = new KeyMapping(
            "key."+CompendiumMod.MODID+".show_mana_action",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY
    );


}
