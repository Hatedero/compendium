package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.*;

public class ReverseCursedTechniqueSpell extends Spell {
    public ReverseCursedTechniqueSpell(float costPerTick) {
        super(costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 100;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);
                player.heal((float) (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY))/20);
            }
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }
}
