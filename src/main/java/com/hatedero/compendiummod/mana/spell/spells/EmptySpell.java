package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class EmptySpell extends Spell {
    public EmptySpell(float costPerTick) {
        super(costPerTick);
    }

}
