package com.levicore.silvermoon.battle;

import aurelienribon.tweenengine.Timeline;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/12/2015.
 */
public abstract class Buff {

    public static final int AURA = -1;

    public int duration;
    public Entity icon;

    public Buff(Entity icon, int duration) {
        this.icon = icon;
        this.duration = duration;
    }

    /**
     * @param character - character affected
     * @return
     */
    public abstract Timeline executeTurnEffect(BattleEntity character);

}
