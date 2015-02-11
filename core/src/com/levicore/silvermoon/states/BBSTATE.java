package com.levicore.silvermoon.states;

import com.levicore.silvermoon.entities.battle.BattleEntity;

import java.util.List;

/**
 * Created by user on 2/9/2015.
 */
public class BBSTATE {

    private List<BattleEntity> a;
    private List<BattleEntity> b;

    private List<BattleEntity> battlers;

    public BBSTATE() {
        //init
    }

    void update() {

    }

    enum PHASE {
        TURN_START, ACTION_SELECTION, TARGET_SELECTION, EXECUTION, TURN_END
    }

}
