package com.levicore.silvermoon.entities.battle;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.battle.Behavior;
import com.levicore.silvermoon.battle.Buff;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.battle.skills.Attack;
import com.levicore.silvermoon.battle.skills.Defend;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.ui.PercentageBar;
import com.levicore.silvermoon.utils.tween.EntityAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/3/2015.
 *
 */
public class BattleEntity extends Entity {

    public List<Skill> skills = new ArrayList<>();
    public List<Buff> buffs = new ArrayList<>();

    public float maxHP;
    public float maxMP;
    public float maxTP;

    public float curHP;
    public float curMP;
    public float curTP;

    public float atk;
    public float def;
    public float mAtk;
    public float mDef;

    public float speed;

    public Animation IDLE;
    public Animation HURT;
    public Animation DANGER;
    public Animation WALKING;

    public Animation VICTORY;
    public Animation DODGE;
    public Animation DEAD;

    public Animation MELEE_ATTACK;
    public Animation RANGED_ATTACK;
    public Animation SPECIAL_ATTACK;
    public Animation CAST;

    public Animation DEFEND;

    public PercentageBar hpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("HPBar"));
    public PercentageBar mpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("MPBar"));
    public PercentageBar tpBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("TPBar"));

    public String name;

    public Entity weapon;

    public List<Behavior> behaviors = new ArrayList<>();
    public List<Item> itemDrops = new ArrayList<>();

    private BitmapFont bitmapFont;

    protected BattleEntity() {
        super();
        init();
    }

    public BattleEntity(Texture texture) {
        super(texture);

        init();
    }

    public BattleEntity(Animation animation) {
        super(animation);

        init();
    }

    public BattleEntity(Texture texture, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed) {
        super(texture);
        this.maxHP = maxHP;
        this.maxMP = maxMP;
        this.maxTP = maxTP;
        this.atk = atk;
        this.def = def;
        this.mAtk = mAtk;
        this.mDef = mDef;
        this.speed = speed;

        init();
    }

    public void init() {
        skills.add(new Attack());
        skills.add(new Defend());
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

    public Entity getWeapon() {
        return weapon;
    }

    public void setWeapon(Entity weapon) {
        this.weapon = weapon;
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
