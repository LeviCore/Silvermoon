package com.levicore.silvermoon.core;

import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;

/**
 * Created by user on 2/12/2015.
 */
public abstract class Equipment extends Item {

    // TODO FINISH THIS
    protected BattleEntity user;
    private EQUIPMENT_TYPE equipment_type;

    /**
     * Constructor with 0 cost as default
     */
    public Equipment(Entity icon, String name, String description, BattleEntity user, EQUIPMENT_TYPE equipment_type) {
        super(icon, name, description);
        this.user = user;
        this.equipment_type = equipment_type;
    }

    /**
     * Constructor with cost
     */
    public Equipment(Entity icon, String name, String description, int cost, BattleEntity user, EQUIPMENT_TYPE equipment_type) {
        super(icon, name, description, cost);
        this.user = user;
        this.equipment_type = equipment_type;
    }

    public abstract void equipEffect();
    public abstract void unequipEffect();

    public EQUIPMENT_TYPE getEquipment_type() {
        return equipment_type;
    }

    public static enum EQUIPMENT_TYPE {
        WEAPON_SLOT, SHIELD_SLOT, BODY_SLOT, HEAD_SLOT, GLOVES_SLOT, BOOTS_SLOT,
    }

}
