package com.levicore.silvermoon.entities.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.utils.tween.EntityAccessor;

/**
 * Created by user on 2/19/2015.
 */
public class LevelableBattler extends BattleEntity {

    private float curExp;
    private float toNextLevel;
    private float expRate;

    /**
     * Level Control
     */
    public Tween addExp(float expToAdd, float duration) {
        return Tween.to(this, EntityAccessor.EXP, duration).target(curExp += expToAdd).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if(curExp > toNextLevel) {
                    level++;

                    curExp = 0;
                    toNextLevel += toNextLevel * expRate;
                }
            }
        });
    }

    /**
     * Constructors
     */
    public LevelableBattler(String name, int level, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, float curExp, float toNextLevel, float expRate) {
        super(name, level, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        this.curExp = curExp;
        this.toNextLevel = toNextLevel;
        this.expRate = expRate;
    }

    public LevelableBattler(String charsetName, int level, boolean flipX, boolean flipY, int regionWidth, int regionHeight, String name, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, float curExp, float toNextLevel, float expRate) {
        super(charsetName, level, flipX, flipY, regionWidth, regionHeight, name, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        this.curExp = curExp;
        this.toNextLevel = toNextLevel;
        this.expRate = expRate;
    }

    public LevelableBattler(String name, int level, Animation animation, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, float curExp, float toNextLevel, float expRate) {
        super(name, level, animation, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        this.curExp = curExp;
        this.toNextLevel = toNextLevel;
        this.expRate = expRate;
    }

    public LevelableBattler(String name, int level, TextureRegion region, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, float curExp, float toNextLevel, float expRate) {
        super(name, level, region, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        this.curExp = curExp;
        this.toNextLevel = toNextLevel;
        this.expRate = expRate;
    }

    public LevelableBattler(String name, int level, Texture texture, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, float curExp, float toNextLevel, float expRate) {
        super(name, level, texture, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        this.curExp = curExp;
        this.toNextLevel = toNextLevel;
        this.expRate = expRate;
    }

    /**
     * Getters and setters
     */

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getCurExp() {
        return curExp;
    }

    @Deprecated
    public void setCurExp(float curExp) {
        this.curExp = curExp;
    }

    public float getToNextLevel() {
        return toNextLevel;
    }

    public void setToNextLevel(float toNextLevel) {
        this.toNextLevel = toNextLevel;
    }

    public float getExpRate() {
        return expRate;
    }

    public void setExpRate(float expRate) {
        this.expRate = expRate;
    }
}
