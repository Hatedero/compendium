package com.hatedero.compendiummod.mana.spell;

import java.util.Map;

public abstract class Spell {
    protected float costPerTick;

    public abstract boolean canStart ();

    public abstract void chargeTick ();

    public abstract void release ();
}
