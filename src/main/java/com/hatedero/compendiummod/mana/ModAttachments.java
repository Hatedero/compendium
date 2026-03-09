package com.hatedero.compendiummod.mana;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.packets.*;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CompendiumMod.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> MANA = ATTACHMENT_TYPES.register(
            "mana", () -> AttachmentType.builder(() -> 0.0)
                    .serialize(Codec.DOUBLE)
                    .sync(new ManaSyncHandler())
                    .copyOnDeath()
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SHOW_MANA = ATTACHMENT_TYPES.register(
            "show_mana", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .sync(new ShowManaSyncHandler())
                    .copyOnDeath()
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<String>> CURRENT_SPELL_ID = ATTACHMENT_TYPES.register(
            "current_spell_id", () -> AttachmentType.builder(() -> "")
                    .serialize(Codec.STRING)
                    .sync(new CurrentSpellIdSyncHandler())
                    .copyOnDeath()
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CHARGE_TIME = ATTACHMENT_TYPES.register(
            "charge_time", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .sync(new ChargeTimeSyncHandler())
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IS_CHARGING = ATTACHMENT_TYPES.register(
            "is_charging", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .sync(new IsChargingSyncHandler())
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CAST_COOLDOWN = ATTACHMENT_TYPES.register(
            "cast_cooldown", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .sync(new CastCooldownSyncHandler())
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CHARGING_SPELL_ID = ATTACHMENT_TYPES.register(
            "charging_spell_id", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .sync(new ChargingSpellIdSyncHandler())
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
