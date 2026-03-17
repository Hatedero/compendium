package com.hatedero.compendiummod.network;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdateServerPayloadHandler;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import com.hatedero.compendiummod.network.SpellData.SpellDataUpdateServerPayloadHandler;
import com.hatedero.compendiummod.network.SpellDataActiveSLot.SpellDataActiveSlotUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.SpellDataActiveSLot.SpellDataActiveSlotUpdatePayload;
import com.hatedero.compendiummod.network.SpellDataActiveSLot.SpellDataActiveSlotUpdateServerPayloadHandler;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdateServerPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                IsChargingUpdatePayload.TYPE,
                IsChargingUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        IsChargingUpdateClientPayloadHandler::handleDataOnMain,
                        IsChargingUpdateServerPayloadHandler::handleDataOnMain
                )
        );
        registrar.playBidirectional(
                CurrentSpellIdUpdatePayload.TYPE,
                CurrentSpellIdUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        CurrentSpellIdUpdateClientPayloadHandler::handleDataOnMain,
                        CurrentSpellIdUpdateServerPayloadHandler::handleDataOnMain
                )
        );
        registrar.playBidirectional(
                SpellDataUpdatePayload.TYPE,
                SpellDataUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SpellDataUpdateClientPayloadHandler::handleDataOnMain,
                        SpellDataUpdateServerPayloadHandler::handleDataOnMain
                )
        );
        registrar.playBidirectional(
                SpellDataActiveSlotUpdatePayload.TYPE,
                SpellDataActiveSlotUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SpellDataActiveSlotUpdateClientPayloadHandler::handleDataOnMain,
                        SpellDataActiveSlotUpdateServerPayloadHandler::handleDataOnMain
                )
        );
    }
}