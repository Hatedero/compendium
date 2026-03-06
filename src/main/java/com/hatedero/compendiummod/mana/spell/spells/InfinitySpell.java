package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class InfinitySpell extends Spell {
    float range;

    public InfinitySpell(float costPerTick, float range) {
        super(costPerTick);
        this.range = range;
    }

    @Override
    public int getUseDuration() {
        return 2000;
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);

                AABB area = player.getBoundingBox().inflate(range);

                List<Entity> entities = level.getEntities(player, area);

                for (Entity target : entities) {
                    if (target instanceof Mob mob) {
                        mob.setNoAi(true);
                    } else if (target instanceof Projectile projectile) {
                        projectile.setDeltaMovement(Vec3.ZERO);
                        projectile.setNoGravity(true);
                    }
                }
            }
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }
}
