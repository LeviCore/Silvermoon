package com.levicore.silvermoon.battle.skills;

import aurelienribon.tweenengine.Timeline;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.states.BattleState;

import java.util.List;

/**
 * Created by Leonard on 1/10/2015.
 */
public class Defend extends Skill {

    @Override
    public Timeline execute(BattleState battleState, BattleEntity caster, List<BattleEntity> targets) {
        Timeline timeline = Timeline.createSequence();
        timeline.push(caster.setAnimation(caster.getDefendPose()));
        timeline.pushPause(1);

        return timeline;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public int getTurnCost() {
        return 0;
    }

    @Override
    public int getMpCost() {
        return 0;
    }

    @Override
    public int getHpCost() {
        return 0;
    }

    @Override
    public int getGoldCost() {
        return 0;
    }

    @Override
    public int getTpCost() {
        return 0;
    }

    @Override
    public TARGET_TYPE getTargetType() {
        return TARGET_TYPE.SELF;
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return SKILL_TYPE.BUFF;
    }

    @Override
    public Entity getIcon() {
        return new Entity(Assets.ICONS_ATLAS.findRegion("shield"));
    }

}
