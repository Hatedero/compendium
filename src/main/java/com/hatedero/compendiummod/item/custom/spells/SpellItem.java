package com.hatedero.compendiummod.item.custom.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class SpellItem extends Item {
    protected float cooldown; //after one use, second
    protected int manaCost; //cost per second

    public SpellItem(Properties properties, float cooldown, int manaCost) {
        super(properties);
        this.cooldown = cooldown;
        this.manaCost = manaCost;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (manaCost * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);
            }
            else {
                player.releaseUsingItem();
            }
        }
    }

    public boolean canUseMana(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            double cost = (manaCost * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
            if (player.getData(MANA) - cost >= 0)
                return true;
        }
        return false;
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        if (entity instanceof Player player && entity.level().isClientSide) {
            player.displayClientMessage(Component.literal("CHARGED FOR " + (getUseDuration(stack, player) - count)/20.0 + "s"), true);
        }
        if (entity instanceof Player player && !entity.level().isClientSide)
            player.getCooldowns().addCooldown(stack.getItem(), (int) ((cooldown*20.0)/player.getAttributeValue(ModAttributes.COOLDOWN_MULTIPLIER)));

        super.onStopUsing(stack, entity, count);
    }
}
