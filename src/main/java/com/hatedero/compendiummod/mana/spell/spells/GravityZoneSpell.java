package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;
import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class GravityZoneSpell extends Spell {
    float strength;
    float range;
    boolean includePlayer;

    public GravityZoneSpell(float costPerTick, float strength, float range, boolean includePlayer) {
        super(costPerTick);
        this.strength = strength;
        this.range = range;
        this.includePlayer = includePlayer;
    }

    @Override
    public int getUseDuration() {
        return 200;
    }

    @Override
    public void chargeTick(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (canUseMana(livingEntity)) {
                double cost = (costPerTick * (player.getAttributeValue(ModAttributes.MANA_OUTPUT) * (player.getAttributeValue(ModAttributes.MANA_OUTPUT)))) / 20;
                player.setData(MANA, player.getData(MANA) - cost);

                AABB area = player.getBoundingBox().inflate(range);

                List<Entity> entities = level.getEntities(player, area);

                if(includePlayer)
                    entities.add(player);

                for (Entity target : entities) {
                    target.setDeltaMovement(target.getDeltaMovement().x,-strength,target.getDeltaMovement().z);
                }
            }
            else {
                release(level, livingEntity, remainingUseDuration);
            }
        }
    }
}
