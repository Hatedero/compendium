package com.hatedero.compendiummod.item.custom.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class ReverseCursedTechniqueSpellItem extends SpellItem{

    public ReverseCursedTechniqueSpellItem(Properties properties, float cooldown, int manaCost) {
        super(properties, cooldown, manaCost);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (manaCost * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);
                player.heal((float) (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * player.getAttributeValue(ModAttributes.MANA_EFFICIENCY))/20);
            }
            else {
                player.getCooldowns().addCooldown(stack.getItem(), (int) (player.getAttributeValue(ModAttributes.COOLDOWN_MULTIPLIER) * (cooldown/20.0)));
                player.releaseUsingItem();
            }
        }
    }
}
