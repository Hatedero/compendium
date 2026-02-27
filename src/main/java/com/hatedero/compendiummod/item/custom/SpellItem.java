package com.hatedero.compendiummod.item.custom;

import com.hatedero.compendiummod.mana.ModAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.Random;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class SpellItem extends Item {
    public SpellItem(Properties properties) {
        super(properties);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player)
            return (int) player.getAttributeValue(ModAttributes.MAX_MANA) * 20;
        return 20;
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
            double cost = (1 * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT))))/20;
            if (player.getData(MANA) - cost >= 0)
                player.setData(MANA, player.getData(MANA) - cost);
            else {
                player.getCooldowns().addCooldown(stack.getItem(), (int) player.getAttributeValue(ModAttributes.COOLDOWN_MULTIPLIER) * 20);
                player.releaseUsingItem();

            }
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (level.isClientSide && livingEntity instanceof Player player) {
            player.displayClientMessage(Component.literal("CHARGED FOR " + (getUseDuration(stack, player) - timeCharged)/20.0 + "s"), true);
        }
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }
}
