package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.advancements.critereon.FallAfterExplosionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.ModAttachments.IS_CHARGING;

public class DismantleSpell extends Spell {
    public DismantleSpell(String name, float costPerTick) {
        super(name, costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 2000;
    }

    @Override
    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            HitResult target = getPlayerLookingAt(player, 100);
            switch(target.getType()) {
                case HitResult.Type.MISS -> {

                }
                case HitResult.Type.ENTITY -> {
                    EntityHitResult entityHit = (EntityHitResult) target;
                    Entity entity = entityHit.getEntity();
                    if (entity instanceof LivingEntity lEntity) {
                        lEntity.hurt(level.damageSources().playerAttack(player), 3);
                        lEntity.knockback(remainingUseDuration/20.F, -player.getLookAngle().x, -player.getLookAngle().z);
                    }
                }
                case HitResult.Type.BLOCK -> {
                    BlockHitResult blockHit = (BlockHitResult) target;
                    BlockPos pos = blockHit.getBlockPos();
                    player.sendSystemMessage(Component.literal(pos.toString()));
                    level.destroyBlock(pos, false);
                }
            }
        }
        if (!level.isClientSide()) {
            livingEntity.setData(CAST_COOLDOWN, 20);
            livingEntity.setData(IS_CHARGING, false);
            livingEntity.setData(CHARGE_TIME, 0);
        }
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

    public HitResult getPlayerLookingAt(Player player, double range) {
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition();
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 reachVec = eyePos.add(viewVec.scale(range));

        BlockHitResult blockHit = level.clip(new ClipContext(
                eyePos, reachVec, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        double effectiveRange = blockHit.getType() != HitResult.Type.MISS ?
                blockHit.getLocation().distanceTo(eyePos) : range;

        AABB box = player.getBoundingBox().expandTowards(viewVec.scale(range)).inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                eyePos,
                eyePos.add(viewVec.scale(effectiveRange)),
                box,
                (entity) -> !entity.isSpectator() && entity.isPickable(),
                effectiveRange * effectiveRange
        );

        return (entityHit != null) ? entityHit : blockHit;
    }
}
