package com.hatedero.compendiummod.datagen;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CompendiumMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.OLD_SPEAR_HEAD.get());
        basicItem(ModItems.OLD_SPEAR_SHAFT.get());
        basicItem(ModItems.OLD_SPEAR_SCARF.get());
        basicItem(ModItems.DAWNBREAKER_HILT.get());
        basicItem(ModItems.DAWNBREAKER_BLADE.get());
        basicItem(ModItems.DAWNBREAKER_TIP.get());
        armorItem(ModItems.STARFIRE_PROTOCOL);
    }

    private void armorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = CompendiumMod.MODID;

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(CompendiumMod.MODID  + ":item/" + armorItem.getDescriptionId())).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
        }
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID,"item/" + item.getId().getPath()));
    }
}