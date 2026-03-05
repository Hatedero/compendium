package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import static com.hatedero.compendiummod.mana.ModAttachments.*;

public class DashSpell extends Spell {
    public DashSpell(String name, float costPerTick) {
        super(name, costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 100;
    }

    @Override
    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
            boolean groundedLaunch = player.onGround();
            float factor = 1.4F + (((float) player.getDeltaMovement().horizontalDistance()));
            if (!groundedLaunch)
                factor += 0.1F;
            factor += (remainingUseDuration/100.f) * 1.5;

            Vec3 playerSightLigne = player.getViewVector(1).normalize().multiply(factor, factor, factor);
            playerSightLigne = playerSightLigne.add(player.getDeltaMovement());
            player.setDeltaMovement(playerSightLigne);
            player.resetFallDistance();
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
        super.release(level, livingEntity, remainingUseDuration);
    }

    @Override
    public boolean canUseMana(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
            if (player.getData(MANA) - cost >= 0 &&  player.getData(CHARGE_TIME) <= getUseDuration())
                return true;
        }
        return false;
    }
}
