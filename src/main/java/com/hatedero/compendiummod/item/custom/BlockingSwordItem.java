package com.hatedero.compendiummod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class BlockingSwordItem extends TieredItem {

    public BlockingSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide) player.sendSystemMessage(Component.literal("USE"));
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if(level.isClientSide && livingEntity instanceof Player) livingEntity.sendSystemMessage(Component.literal("USED FOR " + timeCharged));
        if (!level.isClientSide && livingEntity instanceof Player) {
            stack.hurtAndBreak(1, livingEntity, LivingEntity.getSlotForHand(livingEntity.getUsedItemHand()));

            float randomSpeed = (float) (20f * Math.random());

            float speedModifier = randomSpeed - 4.0f;

            float baseDamage = (float) (10000f * Math.random());

            ItemAttributeModifiers newModifiers = ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(
                                    ResourceLocation.withDefaultNamespace("base_attack_damage"),
                                    (double)baseDamage,
                                    AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)

                    .add(Attributes.ATTACK_SPEED,
                            new AttributeModifier(
                                    ResourceLocation.withDefaultNamespace("base_attack_speed"),
                                    (double)speedModifier,
                                    AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .build();

            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newModifiers);

            stack.getAttributeModifiers().modifiers().forEach((modifier) -> {
                        livingEntity.sendSystemMessage(Component.literal(modifier.attribute().getRegisteredName()));
                    }
            );
        }
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemTags.PLANKS) || super.isValidRepairItem(toRepair, repair);
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility) || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, int attackDamage, float attackSpeed) {
        return createAttributes(tier, (float)attackDamage, attackSpeed);
    }

    public static ItemAttributeModifiers createAttributes(Tier p_330371_, float p_331976_, float p_332104_) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double)(p_331976_ + p_330371_.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)p_332104_, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }
}
