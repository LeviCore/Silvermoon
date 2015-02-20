package com.levicore.silvermoon.battle.skills;

import aurelienribon.tweenengine.Timeline;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.presets.timelines.AnimationTimelines;
import com.levicore.silvermoon.states.BattleState;

import java.util.List;

/**
 * Created by Leonard on 1/3/2015.
 */
public class Attack extends Skill {

    @Override
    public Timeline execute(BattleState battleState, BattleEntity caster, List<BattleEntity> targets) {
        float originalX = caster.getX();
        float originalY = caster.getY();

        int side = battleState.getSideFacing(caster);

        Timeline timeline = Timeline.createSequence();
        timeline.push(caster.setAnimation(caster.getWalkingPose()));
        timeline.push(caster.moveTo(targets.get(0).getX() - (targets.get(0).getWidth() * side), targets.get(0).getY(), 1));

        timeline.push(
                Timeline.createParallel()
                        .push(Timeline.createSequence()
                                        .push(caster.setAnimation(caster.getMeleeAttackPose()))
                                        .pushPause(0.09f * 3)
                                        .push(battleState.setAttribute(BattleEntity.Attribute.HP, targets.get(0), -15))
                        )
                        .push(battleState.normalWeaponSwing(caster, null))
        );
        timeline.push(AnimationTimelines.SLASH(battleState, targets.get(0), targets.get(0).getX(), targets.get(0).getY()));

        timeline.push(caster.setAnimation(caster.getWalkingPose()));
        timeline.push(caster.moveTo(originalX, originalY, 1));
        timeline.push(caster.setAnimation(caster.getIdlePose()));


        return timeline;
    }

    @Override
    public String getName() {
        return "Attack";
    }

    @Override
    public int getTurnCost() {
        return 1;
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
        return TARGET_TYPE.SINGLE_ENEMY;
    }

    @Override
    public SKILL_TYPE getSkillType() {
        return SKILL_TYPE.NORMAL;
    }

    @Override
    public Entity getIcon() {
        return new Entity(Assets.ICONS_ATLAS.findRegion("skill_book"));
    }


}
