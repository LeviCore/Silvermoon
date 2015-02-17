package com.levicore.silvermoon.battle.behaviors;

import com.levicore.silvermoon.battle.Behavior;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.states.BattleState;

/**
 * Created by Leonard on 1/15/2015.
 */
public class Normal extends Behavior {

    // TODO Fix this
    public Normal(BattleState battleStage, BattleEntity battleEntity) {
        super(battleStage, battleEntity);
    }

    @Override
    public Skill selectSkill() {

        for(Skill skill : battleEntity.getSkills()) {
            if(skill.getSkillType() == Skill.SKILL_TYPE.HEAL) {
                healSkills.add(skill);
            }
        }

        for(BattleEntity party : battleState.getParty(battleEntity)) {
            if(party.getPercentage(BattleEntity.Attribute.HP) < 50) {
                if(!healSkills.isEmpty()) return getPrioritySkill(healSkills);
            }
        }

        return battleEntity.getSkills().get(0);
    }

    @Override
    public void setTargets() {
        switch (battleState.getSkillSelected().getSkillType()) {
            case NORMAL :
            case HEAL :
                switch (battleState.getSkillSelected().getTargetType()) {
                    case SINGLE_ENEMY :
                    case SINGLE_ALLY :
                        battleState.getTargetsSelected().add(battleState.getAvailableTargets().get(0));
                        break;
                }
                break;
        }
    }

}
