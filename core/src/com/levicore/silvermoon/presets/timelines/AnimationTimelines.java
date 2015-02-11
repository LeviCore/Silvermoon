package com.levicore.silvermoon.presets.timelines;

import aurelienribon.tweenengine.Timeline;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.SoundManager;
import com.levicore.silvermoon.State;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.presets.animations.SkillAnimations;

/**
 * Created by Leonard on 1/30/2015.
 */
public class AnimationTimelines {

    public static Timeline SLASH(State state, BattleEntity target, float x, float y) {
        //TODO REPLACE SOUND AND ANIMATION ASSET THESE ARE FROM RPGMAKER

        Timeline timeline = Timeline.createSequence();

        timeline.push(state.createTemporaryTweenAnimation(target.getWidth(), target.getHeight(), x, y, SkillAnimations.SWORD_SLASH()));
        timeline.push(SoundManager.playSound(Assets.getSound("slash_1")));

        return timeline;
    }



}
