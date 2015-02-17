package com.levicore.silvermoon.presets.equipments;

import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;

/**
* Created by user on 2/17/2015.
*/
public class Sword extends Equipment {

    public Sword(BattleEntity user) {
        super(new Entity(Assets.ICONS_ATLAS.findRegion("sword")), "Short Sword", "A short sword. Wow, Atk + 5", 50, user, EQUIPMENT_TYPE.WEAPON_SLOT);
    }

    @Override
    public void equipEffect() {
        user.setAtk(user.getAtk() + 5);
    }

    @Override
    public void unequipEffect() {
        user.setAtk(user.getAtk() - 5);
    }
}
