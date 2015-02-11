package com.levicore.silvermoon.battle;

import aurelienribon.tweenengine.Timeline;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.states.BattleState;

import java.util.List;

/**
 * Created by Leonard on 1/3/2015.
 */
public abstract class Skill {

    private int skillCooldown = 3;
    private int currentCooldown = 0;

    public abstract Timeline execute(BattleState battleState, BattleEntity caster, List<BattleEntity> targets);

    public abstract String getName();
    public abstract int getTurnCost();
    public abstract int getMpCost();
    public abstract int getHpCost();
    public abstract int getGoldCost();
    public abstract int getTpCost();

    public boolean isCooldown() {
        if(currentCooldown == 0) {
            return true;
        }

        return false;
    }

    public boolean canBeCasted(BattleEntity battleEntity) {
        if(isCooldown() && battleEntity.curHP > getHpCost() &&  battleEntity.curMP > getMpCost() &&  battleEntity.curTP > getTpCost()) {
            return true;
        }

        return false;
    }

    public abstract Entity getIcon();
    public abstract TARGET_TYPE getTargetType();
    public abstract SKILL_TYPE getSkillType();

    public void resetCooldown() {
        currentCooldown = skillCooldown;
    }

    public static enum TARGET_TYPE {
        SELF, SINGLE_ENEMY, ALL_ENEMIES, SINGLE_ALLY, ALL_ALLIES, ALL
    }

    public static enum SKILL_TYPE {
        NORMAL, BUFF, HEAL, SPECIAL, CUT_SCENE
    }

}