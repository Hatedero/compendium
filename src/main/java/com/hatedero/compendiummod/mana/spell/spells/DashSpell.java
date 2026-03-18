package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class DashSpell extends Spell {
    protected double baseMultiplier;

    public DashSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, double baseMultiplier) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
        this.baseMultiplier = baseMultiplier;
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide()) {
            boolean groundedLaunch = player.onGround();
            float factor = 1.4F + (((float) player.getDeltaMovement().horizontalDistance()));
            if (!groundedLaunch)
                factor += 0.1F;
            factor += (float) ((manaLevel/100.f) * baseMultiplier);

            Vec3 playerSightLigne = player.getViewVector(1).normalize().multiply(factor, factor, factor);
            playerSightLigne = playerSightLigne.add(player.getDeltaMovement());
            player.setDeltaMovement(playerSightLigne);
            player.resetFallDistance();
            if (player instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }
}
