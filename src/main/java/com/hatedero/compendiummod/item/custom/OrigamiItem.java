package com.hatedero.compendiummod.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class OrigamiItem extends Item {
    public OrigamiItem(Properties properties) {
        super(properties);
    }

    public void RandomizeAttribute(ItemStack stack) {
        float randomSpeed = 20f;
        float speedModifier = randomSpeed - 4.0f;

        float baseDamage = 10.0f;

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
    }
}
