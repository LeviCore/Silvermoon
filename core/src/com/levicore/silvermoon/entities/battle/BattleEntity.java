package com.levicore.silvermoon.entities.battle;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.battle.Behavior;
import com.levicore.silvermoon.battle.Buff;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.battle.skills.Attack;
import com.levicore.silvermoon.battle.skills.Defend;
import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.ui.PercentageBar;
import com.levicore.silvermoon.utils.tween.EntityAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonard on 1/3/2015.
 *
 */
public class BattleEntity extends Entity {

    protected List<Skill> skills = new ArrayList<>();
    protected List<Buff> buffs = new ArrayList<>();
    protected List<Behavior> behaviors = new ArrayList<>();
    protected List<Item> itemDrops = new ArrayList<>();

    protected List<Equipment.EQUIPMENT_TYPE> equipmentsAvailable;
    protected Map<Equipment.EQUIPMENT_TYPE, Equipment> equipments;

    protected float maxHP;
    protected float maxMP;
    protected float maxTP;

    protected float curHP;
    protected float curMP;
    protected float curTP;

    protected float atk;
    protected float def;
    protected float mAtk;
    protected float mDef;

    protected float speed;

    protected Animation IDLE;
    protected Animation HURT;
    protected Animation DANGER;
    protected Animation WALKING;

    protected Animation VICTORY;
    protected Animation DODGE;
    protected Animation DEAD;

    protected Animation MELEE_ATTACK;
    protected Animation RANGED_ATTACK;
    protected Animation SPECIAL_ATTACK;
    protected Animation CAST;

    protected Animation DEFEND;

    protected PercentageBar hpBar;
    protected PercentageBar mpBar;
    protected PercentageBar tpBar;

    protected String name;

    protected BitmapFont bitmapFont;



    protected BattleEntity(String name, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed) {
        super();

        this.name = name;

        this.maxHP = maxHP;
        curHP = maxHP;

        this.maxMP = maxMP;
        curMP = maxMP;

        this.maxTP = maxTP;
        curTP = maxTP;

        this.atk = atk;
        this.def = def;
        this.mAtk = mAtk;
        this.mDef = mDef;
        this.speed = speed;

        init();
    }

    public BattleEntity(String name, Animation animation, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed) {
        super(animation);

        this.name = name;

        this.maxHP = maxHP;
        curHP = maxHP;

        this.maxMP = maxMP;
        curMP = maxMP;

        this.maxTP = maxTP;
        curTP = maxTP;

        this.atk = atk;
        this.def = def;
        this.mAtk = mAtk;
        this.mDef = mDef;
        this.speed = speed;

        init();
    }

    public BattleEntity(String name, TextureRegion region, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed) {
        super(region);

        this.name = name;

        this.maxHP = maxHP;
        curHP = maxHP;

        this.maxMP = maxMP;
        curMP = maxMP;

        this.maxTP = maxTP;
        curTP = maxTP;

        this.atk = atk;
        this.def = def;
        this.mAtk = mAtk;
        this.mDef = mDef;
        this.speed = speed;

        init();
    }

    public BattleEntity(String name, Texture texture, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed) {
        super(texture);

        this.name = name;

        this.maxHP = maxHP;
        curHP = maxHP;

        this.maxMP = maxMP;
        curMP = maxMP;

        this.maxTP = maxTP;
        curTP = maxTP;

        this.atk = atk;
        this.def = def;
        this.mAtk = mAtk;
        this.mDef = mDef;
        this.speed = speed;

        init();
    }

