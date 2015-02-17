package com.levicore.silvermoon.battle;

import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.states.BattleState;
import com.levicore.silvermoon.utils.comparators.SkillPriorityComparator;
import com.levicore.silvermoon.utils.comparators.StatComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leonard on 1/15/2015.
 */
public abstract class Behavior {

    protected BattleState battleState;
    protected BattleEntity battleEntity;

    protected List<Skill> normalSkills = new ArrayList<>();
    protected List<Skill> buffSkills = new ArrayList<>();
    protected List<Skill> healSkills = new ArrayList<>();
    protected List<Skill> specialSkills = new ArrayList<>();

    public Behavior(BattleState battleState, BattleEntity battleEntity) {
        this.battleState = battleState;
        this.battleEntity = battleEntity;
    }

    public abstract Skill selectSkill();
    public abstract void setTargets();

    public void storeSkills() {
        for(Skill skill : battleEntity.getSkills()) {
            switch (skill.getSkillType()) {
                case NORMAL:
                    normalSkills.add(skill);
                    break;
                case BUFF:
                    buffSkills.add(skill);
                    break;
                case HEAL:
                    healSkills.add(skill);
                    break;
                case SPECIAL:
                    specialSkills.add(skill);
                    break;
            }
        }

        SkillPriorityComparator skillPriorityComparator = new SkillPriorityComparator();

        normalSkills.sort(skillPriorityComparator);
        buffSkills.sort(skillPriorityComparator);
        healSkills.sort(skillPriorityComparator);
        specialSkills.sort(skillPriorityComparator);
    }

    public Skill getPrioritySkill(List<Skill> skills) {
        for(Skill skill : skills) {
            if(skill.canBeCasted(battleEntity)) {
                return skill;
            }
        }

        return null;
    }

    public BattleEntity getLowestHP() {
        Collections.sort(battleState.getAvailableTargets(), new StatComparator(StatComparator.Attribute.HP));
        return battleState.getAvailableTargets().get(battleState.getAvailableTargets().size() - 1);
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }
}
