package com.hatedero.compendiummod.item.custom;

import com.hatedero.compendiummod.event.MyModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BootsItem extends ArmorItem {

    public BootsItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                var model = new BootsModel(Minecraft.getInstance().getEntityModels().bakeLayer(MyModelLayers.BOOTS_LAYER));

                model.leftLeg.visible = (armorSlot == EquipmentSlot.FEET);
                model.rightLeg.visible = (armorSlot == EquipmentSlot.FEET);

                return model;
            }
        });
    }
}