    public void init() {
        equipmentsAvailable = new ArrayList<>();
        equipments = new HashMap<>();

        skills.add(new Attack());
        skills.add(new Defend());

        hpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("HPBar"));
        mpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("MPBar"));
        tpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("TPBar"));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        hpBar.setCurrentValue(curHP);
        hpBar.setMaxValue(maxHP);
        hpBar.setPosition(getX() + (getWidth() / 2) - (hpBar.getMaxWidth() / 2), getY() - hpBar.getHeight());
        hpBar.update(delta);

        mpBar.setCurrentValue(curMP);
        mpBar.setMaxValue(maxMP);
        mpBar.setPosition(getX() + (getWidth() / 2) - (hpBar.getMaxWidth() / 2), hpBar.getY() - mpBar.getHeight());
        mpBar.update(delta);


        tpBar.setCurrentValue(curTP);
        tpBar.setMaxValue(maxTP);
        tpBar.setPosition(getX() + (getWidth() / 2) + mpBar.getWidth() - (hpBar.getMaxWidth() / 2), hpBar.getY() - mpBar.getHeight());
        tpBar.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);

        hpBar.draw(batch);
        mpBar.draw(batch);
        tpBar.draw(batch);
    }

    public float getPercentage(Attribute attribute) {
        switch (attribute) {
            case HP :
                return (curHP / maxHP) * 100;
            case MP :
                return (curMP / maxMP) * 100;
            case TP :
                return (curTP / maxTP) * 100;
        }

        return -1;
    }

    /**
     * Equipment control
     */
    public void equip(Equipment equipment) throws Exception {
        if(equipmentsAvailable.contains(equipment.getEquipment_type())) {
            if(!equipments.containsKey(equipment.getEquipment_type())) {

                equipments.put(equipment.getEquipment_type(), equipment);
                equipment.equipEffect();

            } else {
                throw new Exception("an Equipment is already Equiped.");
            }
        } else {
           throw new Exception("Equipment is not available for this character, add it");
        }
    }

    public void unEquip(Equipment equipment) {
        if(equipments.containsKey(equipment.getEquipment_type())) {
            equipment.unequipEffect();
            equipments.remove(equipment);
        }
    }

    /**
     * Macros
     */
    public Timeline fadeInBars(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(hpBar.fadeIn(duration));
        timeline.push(mpBar.fadeIn(duration));
        timeline.push(tpBar.fadeIn(duration));

        return timeline;

    }

    public Timeline fadeOutBars(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(hpBar.fadeOut(duration));
        timeline.push(mpBar.fadeOut(duration));
        timeline.push(tpBar.fadeOut(duration));

        return timeline;
    }

    public void addCustomFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    /**
     * Getters and setters
     */
    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(float maxMP) {
        this.maxMP = maxMP;
    }

    public float getMaxTP() {
        return maxTP;
    }

    public void setMaxTP(float maxTP) {
        this.maxTP = maxTP;
    }

    public float getCurHP() {
        return curHP;
    }

    public void setCurHP(float curHP) {
        this.curHP = curHP;
    }

    public float getCurMP() {
        return curMP;
    }

    public void setCurMP(float curMP) {
        this.curMP = curMP;
    }

    public float getCurTP() {
        return curTP;
    }

    public void setCurTP(float curTP) {
        this.curTP = curTP;
    }

    public float getAtk() {
        return atk;
    }

    public void setAtk(float atk) {
        this.atk = atk;
    }

    public float getDef() {
        return def;
    }

    public void setDef(float def) {
        this.def = def;
    }

    public float getmAtk() {
        return mAtk;
    }

    public void setmAtk(float mAtk) {
        this.mAtk = mAtk;
    }

    public float getmDef() {
        return mDef;
    }

    public void setmDef(float mDef) {
        this.mDef = mDef;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Animation getIdlePose() {
        return IDLE;
    }

    public void setIDLE(Animation IDLE) {
        this.IDLE = IDLE;
    }

    public Animation getHurtPose() {
        return HURT;
    }

    public void setHURT(Animation HURT) {
        this.HURT = HURT;
    }

    public Animation getDANGER() {
        return DANGER;
    }

    public void setDANGER(Animation DANGER) {
        this.DANGER = DANGER;
    }

    public Animation getWalkingPose() {
        return WALKING;
    }

    public void setWALKING(Animation WALKING) {
        this.WALKING = WALKING;
    }

    public Animation getVICTORY() {
        return VICTORY;
    }

    public void setVICTORY(Animation VICTORY) {
        this.VICTORY = VICTORY;
    }

    public Animation getDODGE() {
        return DODGE;
    }

    public void setDODGE(Animation DODGE) {
        this.DODGE = DODGE;
    }

    public Animation getDeadPose() {
        return DEAD;
    }

    public void setDEAD(Animation DEAD) {
        this.DEAD = DEAD;
    }

    public Animation getMeleeAttackPose() {
        return MELEE_ATTACK;
    }

    public void setMELEE_ATTACK(Animation MELEE_ATTACK) {
        this.MELEE_ATTACK = MELEE_ATTACK;
    }

    public Animation getRANGED_ATTACK() {
        return RANGED_ATTACK;
    }

    public void setRANGED_ATTACK(Animation RANGED_ATTACK) {
        this.RANGED_ATTACK = RANGED_ATTACK;
    }

    public Animation getSPECIAL_ATTACK() {
        return SPECIAL_ATTACK;
    }

    public void setSPECIAL_ATTACK(Animation SPECIAL_ATTACK) {
        this.SPECIAL_ATTACK = SPECIAL_ATTACK;
    }

    public Animation getCAST() {
        return CAST;
    }

    public void setCAST(Animation CAST) {
        this.CAST = CAST;
    }

    public Animation getDefendPose() {
        return DEFEND;
    }

    public void setDEFEND(Animation DEFEND) {
        this.DEFEND = DEFEND;
    }

    public PercentageBar getHpBar() {
        return hpBar;
    }

    public void setHpBar(PercentageBar hpBar) {
        this.hpBar = hpBar;
    }

    public PercentageBar getMpBar() {
        return mpBar;
    }

    public void setMpBar(PercentageBar mpBar) {
        this.mpBar = mpBar;
    }

    public PercentageBar getTpBar() {
        return tpBar;
    }

    public void setTpBar(PercentageBar tpBar) {
        this.tpBar = tpBar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<Behavior> behaviors) {
        this.behaviors = behaviors;
    }

    public List<Item> getItemDrops() {
        return itemDrops;
    }

    public void setItemDrops(List<Item> itemDrops) {
        this.itemDrops = itemDrops;
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    public List<Equipment.EQUIPMENT_TYPE> getEquipmentsAvailable() {
        return equipmentsAvailable;
    }

    public void setEquipmentsAvailable(List<Equipment.EQUIPMENT_TYPE> equipmentsAvailable) {
        this.equipmentsAvailable = equipmentsAvailable;
    }

    public Animation getIDLE() {
        return IDLE;
    }

    public Animation getHURT() {
        return HURT;
    }

    public Animation getWALKING() {
        return WALKING;
    }

    public Animation getDEAD() {
        return DEAD;
    }

    public Animation getMELEE_ATTACK() {
        return MELEE_ATTACK;
    }

    public Animation getDEFEND() {
        return DEFEND;
    }

    public Equipment getEquipment(Equipment.EQUIPMENT_TYPE type) {
        return equipments.get(type);
    }

    /**
     * Attribute control
     */
    public static enum Attribute {
        HP, MP, TP
    }

    public Tween setHP(float hp, float duration) {
        return Tween.to(this, EntityAccessor.HP, duration).target(hp);
    }

    public Tween setMP(float mp, float duration) {
        return Tween.to(this, EntityAccessor.MP, duration).target(mp);
    }

    public Tween setTP(float tp, float duration) {
        return Tween.to(this, EntityAccessor.TP, duration).target(tp);
    }

}
