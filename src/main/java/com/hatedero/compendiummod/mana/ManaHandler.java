package com.hatedero.compendiummod.mana;

import net.minecraft.world.entity.player.Player;

public class ManaHandler {
    /*public static void setMana(Player player, float newMana) {
        float oldMana = this.mana;
        this.mana = Math.max(0, Math.min(newMana, maxMana));

        float threshold = this.maxMana / 10.0f;

        int oldBucket = (int) (oldMana / threshold);
        int newBucket = (int) (newMana / threshold);

        if (newBucket < oldBucket) {
            int chunksLost = oldBucket - newBucket;
            this.onManaThresholdDropped(chunksLost);
        }
    }*/
}
