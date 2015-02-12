package com.levicore.silvermoon.core;

import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by user on 2/12/2015.
 */
public class Consumable extends Item {

    private int quantity;
    private Skill consumableSkill;

    public Consumable(Entity icon, String name, String description, int quantity, Skill consumableSkill) {
        super(icon, name, description);
        this.quantity = quantity;
        this.consumableSkill = consumableSkill;
    }

    public Consumable(Entity icon, String name, String description, int cost, int quantity, Skill consumableSkill) {
        super(icon, name, description, cost);
        this.quantity = quantity;
        this.consumableSkill = consumableSkill;
    }

    /**
     * Decrements after method is called, Call only when using the skill
     * Note : there is no restriction for consumable use for now
     */
    public Skill getConsumableSkill() {
        quantity--;
        return consumableSkill;
    }

//    TODO : FINISH THIS
//    public boolean isSkillUsable() {
//    }

}
