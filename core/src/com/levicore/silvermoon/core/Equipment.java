package com.levicore.silvermoon.core;

import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;

/**
 * Created by user on 2/12/2015.
 */
public abstract class Equipment extends Item {

    // TODO FINISH THIS
    private BattleEntity battleEntity;
    private EQUIPMENT_TYPE equipment_type;

    public Equipment(BattleEntity battleEntity, EQUIPMENT_TYPE equipment_type, Entity icon, String name, String description) {
        super(icon, name, description);
        this.battleEntity = battleEntity;
        this.equipment_type = equipment_type;
    }

    public Equipment(Entity icon, String name, String description, BattleEntity battleEntity, EQUIPMENT_TYPE equipment_type) {
        super(icon, name, description);
        this.battleEntity = battleEntity;
        this.equipment_type = equipment_type;
    }

    public Equipment(Entity icon, String name, String description, int cost, BattleEntity battleEntity, EQUIPMENT_TYPE equipment_type) {
        super(icon, name, description, cost);
        this.battleEntity = battleEntity;
        this.equipment_type = equipment_type;
    }

    public abstract void equipEffect();
    public abstract void unequipEffect();

    public EQUIPMENT_TYPE getEquipment_type() {
        return equipment_type;
    }

    public static enum EQUIPMENT_TYPE {
        SWORD_SLOT, SHIELD_SLOT, BODY_SLOT, HEAD_SLOT, GLOVES_SLOT, BOOTS_SLOT,
    }

}
