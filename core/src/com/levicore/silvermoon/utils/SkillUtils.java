package com.levicore.silvermoon.utils;

import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.battle.skills.Attack;
import com.levicore.silvermoon.battle.skills.Defend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/15/2015.
 */
public class SkillUtils {

    public static List<Skill> skills() {

        List<Skill> skills = new ArrayList<>();
        skills.add(new Attack());
        skills.add(new Defend());

        return skills;
    }

    public static int getSkillPriority(Skill skill) throws Exception {
        List<Skill> skills = skills();
        for (int i = 0; i < skills.size(); i++) {
            if(skills.get(i).getClass() == skill.getClass()) {
                return i;
            }
        }

        throw new Exception("Skill is not on the list");
    }

}
