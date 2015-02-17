package com.levicore.silvermoon.entities.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.utils.AnimationUtils;

/**
 * Created by Leonard on 1/11/2015.
 */
public class KadukiBattler extends BattleEntity {

    private TextureRegion[][] char_1, char_2, char_3;

    /**
     * Constructor with default stat values as 1
     */
    public KadukiBattler(String name, String charsetName, int regionWidth, int regionHeight, boolean flipX, boolean flipY) {
        super(name, 1, 1, 1, 1, 1, 1, 1, 1);
        createKadukiBattler(charsetName, regionWidth, regionHeight);
        flipBattler_void(flipX, flipY);
    }

    public KadukiBattler(String name, String charsetName, int regionWidth, int regionHeight, float maxHP, float maxMP, float maxTP, float atk, float def, float mAtk, float mDef, float speed, boolean flipX, boolean flipY) {
        super(name, maxHP, maxMP, maxTP, atk, def, mAtk, mDef, speed);
        createKadukiBattler(charsetName, regionWidth, regionHeight);
        flipBattler_void(flipX, flipY);
    }

    public void createKadukiBattler(String charsetName, int regionWidth, int regionHeight) {
        char_1 = split(new Texture("data/characters/"+charsetName+"_1.png"), regionWidth, regionHeight);
        char_2 = split(new Texture("data/characters/"+charsetName+"_2.png"), regionWidth, regionHeight);
        char_3 = split(new Texture("data/characters/"+charsetName+"_3.png"), regionWidth, regionHeight);

        IDLE = AnimationUtils.createAnimationFromRegions(char_1, 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        HURT = AnimationUtils.createAnimationFromRegions(char_1, 1, 0.10f, Animation.PlayMode.NORMAL);
        DANGER = AnimationUtils.createAnimationFromRegions(char_1, 2, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        WALKING = AnimationUtils.createAnimationFromRegions(char_1, 3, 0.15f, Animation.PlayMode.LOOP_PINGPONG);

        VICTORY = AnimationUtils.createAnimationFromRegions(char_2, 0, 0.15f, Animation.PlayMode.NORMAL);
        DODGE = AnimationUtils.createAnimationFromRegions(char_2, 1, 0.15f, Animation.PlayMode.NORMAL);
        DEAD = AnimationUtils.createAnimationFromRegions(char_2, 3, 0.15f, Animation.PlayMode.NORMAL);

        MELEE_ATTACK = AnimationUtils.createAnimationFromRegions(char_3, 0, 0.09f, Animation.PlayMode.LOOP_PINGPONG);
        RANGED_ATTACK = AnimationUtils.createAnimationFromRegions(char_3, 1, 0.15f, Animation.PlayMode.NORMAL);
        SPECIAL_ATTACK = AnimationUtils.createAnimationFromRegions(char_3, 2, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        CAST = AnimationUtils.createAnimationFromRegions(char_3, 3, 0.15f, Animation.PlayMode.LOOP_PINGPONG);

        DEFEND = AnimationUtils.createAnimationFromRegions(char_3, 0, 0, Animation.PlayMode.REVERSED);

        animation = IDLE;
        setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());

        hpBar.setMaxWidth(getWidth());
        mpBar.setMaxWidth(getWidth() / 2);
        tpBar.setMaxWidth(getWidth() / 2);
    }

    public Tween flipBattler(final boolean x, final boolean y) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                AnimationUtils.flipTextureRegions(char_1, x, y);
                AnimationUtils.flipTextureRegions(char_2, x, y);
                AnimationUtils.flipTextureRegions(char_3, x, y);
            }
        });
    }

    public void flipBattler_void(boolean x, boolean y) {
        AnimationUtils.flipTextureRegions(char_1, x, y);
        AnimationUtils.flipTextureRegions(char_2, x, y);
        AnimationUtils.flipTextureRegions(char_3, x, y);
    }

}
