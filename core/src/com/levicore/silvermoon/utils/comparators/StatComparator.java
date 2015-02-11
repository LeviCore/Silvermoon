package com.levicore.silvermoon.utils.comparators;

import com.levicore.silvermoon.entities.battle.BattleEntity;

import java.util.Comparator;

/**
 * Created by Leonard on 1/3/2015.
 */
public class StatComparator implements Comparator<BattleEntity> {

    public Attribute attribute;

    public StatComparator(Attribute attribute) {
        this.attribute = attribute;
    }

    public static enum Attribute {
        SPEED, HP
    }

    @Override
    public int compare(BattleEntity o1, BattleEntity o2) {
        switch (attribute) {
            case SPEED :
                return (int) (o2.speed - o1.speed);
            case HP :
                return (int) (o2.curHP - o1.curHP);
        }

        return 0;
    }
}
