package com.levicore.silvermoon.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Leonard on 1/1/2015.
 */
public class AnimationUtils {

    public static Animation createAnimationFromRegions(TextureRegion[][] textureRegions, int column, float frameDuration, Animation.PlayMode playMode) {
        Animation animation = new Animation(frameDuration, textureRegions[column][0], textureRegions[column][1], textureRegions[column][2]);
        animation.setPlayMode(playMode);
        return animation;
    }

    public static Animation getLoopingAnimation(Animation animation, Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
        return animation;
    }

    public static Animation flipAnimation(Animation animation, boolean x, boolean y) {
        for(TextureRegion textureRegion : animation.getKeyFrames()) {
            textureRegion.flip(x, y);
        }
        return animation;
    }

    public static void flipTextureRegions(TextureRegion[][] textureRegions, boolean x, boolean y) {
        for(TextureRegion[] textureRegions_2 : textureRegions) {
            for(TextureRegion textureRegion : textureRegions_2) {
                textureRegion.flip(x, y);
            }
        }
    }

}
