package com.levicore.silvermoon.utils.comparators;

import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.utils.SkillUtils;

import java.util.Comparator;

/**
 * Created by Leonard on 1/15/2015.
 */
public class SkillPriorityComparator implements Comparator<Skill> {
    @Override
    public int compare(Skill o1, Skill o2) {
        try {
            return SkillUtils.getSkillPriority(o2) - SkillUtils.getSkillPriority(o1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
